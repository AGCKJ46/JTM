package net.ckj46.JTM.imports.control;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.app.exceptions.NotFoundException;
import net.ckj46.JTM.imports.boundary.ImportProject;
import net.ckj46.JTM.imports.boundary.ImportTask;
import net.ckj46.JTM.projects.control.ProjectsService;
import net.ckj46.JTM.projects.entity.Project;
import net.ckj46.JTM.tasks.control.TasksService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class ImportService {
    private final TasksService tasksService;
    private final ProjectsService projectsService;

    @Transactional
    public void startImport(List<ImportProject> importProjects, List<ImportTask> importTasks){

        for (ImportProject importProject: importProjects) {
            log.info("Imported project: {}", importProject.toString());
            final Project project = projectsService.addProject(importProject.getTitle(), importProject.getDescription());
            importTasks
                    .stream()
                    .filter(t->t.getProjectTitle().equals(project.getTitle()))
                    .forEach(t -> tasksService.addTask(t.getTitle(), t.getDescription(), t.getPrio(), project));
        }
    }
}
