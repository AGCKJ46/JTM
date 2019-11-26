package net.ckj46.JTM;

import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

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
        return repo.stream()
                .filter(task->id.equals(task.getId()))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("Task: "+id+" not found!"));
    }

    @Override
    public void deletingById(Long id) {
        repo.stream()
                .filter(task->id.equals(task.getId()))
                .findFirst()
                .ifPresent(repo::remove);
    }
}
