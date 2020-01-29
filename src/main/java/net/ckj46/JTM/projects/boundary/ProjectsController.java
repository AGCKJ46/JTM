package net.ckj46.JTM.projects.boundary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.app.exceptions.NotFoundException;
import net.ckj46.JTM.projects.control.ProjectsService;
import net.ckj46.JTM.projects.entity.Project;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping(path = "/api/projects")
@RestController
@RequiredArgsConstructor
// @CrossOrigin
public class ProjectsController {
    private final ProjectsService projectsService;

    @PostConstruct
    void init() throws IOException {
        log.info("init");
        projectsService.addProject("KURS SPRINGA", "");
        projectsService.addProject("DOM", "wszystkie zadania związane z naprawami, remontami w domu");
        projectsService.addProject("FIN", "");
    }

    @PostMapping
    public void addProject(HttpServletResponse response, @RequestBody CreateProjectRequest project) throws IOException {
        log.info("Adding new project: {}", project.toString());
        projectsService.addProject(project.getTitle(), project.getDescription());
        response.setStatus(HttpStatus.CREATED.value());
    }

    @GetMapping(path = "/{id}")
    public ProjectResponse getProjectById(HttpServletResponse response, @PathVariable Long id) throws IOException {
        log.info("Fetching a project: {}", id);
        ProjectResponse projectResponse = null;
        try {
            Project project  = projectsService.fetchProjectById(id);
            projectResponse = ProjectResponse.from(project);
            response.setStatus(HttpStatus.OK.value());
        } catch (NotFoundException e) {
            log.error("Unable to find a project: {} - error: {}", id, e.getMessage());
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
        return projectResponse;
    }

    @GetMapping
    public List<ProjectResponse> getTasks(HttpServletResponse response, @RequestParam Optional<String> query) throws IOException {
        log.info("Fetching all projects with query: {}", query);

        List<ProjectResponse> projectsResponseList = null;
        try {
            projectsResponseList = query
                    .map(projectsService::filterAllByQuery)
                    .orElseGet(projectsService::fetchAll)
                    .stream()
                    .map(project->ProjectResponse.from(project))
                    .collect(Collectors.toList());

            response.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }

        return projectsResponseList;
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity updateProject(@PathVariable Long id, @RequestBody CreateProjectRequest projectRequest) throws IOException {
        log.info("Updating project: {}", id);
        Project project = projectsService.fetchProjectById(id);

        if(!projectRequest.getTitle().isEmpty()){
            project.setTitle(projectRequest.getTitle());
            log.info("  Project title is updeted by: {}", projectRequest.getTitle());
        }

        if(!projectRequest.getDescription().isEmpty()){
            project.setDescription(projectRequest.getDescription());
            log.info("  Project description is updeted by: {}", projectRequest.getDescription());
        }

        projectsService.saveProject(project);

        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity getDeleteById(@PathVariable Long id){
        log.info("Deleting a project: {}", id);
        projectsService.deleteTaskById(id);
        log.info("Project: {} is deleted!", id);
        return ResponseEntity
                .noContent()
                .build();
    }

}
