package net.ckj46.JTM.attachments.boundary;

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
public class AttachmentsViewController {

    private final AttachmentService attachmentService;
    private final TasksService tasksService;

    @GetMapping("/tasks/{taskId}/attachments/addnew")
    public String addNewAttachmentPage(Model model, @PathVariable Long taskId) {
        Task task = tasksService.fetchTaskById(taskId);
        model.addAttribute("taskId", task.getId());
        model.addAttribute("taskTitle", task.getTitle());
        return "attachments/addnew";
    }

    @PostMapping("/tasks/{taskId}/attachments/new")
    public String addAttachmentAction(@RequestParam("attachment") MultipartFile attachment, @PathVariable Long taskId) throws IOException {
        log.info("addAttachmentAction: '{}'", attachment.getOriginalFilename());
        attachmentService.addAttachment(attachment, tasksService.fetchTaskById(taskId));
        return "redirect:/tasks/" + taskId + "/attachments";
    }

    @PostMapping("/tasks/{taskId}/attachments/{attachmentId}/delete")
    public String deleteAttachmentAction(@PathVariable Long taskId, @PathVariable Long attachmentId) throws IOException {
        log.info("deleteAttachmentAction - taskId: {}, attachmentId: {}", taskId, attachmentId);
        Attachment attachment = attachmentService.fetchAttachmentById(attachmentId);
        log.info("deleteAttachmentAction - attachment: {}", attachment);
        attachmentService.delAttachment(attachmentId, attachment.getFileName(), taskId);
        return "redirect:/tasks/" + taskId + "/attachments";
    }

}
