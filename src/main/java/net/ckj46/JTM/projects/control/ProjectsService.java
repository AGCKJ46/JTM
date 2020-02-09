package net.ckj46.JTM.projects.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.projects.entity.Project;
import net.ckj46.JTM.projects.repository.ProjectsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProjectsService {
    private final ProjectsRepository projectsRepository;

    public Project addProject(String title, String description) {
        log.info("Adding project...");
        Project project = new Project();
        project.setTitle(title);
        project.setDescription(description);
        project.setCreatedAt(LocalDateTime.now());
        project.setEditedAt(LocalDateTime.now());

        projectsRepository.save(project);
        log.info("Project is added");

        return project;
    }

    public Project fetchProjectById(Long id) {
        log.info("Fetching project by id: {}", id);
        return projectsRepository.fetchById(id);
    }

    public List<Project> filterAllByQuery(String query) {
        log.info("Fetching projects by query: {}", query);
        return projectsRepository.fetchAll()
                .stream()
                .filter(task -> task.getTitle().contains(query)
                        || task.getDescription().contains(query))
                .collect(Collectors.toList());
    }

    public List<Project> fetchAll() {
        log.info("Fetching all projects");
        return projectsRepository.fetchAll();
    }

    public void saveProject(Project project) {
        log.info("Updating project: {}", project.toString());
        projectsRepository.save(project);
        log.info("Project is updated");
    }

    public void deleteProjectById(Long projectId) {
        log.info("Deleting project: {}", projectId);
        projectsRepository.deleteById(projectId);
        log.info("Project is deleted");
    }

    public Project findByTitle(String title) {
        log.info("finding project by title: {}", title);
        return projectsRepository.findByTitle(title).get();
    }
}
