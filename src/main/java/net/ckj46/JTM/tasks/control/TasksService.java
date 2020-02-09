package net.ckj46.JTM.tasks.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.app.exceptions.NotFoundException;
import net.ckj46.JTM.app.sys.Clock;
import net.ckj46.JTM.attachments.entity.Attachment;
import net.ckj46.JTM.attachments.repository.StorageService;
import net.ckj46.JTM.projects.entity.Project;
import net.ckj46.JTM.tags.entity.Tag;
import net.ckj46.JTM.tags.repository.TagsRepository;
import net.ckj46.JTM.tasks.entity.Task;
import net.ckj46.JTM.tasks.repository.TasksRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public Task addTask(String title, String description, int prio, Project project){
        log.info("addTask - title: {}., description: {}, prio: {}, project: {}", title, description, prio, project.getTitle());
        Task task = new Task(
                title,
                description,
                prio,
                clock.time(),
                clock.time(), // TODO poszukać gdzie nie korzystam z tej klasy
                project
        );
        tasksRepository.add(task);
        return task;
    }

    public void addTag(Long tagId, Long taskId){
        Task task = tasksRepository.fetchById(taskId);
        Tag tag = tagsRepository.fetchById(tagId);
        task.addTag(tag);
        tasksRepository.save(task);
    }

    public void updateTask(Long id, String title, String description, int prio, Project project) {
        tasksRepository.update(id, title, description, prio, clock.time(), project);
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
        log.info("Task {} is deleting ...", taskId);
        Task task = tasksRepository.fetchById(taskId);

        Set<Attachment> attachments = task.getAttachments();
        for (Attachment attachment: attachments) {
            storageService.deleteFile(attachment.getFileName(), taskId);
        }

        tasksRepository.deleteById(taskId);

        log.info("Task {} is deleted ...", taskId);
    }

    public Task fetchTaskById(Long taskId) {
        log.info("fetchTaskById - taskId: {}", taskId);
        return tasksRepository.fetchById(taskId);
    }

    public void saveTask(Task task){
        log.info("Saving a task: {}", task.toString());
        tasksRepository.save(task);
        log.info("Task: {} is saved", task.toString());
    }

    public List<Task> findTaskByTitle(String title) {
        log.info("findTaskByTitle - title: {}", title);
        return tasksRepository.findByTitle(title);
    }
}
