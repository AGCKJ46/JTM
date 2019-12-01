package net.ckj46.JTM.tasks.boundary;

import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.exceptions.NotFoundException;
import net.ckj46.JTM.tasks.control.TasksService;
import net.ckj46.JTM.tasks.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public List<TaskResponse> getTasks(@RequestParam Optional<String> query) {
        log.info("Fetching all task with query: {}", query);
        return query.map(tasksService::filterAllByQuery)
                .orElseGet(tasksService::fetchAll)
                .stream()
                .map(this::toTaskResponse)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        log.info("Fetching a task: {}", id);
        return toTaskResponse(tasksRepository.fetchById(id));
    }

    @PostMapping
    public void addTask(@RequestBody CreateTaskRequest task) {
        log.info("Adding new task: {}", task.toString());
        tasksService.addTask(task.title, task.description, task.project, task.prio);
    }

    @DeleteMapping(path = "/{id}")
    public void deletingTaskById(@PathVariable Long id) {
        log.info("Deleting a task: {}", id);
        tasksRepository.deletingById(id);
    }

    @PutMapping(path = "/{id}")
    public void updateTask(@PathVariable Long id, @RequestBody UpdateTaskRequest task ) {
        log.info("Updating a task: {}", id);
        try {
            tasksService.updateTask(id, task.title, task.description, task.project, task.prio);
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
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
