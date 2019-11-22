package net.ckj46.JTM;

import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class MemoryTasksRepository implements TasksRepository {
    private final List<Task> repo = new LinkedList<Task>();

    @Override
    public void add(Task task) {
        repo.add(task);
    }

    @Override
    public List<Task> fetchAll() {
        return repo;
    }
}
