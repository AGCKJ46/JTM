package net.ckj46.JTM.tasks.control;

import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.projects.control.ProjectsService;
import net.ckj46.JTM.projects.entity.Project;
import net.ckj46.JTM.tasks.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
class TasksServiceIntegrationTest {
    @Autowired
    private TasksService tasksService;

    @Autowired
    private ProjectsService projectsService;

    @Test
    public void entityVersionTest(){
        // given
        Project project = projectsService.addProject("TEST1", "");
        Task task = tasksService.addTask("Test Task 1", "", 1, project);

        // when
        Task task1 = tasksService.fetchTaskById(task.getId());
        Task task2 = tasksService.fetchTaskById(task.getId());

        task1.setTitle("new title 1");
        tasksService.saveTask(task1);

        task2.setTitle("new title 2");

        // then
        Exception exception = assertThrows(ObjectOptimisticLockingFailureException.class, () -> {
            tasksService.saveTask(task2);
        });
    }
}