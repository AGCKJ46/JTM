package net.ckj46.JTM.tasks.control;

import net.ckj46.JTM.app.sys.Clock;
import net.ckj46.JTM.attachments.control.AttachmentService;
import net.ckj46.JTM.attachments.entity.Attachment;
import net.ckj46.JTM.tasks.repository.TasksRepository;
import net.ckj46.JTM.tasks.entity.Task;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TasksService {
    private final TasksRepository tasksRepository;
    private final AttachmentService attachmentService;
    private final Clock clock;

    public TasksService(TasksRepository tasksRepository, AttachmentService attachmentService, Clock clock) {
        this.tasksRepository = tasksRepository;
        this.attachmentService = attachmentService;
        this.clock = clock;
    }

    // TODO wywalić file
    public Task addTask(String title, String description, String project, int prio, MultipartFile file) throws IOException {
        Task task = new Task(
                title,
                description,
                project,
                prio,
                clock.time(),
                clock.time()
        );
        tasksRepository.add(task);

        if (file!= null && !file.isEmpty()) {
            attachmentService.addAttachment(file, task.getId());
            task.addAttachment(new Attachment(file.getOriginalFilename()));
            tasksRepository.save(task); // TODO save -> update
        }

        return task;
    }

    public void updateTask(Long id, String title, String description, String project, int prio) {
        tasksRepository.update(id, title, description, project, prio, clock.time());
    }

    public List<Task> fetchAll(){
        return tasksRepository.fetchAll();
    }

    public List<Task> filterAllByQuery(String query){
        return tasksRepository.fetchAll()
                .stream()
                .filter(task -> task.getTitle().contains(query)
                        || task.getDescription().contains(query))
                .collect(Collectors.toList());
    }

    public void deleteTaskById(Long taskId) throws IOException {
        Task task = tasksRepository.fetchById(taskId);
        Set<Attachment> attachments = task.getAttachments();
        tasksRepository.deleteById(taskId);
        attachmentService.delAttachments(attachments, taskId);
    }

    public Task fetchTaskById(Long taskId) {
        return tasksRepository.fetchById(taskId);
    }

    public void saveTask(Task task) {
        tasksRepository.save(task);
    }
}
