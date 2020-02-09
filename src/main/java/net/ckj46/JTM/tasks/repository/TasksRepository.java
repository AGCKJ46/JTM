package net.ckj46.JTM.tasks.repository;

import net.ckj46.JTM.projects.entity.Project;
import net.ckj46.JTM.tasks.entity.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface TasksRepository {
    void add(Task task);

    List<Task> fetchAll();

    Task fetchById(Long id);

    List<Task> findByTitle(String title);

    void update(Long id, String title, String description, int prio, LocalDateTime editedAt, Project project);

    void deleteById(Long id);

    void save(Task task);
}
