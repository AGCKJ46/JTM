package net.ckj46.JTM.tasks.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.attachments.StorageService;
import net.ckj46.JTM.tasks.control.TasksService;
import net.ckj46.JTM.tasks.entity.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class TaskViewController {

    private final TasksService tasksService;
    private final StorageService storageService;

    @GetMapping("/")
    public String homePage(Model model){
        model.addAttribute("tasks", tasksService.fetchAll());
        model.addAttribute("newTask", new CreateTaskRequest());
        return "home";
    }

    @PostMapping("/tasks")
    public String addTask(@ModelAttribute("newTask") CreateTaskRequest request,
                          @RequestParam("attachment") MultipartFile attachment) throws IOException {
        log.info("New task is adding now...");
        Task task = tasksService.addTask(request.title, request.description, request.project, request.prio);
        if (!attachment.isEmpty()) {
            storageService.saveFile(task.getId(), attachment);
        }

        return "redirect:/";
    }

    @PostMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id){
        log.info("Task {} is deleting now...",id);
        tasksService.deleteTask(id);
        return "redirect:/";
    }
}
