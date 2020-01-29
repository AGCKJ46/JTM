package net.ckj46.JTM.projects.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.app.exceptions.NotFoundException;
import net.ckj46.JTM.projects.entity.Project;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Slf4j
@Primary
@RequiredArgsConstructor
@Repository
public class AdaptedProjectsCrudRepository implements ProjectsRepository {
    private final ProjectsCrudRepository projectsCrudRepository;

    @Override
    public List<Project> fetchAll() {
        log.info("fetchAll");
        return StreamSupport.stream(projectsCrudRepository.findAll().spliterator(), false)
                .collect(toList());
    }

    @Override
    public Project fetchById(Long projectId) {
        log.info("fetchById - projectId: {}", projectId);
        Project project = projectsCrudRepository
                .findById(projectId)
                .orElseThrow(() -> new NotFoundException("Cannot find project with id: " + projectId));
        return project;
    }

    @Override
    public void deleteById(Long id) {
        log.info("deleteById - projectId: {}", id);
        projectsCrudRepository.deleteById(id);
    }

    @Override
    public void save(Project project) {
        log.info("save - project: {}",project.toString());
        projectsCrudRepository.save(project);
    }
}
