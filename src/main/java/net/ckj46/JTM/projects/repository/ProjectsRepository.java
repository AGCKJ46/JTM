package net.ckj46.JTM.projects.repository;

import net.ckj46.JTM.projects.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectsRepository {
    List<Project> fetchAll();
    Project fetchById(Long id);
    void deleteById(Long id);

    void save(Project project);

    Optional<Project> findByTitle(String title);
}
