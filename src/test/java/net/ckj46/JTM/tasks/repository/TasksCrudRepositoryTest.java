package net.ckj46.JTM.tasks.repository;

import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.app.sys.Clock;
import net.ckj46.JTM.app.sys.SystemClock;
import net.ckj46.JTM.projects.entity.Project;
import net.ckj46.JTM.projects.repository.ProjectsCrudRepository;
import net.ckj46.JTM.tasks.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Slf4j
public class TasksCrudRepositoryTest {
    private Clock clock = new SystemClock();

    @Autowired
    private ProjectsCrudRepository projectsRepository;
    @Autowired
    private TasksCrudRepository tasksRepository;

    @Test
    public void shouldLoadEntity(){
        // given
        projectsRepository.save(new Project("TEST_PROJECT1","Project testowy"));
        Optional<Project> project = projectsRepository.findByTitle("TEST_PROJECT1");
        Task task1 = new Task("Test Task 1", "", 1, clock.time(), clock.time(), project.get());
        Task task2 = new Task("Test Task 2", "", 1, clock.time(), clock.time(), project.get());
        Task task3 = new Task("Test Task 3", "", 1, clock.time(), clock.time(), project.get());

        // when
        tasksRepository.save(task1);
        tasksRepository.save(task2);
        tasksRepository.save(task3);
        List<Task> tasks = tasksRepository.findAll();

        // then
        assertThat(tasks.size()).isEqualTo(3);
    }

    @Test
    public void findByTitle(){
        // given
        projectsRepository.save(new Project("TEST_PROJECT1","Project testowy"));
        Optional<Project> project = projectsRepository.findByTitle("TEST_PROJECT1");
        Task task1 = new Task("Test Task 1", "", 1, clock.time(), clock.time(), project.get());
        Task task2 = new Task("Test Task 2", "", 1, clock.time(), clock.time(), project.get());
        Task task3 = new Task("Test Task 3", "", 1, clock.time(), clock.time(), project.get());

        // when
        tasksRepository.save(task1);
        tasksRepository.save(task2);
        tasksRepository.save(task3);
        List<Task> tasks = tasksRepository.findByTitle("Test Task 1");

        // then
        assertThat(tasks.size()).isEqualTo(1);
    }

    @Test
    public void shouldLoadView(){
        // given
        projectsRepository.save(new Project("TEST_PROJECT1","Project testowy"));
        Optional<Project> project = projectsRepository.findByTitle("TEST_PROJECT1");
        Task task = new Task("Test Task 1", "", 1, clock.time(), clock.time(), project.get());

        // when
        tasksRepository.save(task);
        List<TaskView> taskView = tasksRepository.findAllBy();

        // then
        assertThat(taskView.size()).isEqualTo(1);
        assertThat(taskView.get(0).getTitle()).isEqualToIgnoringCase("Test Task 1");
    }
}