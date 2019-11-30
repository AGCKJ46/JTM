package net.ckj46.JTM.tasks.boundary;

import net.ckj46.JTM.tasks.entity.Task;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryTasksRepository implements TasksRepository {
    private final List<Task> repo = new LinkedList<>();

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
        return findById(id)
                .orElseThrow(()->new IllegalArgumentException("Task: "+id+" not found!"));
    }

    @Override
    public void deletingById(Long id) {
        findById(id)
                .ifPresent(repo::remove);
    }

    @Override
    public void update(Long id, String title, String description, String project, int prio, LocalDateTime editedAt) {
        findById(id).ifPresent( task -> {
                                            task.setTitle(title);
                                            task.setDescription(description);
                                            task.setProject(project);
                                            task.setPrio(prio);
                                            task.setEditedAt(editedAt);
                                        }
                                );
    }

    private Optional<Task> findById(Long id) {
        return repo.stream()
                .filter(task -> id.equals(task.getId()))
                .findFirst();
    }
}
