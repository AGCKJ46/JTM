package net.ckj46.JTM.tasks.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.attachments.control.AttachmentService;
import net.ckj46.JTM.attachments.entity.Attachment;
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
    private final AttachmentService attachmentService;

    @GetMapping("/")
    public String homePage(Model model){
        model.addAttribute("tasks", tasksService.fetchAll());
        model.addAttribute("newTask", new CreateTaskRequest());
        return "home";
    }

    @GetMapping("/tasks/attachments/{taskId}")
    public String attachmentsPage(Model model, @PathVariable Long taskId){
        Task task = tasksService.fetchTaskById(taskId);

        model.addAttribute("taskId", task.getId());
        model.addAttribute("taskTitle", task.getTitle());
        model.addAttribute("attachments", task.getAttachments());

        return "attachments";
    }

    @GetMapping("/tasks/attachments/comments/{attachmentId}")
    public String commentsPage(Model model, @PathVariable Long attachmentId){
        Attachment attachment = attachmentService.fetchAttachmentById(attachmentId);

        model.addAttribute("attachmentId",  attachment.getId());
        model.addAttribute("taskTitle",     attachment.getFilename());
        //model.addAttribute("comments",      attachment.getComments());

        return "comments";
    }


    @PostMapping("/tasks")
    public String addTaskAction(@ModelAttribute("newTask") CreateTaskRequest request,
                          @RequestParam("attachment") MultipartFile attachment) throws IOException {
        log.info("New task is adding now...");
        Task task = tasksService.addTask(request.title, request.description, request.project, request.prio, attachment);
        return "redirect:/";
    }

    @PostMapping("/tasks/delete/{id}")
    public String deleteTaskAction(@PathVariable Long id) throws IOException {
        log.info("Task {} is deleting now...",id);
        tasksService.deleteTaskById(id);
        return "redirect:/";
    }


}
