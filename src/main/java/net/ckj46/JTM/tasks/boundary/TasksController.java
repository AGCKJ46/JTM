package net.ckj46.JTM.tasks.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.app.exceptions.NotFoundException;
import net.ckj46.JTM.attachments.control.AttachmentService;
import net.ckj46.JTM.attachments.entity.Attachment;
import net.ckj46.JTM.attachments.repository.StorageService;
import net.ckj46.JTM.tags.control.TagsService;
import net.ckj46.JTM.tasks.control.TasksService;
import net.ckj46.JTM.tasks.entity.Task;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping(path = "/api/tasks")
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class TasksController {
    private final TasksService tasksService;
    private final AttachmentService attachmentService;
    private final StorageService storageService; // TODO TaskController nie wpowinien bezpośrednio korzystać z StorageService

    @PostConstruct
    void init() throws IOException {
        log.info("init");
        tasksService.addTask("zadanie domowe M2", "1. rozszerzyć obiekt Task 2. pakietowanie", 1L, 1);
        tasksService.addTask("youtube od Przemka Bykowskiego", "", 1L, 1);
        tasksService.addTask("wymienić zamek w drzwiach do garażu", "", 2L, 2);
        tasksService.addTask("odnowić prenumeratę Programista","", 2L, 2);
        tasksService.addTask("spłacić kartę","", 3L, 2);
    }

    @GetMapping
    public List<TaskResponse> getTasks(HttpServletResponse response, @RequestParam Optional<String> query) throws IOException {
        log.info("Fetching all task with query: {}", query);

        List<TaskResponse> taskResponseList = null;
        try {
            taskResponseList = query
                    .map(tasksService::filterAllByQuery)
                    .orElseGet(tasksService::fetchAll)
                    .stream()
                    .map(task->TaskResponse.from(task, task.getTags(), task.getAttachments()))
                    .collect(Collectors.toList());

            response.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }

        return taskResponseList;
    }

    @GetMapping(path = "/{id}")
    public TaskResponse getTaskById(HttpServletResponse response, @PathVariable Long id) throws IOException {
        log.info("Fetching a task: {}", id);
        TaskResponse taskResponse = null;
        try {
            Task task = tasksService.fetchTaskById(id);
            taskResponse = TaskResponse.from(task, task.getTags(), task.getAttachments());
            response.setStatus(HttpStatus.OK.value());
        } catch (NotFoundException e) {
            log.error("Unable to find a task: {} - error: {}", id, e.getMessage());
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
        return taskResponse;
    }

    @GetMapping(path = "/title/{title}")
    public List<TaskResponse> getTaskByTitle(HttpServletResponse response, @PathVariable String title) throws IOException {
        log.info("getTaskByTitle: {}", title);
        List<TaskResponse> taskResponses = new LinkedList<>();
        try {
            List<Task> tasks = tasksService.findTaskByTitle(title);
            for (Task task: tasks) {
                TaskResponse taskResponse = TaskResponse.from(task, task.getTags(), task.getAttachments());
                taskResponses.add(taskResponse);
            }
            response.setStatus(HttpStatus.OK.value());
        } catch (NotFoundException e) {
            log.error("Unable to find a task with title: {} - error: {}", title, e.getMessage());
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
        return taskResponses;
    }

    // TODO przenieść do AtachmentControler?
    @GetMapping(path = "/{id}/attachments/{filename}")
    public ResponseEntity getAttachmentByFilename(@PathVariable Long id, @PathVariable String filename, HttpServletRequest request) {
        log.info("Fetching an tasks {} attachment : {}", id, filename);
        Resource resource;
        String mimeType;

        try {
            resource = storageService.loadFile(id, filename);
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
        } catch (NotFoundException | MalformedURLException e) {
            log.error("Unable to add attachment to a task: {} - error: {}", id, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (IOException e) {
            log.error("Unable to add attachment to a task: {} - error: {}", id, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resource);
    }

    // TODO przenieść do AtachmentControlerv?
    @PostMapping(path = "/{taskId}/attachments")
    public ResponseEntity addAttachment(@PathVariable Long taskId, @RequestParam("file") MultipartFile file) throws IOException {
        log.info("Adding attachment: {} to a task: {}", file.getOriginalFilename(), taskId);

        Task task = tasksService.fetchTaskById(taskId);
        attachmentService.addAttachment(file, task);

        log.info("Attachment: {} is added to a task: {}", file.getOriginalFilename(), taskId);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping
    public void addTask(HttpServletResponse response, @RequestBody CreateTaskRequest task) throws IOException {
        log.info("Adding new task: {}", task.toString());
        tasksService.addTask(task.title, task.description, task.projectId, task.prio);
        response.setStatus(HttpStatus.CREATED.value());
    }

    @DeleteMapping(path = "/{taskId}")
    public ResponseEntity deleteTaskById(@PathVariable Long taskId) throws IOException {
        log.info("Deleting a task: {}", taskId);
        tasksService.deleteTaskById(taskId);
        // TODO co z załącznikami
        log.info("Task: {} is deleted!", taskId);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequest task) {
        log.info("Updating a task: {}", id);
        tasksService.updateTask(id, task.title, task.description, task.projectId, task.prio);
        log.error("Task {} is updated!", id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
