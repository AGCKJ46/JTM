package net.ckj46.JTM.tasks.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.attachments.StorageService;
import net.ckj46.JTM.app.exceptions.NotFoundException;
import net.ckj46.JTM.tasks.control.TasksService;
import net.ckj46.JTM.tasks.entity.*;
import net.ckj46.JTM.tasks.repository.TasksRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping(path="/api/tasks")
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class TasksController {
    private final TasksRepository tasksRepository;
    private final TasksService tasksService;
    private final StorageService storageService;

    @PostConstruct
    void init(){
        tasksService.addTask("zadanie domowe M2", "1. rozszerzyć obiekt Task 2. pakietowanie", "Kurs Springa", 1);
        tasksService.addTask("youtube od Przemka Bykowskiego", "", "Kurs Springa", 1);
        tasksService.addTask("wymienić zamek w drzwiach do garażu", "", "Dom", 2);
    }

    @GetMapping
    public List<TaskResponse> getTasks(HttpServletResponse response, @RequestParam Optional<String> query) throws IOException {
        log.info("Fetching all task with query: {}", query);

        List<TaskResponse> taskResponseList = null;
        try{
            taskResponseList = query
                                .map(tasksService::filterAllByQuery)
                                .orElseGet(tasksService::fetchAll)
                                .stream()
                                .map(this::toTaskResponse)
                                .collect(Collectors.toList());

            response.setStatus(HttpStatus.OK.value());
        }catch(Exception e){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }

        return taskResponseList;
    }

    @GetMapping(path = "/{id}")
    public TaskResponse getTaskById(HttpServletResponse response, @PathVariable Long id) throws IOException {
        log.info("Fetching a task: {}", id);
        TaskResponse taskResponse = null;
        try{
            taskResponse = toTaskResponse(tasksRepository.fetchById(id));
            response.setStatus(HttpStatus.OK.value());
        }catch(NotFoundException e) {
            log.error("Unable to find a task: {} - error: {}", id, e.getMessage());
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
        return taskResponse;
    }

    @GetMapping(path = "/{id}/attachments/{filename}")
    public ResponseEntity getAttachmentByFilename(@PathVariable Long id, @PathVariable String filename, HttpServletRequest request) {
        log.info("Fetching an tasks {} attachment : {}", id, filename );
        Resource resource;
        String mimeType;

        try{
            resource = storageService.loadFile(id, filename);
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            if(mimeType==null){
                mimeType="application/octet-stream";
            }
        }catch(NotFoundException | MalformedURLException e) {
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

    @PostMapping(path = "/{id}/attachments")
    public ResponseEntity addAttachment(@PathVariable Long id, @RequestParam("file") MultipartFile file){
        log.info("Adding attachment: {} to a task: {}", file.getOriginalFilename(), id);

        try {
            storageService.saveFile(id, file);
        } catch (IOException e) { // TODO to split to specific exceptions
            log.error("Unable to add attachment: {} to a task: {} - error: {}", file.getName(), id, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unable to add attachment: "+file.getName()+" "+file.getOriginalFilename()+" to a task: "+id+" - error: " + e.getMessage());
        }
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping
    public void addTask(HttpServletResponse response, @RequestBody CreateTaskRequest task) {
        log.info("Adding new task: {}", task.toString());
        tasksService.addTask(task.title, task.description, task.project, task.prio);
        response.setStatus(HttpStatus.CREATED.value());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteTaskById(@PathVariable Long id) {
        log.info("Deleting a task: {}", id);
        try {
            tasksRepository.deleteById(id);
            log.info("Task: {} is deleted!", id);
            return ResponseEntity
                    .noContent()
                    .build();
        }catch (NotFoundException e){
            log.error("Unable to delete a task: {} - error: {}", id, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequest task ) {
        log.info("Updating a task: {}", id);
        try {
            tasksService.updateTask(id, task.title, task.description, task.project, task.prio);
            log.error("Task {} is updated!", id);
            return ResponseEntity
                    .noContent()
                    .build();
        }catch(NotFoundException e){
            log.error("Unable to update task: {} - error: {}", id, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }

    }

    private TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getProject(),
                task.getPrio(),
                task.getCreatedAt(),
                task.getEditedAt(),
                task.getAttachmentsList()
        );
    }
}
