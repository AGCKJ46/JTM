package net.ckj46.JTM.tasks.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.attachments.control.AttachmentService;
import net.ckj46.JTM.attachments.entity.Attachment;
import net.ckj46.JTM.projects.control.ProjectsService;
import net.ckj46.JTM.projects.entity.Project;
import net.ckj46.JTM.tags.control.TagsService;
import net.ckj46.JTM.tags.entity.Tag;
import net.ckj46.JTM.tasks.control.TasksService;
import net.ckj46.JTM.tasks.entity.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
public class TaskViewController {

    private final TasksService tasksService;
    private final AttachmentService attachmentService;
    private final TagsService tagsService;
    private final ProjectsService projectsService;

    @GetMapping("/")
    public String tasksPage(Model model) {
        model.addAttribute("tasks", tasksService
                                                    .fetchAll()
                                                    .stream()
                                                    .map(task->TaskViewResponse.from(task, task.getTags()))
                                                    .collect(Collectors.toList()));
        return "tasks/list";
    }

    @GetMapping("/tasks/{taskId}/attachments")
    public String attachmentsPage(Model model, @PathVariable Long taskId) {
        log.info("attachmentsPage - taskId: {}", taskId);
        Task task = tasksService.fetchTaskById(taskId);
        log.info("attachmentsPage - task {}", task.toString());
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

        List<Tag> tags = new LinkedList<>();
        tags.add(new Tag(""));
        tags.addAll(tagsService.fetchAll());
        model.addAttribute("tags", tags);

        List<Project> projects = new LinkedList<>();
        projects.add(new Project());
        projects.addAll(projectsService.fetchAll());
        model.addAttribute("projects", projects);

        return "tasks/addnew";
    }

    @PostMapping("/tasks/new")
    public String addTaskAction(@ModelAttribute("newTask") CreateTaskRequest request) throws IOException {
        log.info("New task is adding now...");
        Task task = tasksService.addTask(request.title, request.description, request.projectId, request.prio, null);

        Tag tag = tagsService.fetchById(request.tagId);
        task.addTag(tag);
        tasksService.saveTask(task);

        return "redirect:/";
    }

    @PostMapping("/tasks/{taskId}/delete")
    public String deleteTaskAction(@PathVariable Long taskId) throws IOException {
        log.info("Task {} is deleting now...", taskId);
        tasksService.deleteTaskById(taskId);
        return "redirect:/";
    }


}
