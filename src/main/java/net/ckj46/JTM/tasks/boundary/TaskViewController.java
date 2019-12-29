package net.ckj46.JTM.tasks.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.attachments.control.AttachmentService;
import net.ckj46.JTM.attachments.entity.Attachment;
import net.ckj46.JTM.tasks.control.TasksService;
import net.ckj46.JTM.tasks.entity.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class TaskViewController {

    private final TasksService tasksService;
    private final AttachmentService attachmentService;

    @GetMapping("/")
    public String tasksPage(Model model) {
        model.addAttribute("tasks", tasksService.fetchAll());
        return "tasks/list";
    }

    @GetMapping("/tasks/{taskId}/attachments")
    public String attachmentsPage(Model model, @PathVariable Long taskId) {
        Task task = tasksService.fetchTaskById(taskId);
        model.addAttribute("taskId", task.getId());
        model.addAttribute("taskTitle", task.getTitle());
        model.addAttribute("attachments", task.getAttachments());

        return "/attachments/list";
    }

    @GetMapping("/tasks/attachments/comments/{attachmentId}")
    public String commentsPage(Model model, @PathVariable Long attachmentId) {
        Attachment attachment = attachmentService.fetchAttachmentById(attachmentId);

        model.addAttribute("attachmentId", attachment.getId());
        model.addAttribute("taskTitle", attachment.getFileName());
        //model.addAttribute("comments",      attachment.getComments());

        return "comments";
    }

    @GetMapping("/tasks/addnew")
    public String addNewTaskPage(Model model) {
        model.addAttribute("newTask", new CreateTaskRequest());
        return "tasks/addnew";
    }

    @PostMapping("/tasks/new")
    public String addTaskAction(@ModelAttribute("newTask") CreateTaskRequest request) throws IOException {
        log.info("New task is adding now...");
        Task task = tasksService.addTask(request.title, request.description, request.project, request.prio, null);
        return "redirect:/";
    }

    @PostMapping("/tasks/{taskId}/delete")
    public String deleteTaskAction(@PathVariable Long taskId) throws IOException {
        log.info("Task {} is deleting now...", taskId);
        tasksService.deleteTaskById(taskId);
        return "redirect:/";
    }


}
