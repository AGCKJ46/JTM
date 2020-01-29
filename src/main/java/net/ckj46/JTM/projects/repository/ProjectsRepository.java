package net.ckj46.JTM.projects.repository;

import net.ckj46.JTM.projects.entity.Project;

import java.util.List;

public interface ProjectsRepository {
    List<Project> fetchAll();
    Project fetchById(Long id);
    void deleteById(Long id);

    void save(Project project);
}
