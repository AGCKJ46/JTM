package net.ckj46.JTM.projects.repository;

import net.ckj46.JTM.app.sys.Clock;
import net.ckj46.JTM.app.sys.SystemClock;
import net.ckj46.JTM.projects.entity.Project;
import net.ckj46.JTM.tasks.entity.Task;
import net.ckj46.JTM.tasks.repository.TasksCrudRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AdaptedProjectsCrudRepositoryTest {
    private Clock clock = new SystemClock();

    @Autowired
    private ProjectsCrudRepository projectsRepository;
    @Autowired
    private TasksCrudRepository tasksRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void fetchAll() {
    }

    @Test
    void fetchById() {
    }

    @Test
    void deleteById() {
        // given
        projectsRepository.save(new Project("TEST_PROJECT1","Project testowy"));
        Optional<Project> project = projectsRepository.findByTitle("TEST_PROJECT1");
        Task task1 = new Task("Test Task 1", "", 1, clock.time(), clock.time(), project.get());
        Task task2 = new Task("Test Task 2", "", 1, clock.time(), clock.time(), project.get());
        Task task3 = new Task("Test Task 3", "", 1, clock.time(), clock.time(), project.get());

        // when
        projectsRepository.deleteById(project.get().getId());
        List<Task> tasks = tasksRepository.findAll(); // TODO znajdź taski per projekt

        // then
        assertThat(tasks.size()).isEqualTo(0);
    }

    @Test
    void save() {
    }

    @Test
    void findByTitle() {
    }
}