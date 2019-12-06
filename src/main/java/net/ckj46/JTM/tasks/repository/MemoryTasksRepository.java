package net.ckj46.JTM.tasks.repository;

import net.ckj46.JTM.attachments.StorageService;
import net.ckj46.JTM.app.exceptions.NotFoundException;
import net.ckj46.JTM.tasks.entity.Task;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryTasksRepository implements TasksRepository {
    private final List<Task> repo = new LinkedList<>();
    private final StorageService fileSystemStorageService;

    public MemoryTasksRepository(StorageService fileSystemStorageService) {
        this.fileSystemStorageService = fileSystemStorageService;
    }

    @Override
    public void add(Task task) {
        repo.add(task);
    }

    @Override
    public List<Task> fetchAll() {
        return repo;
    }

    @Override
    public Task fetchById(Long id) {
        Task task = findById(id)
                .orElseThrow(()->new NotFoundException("Task with id: "+id+" not found!"));
        task.setAttachmentsList(fileSystemStorageService.fetchAllAttachments(id));
        return task;
    }

    @Override
    public void update(Long id, String title, String description, String project, int prio, LocalDateTime editedAt) {
        Task task = findById(id)
                .orElseThrow(()-> new NotFoundException("Task with id: "+id+" not found!"));

        task.setTitle(title);
        task.setDescription(description);
        task.setProject(project);
        task.setPrio(prio);
        task.setEditedAt(editedAt);
    }

    @Override
    public void deleteById(Long id) {
        Task task = findById(id)
                .orElseThrow(()-> new NotFoundException("Task with id: "+id+" not found!"));
        repo.remove(task);
    }

    private Optional<Task> findById(Long id) {
        return repo.stream()
                .filter(task -> id.equals(task.getId()))
                .findFirst();
    }
}
