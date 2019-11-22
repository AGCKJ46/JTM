package net.ckj46.JTM;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return tasksRepository.fetchAll();
    }

    @PostMapping
    public void addTasks(@RequestBody Task task){
        tasksRepository.add(task);
    }

}
