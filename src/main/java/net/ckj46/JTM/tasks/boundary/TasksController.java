package net.ckj46.JTM.tasks.boundary;

import com.sun.deploy.net.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.exceptions.NotFoundException;
import net.ckj46.JTM.tasks.control.TasksService;
import net.ckj46.JTM.tasks.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping(path="/api/tasks")
@RestController
public class TasksController {
    private final TasksRepository tasksRepository;
    private final TasksService tasksService;

    @Autowired
    public TasksController(TasksRepository tasksRepository, TasksService tasksService) {
        this.tasksRepository = tasksRepository;
        this.tasksService = tasksService;
    }

    @PostConstruct
    void init(){
        tasksService.addTask("zadanie domowe M2", "1. rozszerzyć obiekt Task 2. pakietowanie", "Kurs Springa", 1);
        tasksService.addTask("youtube od Przemka Bykowskiego", "", "Kurs Springa", 1);
        tasksService.addTask("wymienić zamek w drzwiach do garażu", "", "Dom", 2);
    }

    // 200 /
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

    @PostMapping
    public void addTask(HttpServletResponse response, @RequestBody CreateTaskRequest task) {
        log.info("Adding new task: {}", task.toString());
        tasksService.addTask(task.title, task.description, task.project, task.prio);
        response.setStatus(HttpStatus.CREATED.value());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity
    deletingTaskById(@PathVariable Long id) {
        log.info("Deleting a task: {}", id);
        try {
            tasksRepository.deletingById(id);
            log.info("Task: {} is deleted!", id);
            return ResponseEntity
                    .noContent()
                    .build();
        }catch (NotFoundException e){
            log.error("Unable to delete task: {} - error: {}", id, e.getMessage());
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
                task.getEditedAt()
        );
    }
}
