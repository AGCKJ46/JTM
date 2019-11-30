package net.ckj46.JTM.tasks.boundary;

import net.ckj46.JTM.tasks.entity.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface TasksRepository {
    void add(Task task);

    List<Task> fetchAll();

    Task fetchById(Long id);

    void deletingById(Long id);

    void update(Long id, String title, String description, String project, int prio, LocalDateTime editedAt);
}
