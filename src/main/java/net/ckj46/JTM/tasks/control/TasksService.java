package net.ckj46.JTM.tasks.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.app.sys.Clock;
import net.ckj46.JTM.attachments.entity.Attachment;
import net.ckj46.JTM.attachments.repository.StorageService;
import net.ckj46.JTM.tags.entity.Tag;
import net.ckj46.JTM.tags.entity.TagRef;
import net.ckj46.JTM.tags.repository.TagsRepository;
import net.ckj46.JTM.tasks.entity.Task;
import net.ckj46.JTM.tasks.repository.TasksRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TasksService {
    private final TasksRepository tasksRepository;
    private final StorageService storageService;
    private final TagsRepository tagsRepository;

    private final Clock clock;

    // TODO wywalić file
    public Task addTask(String title, String description, String project, int prio, MultipartFile file) throws IOException {
        log.info("addTask - title: {}., description: {}, project: {}, prio: {}", title, description, project, prio);
        Task task = new Task(
                title,
                description,
                project,
                prio,
                clock.time(),
                clock.time()
        );
        tasksRepository.add(task);

        if (file != null && !file.isEmpty()) {
            storageService.saveFile(task.getId(), file);
            task.addAttachment(new Attachment(file.getOriginalFilename(), task.getId()));
            tasksRepository.save(task);
        }

        return task;
    }

    public void addTag(Long tagId, Long taskId){
        Task task = tasksRepository.fetchById(taskId);
        Tag tag = tagsRepository.fetchById(tagId);
        task.addTag(tag);
        tasksRepository.save(task);
    }

    public void updateTask(Long id, String title, String description, String project, int prio) {
        tasksRepository.update(id, title, description, project, prio, clock.time());
    }

    public List<Task> fetchAll() {
        return tasksRepository.fetchAll();
    }

    public List<Task> filterAllByQuery(String query) {
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
        for (Attachment attachment: attachments) {
            storageService.deleteFile(attachment.getFileName(), taskId);
        }
    }

    public Task fetchTaskById(Long taskId) {
        log.info("fetchTaskById - taskId: {}", taskId);
        return tasksRepository.fetchById(taskId);
    }

    public void saveTask(Task task) {
        tasksRepository.save(task);
    }

    public List<Task> findTaskByTitle(String title) {
        log.info("findTaskByTitle - title: {}", title);
        return tasksRepository.findByTitle(title);
    }
}
