package net.ckj46.JTM;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping(path="/api/tasks")
@RestController
public class TasksController {
    private final TasksRepository tasksRepository;

    @Autowired
    public TasksController(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @GetMapping
    public List<Task> getTasks(){
        log.info("Fetching all task ...");
        return tasksRepository.fetchAll();
    }

    @GetMapping(path = "/{id}")
    public Task getTaskById(@PathVariable Long id){
        log.info("Fetching a task: {}",id);
        return tasksRepository.fetchById(id);
    }

    @PostMapping
    public void addTask(@RequestBody Task task){
        log.info("Adding new task: {}", task.toString());
        tasksRepository.add(task);
    }

    @DeleteMapping(path = "/{id}")
    public void deletingTaskById(@PathVariable Long id){
        log.info("Deleting a task: {}",id);
        tasksRepository.deletingById(id);
    }

    @PutMapping
    public void updateTask(){
        log.info("Updating a task ...");
    }

}
