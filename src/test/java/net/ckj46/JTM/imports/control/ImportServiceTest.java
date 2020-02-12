package net.ckj46.JTM.imports.control;

import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.imports.boundary.ImportProject;
import net.ckj46.JTM.imports.boundary.ImportTask;
import net.ckj46.JTM.projects.control.ProjectsService;
import net.ckj46.JTM.tasks.control.TasksService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class ImportServiceTest {
    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private TasksService tasksService;

    @Autowired
    private ImportService importService;

    @Test
    public void shouldImportEntities() {

        int existingProjects = projectsService.fetchAll().size();
        int existingTasks = tasksService.fetchAll().size();

        // given
        ImportProject ip1 = new ImportProject("TEST PROJECT1", "Projekt testowy numer jeden");
        ImportTask it1p1 = new ImportTask("TASK p1.7", "", "TEST PROJECT1", 1);
        ImportTask it2p1 = new ImportTask("TASK P1.8", "", "TEST PROJECT1", 2);

        ImportProject ip2 = new ImportProject("TEST PROJECT2", "Projekt testowy numer dwa");
        ImportTask it1p2 = new ImportTask("TASK p2.1", "", "TEST PROJECT2", 1);
        ImportTask it2p2 = new ImportTask("TASK P2.2", "", "TEST PROJECT2", 2);
        ImportTask it3p2 = new ImportTask("TASK p2.3", "", "TEST PROJECT2", 1);
        ImportTask it4p2 = new ImportTask("TASK P2.4", "", "TEST PROJECT2", 2);


        // when
        importService.startImport(asList(ip1, ip2), asList(it1p1, it2p1, it1p2, it2p2, it3p2, it4p2));

        // then
        assertThat(projectsService.fetchAll().size()).isEqualTo(2+existingProjects);
        assertThat(tasksService.fetchAll().size()).isEqualTo(existingTasks+6);
    }

}