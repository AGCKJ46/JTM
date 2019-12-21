package net.ckj46.JTM.tasks.repository;

import net.ckj46.JTM.attachments.repository.StorageService;
import net.ckj46.JTM.app.exceptions.NotFoundException;
import net.ckj46.JTM.tasks.entity.Task;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MemoryTasksRepository implements TasksRepository {
    private final Set<Task> repo = new HashSet<>();
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
        return repo
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public Task fetchById(Long id){
        Task task = findById(id)
                .orElseThrow(()->new NotFoundException("Task with id: "+id+" not found!"));
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

    @Override
    public void save(Task task) {
        repo.add(task);
    }

    private Optional<Task> findById(Long id) {
        return repo.stream()
                .filter(task -> id.equals(task.getId()))
                .findFirst();
    }

    private Task fetchAllAttachments(Task task) throws IOException {
        return fetchById(task.getId());
    }
}
