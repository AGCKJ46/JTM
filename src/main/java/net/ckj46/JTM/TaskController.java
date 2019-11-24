package net.ckj46.JTM;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping(path="/")
@RestController
public class TaskController {
    private final TasksRepository tasksRepository;

    @Autowired
    public TaskController(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @GetMapping
    public List<Task> getTasks(){
        log.info("Fetching all task ...");
        return tasksRepository.fetchAll();
    }

    @PostMapping
    public void addTasks(@RequestBody Task task){
        log.info("Adding new task: {}", task.toString());
        tasksRepository.add(task);
    }
}
