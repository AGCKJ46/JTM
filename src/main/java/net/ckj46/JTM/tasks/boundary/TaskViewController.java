package net.ckj46.JTM.tasks.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.tasks.control.TasksService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class TaskViewController {

    private final TasksService tasksService;

    @GetMapping("/")
    public String homePage(Model model){
        model.addAttribute("tasks", tasksService.fetchAll());
        model.addAttribute("newTask", new CreateTaskRequest());
        return "home";
    }

    @PostMapping("/tasks")
    public String addTask(@ModelAttribute("newTask") CreateTaskRequest request){
        log.info("New task is adding now...");
        tasksService.addTask(request.title, request.description, request.project, request.prio);
        return "redirect:/";
    }

    @PostMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id){
        log.info("Task {} is deleting now...",id);
        tasksService.deleteTask(id);
        return "redirect:/";
    }
}
