package net.ckj46.JTM;

import java.util.List;

public interface TasksRepository {
    void add(Task task);
    List<Task> fetchAll();
}
