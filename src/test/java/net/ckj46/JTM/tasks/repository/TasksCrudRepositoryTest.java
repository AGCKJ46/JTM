package net.ckj46.JTM.tasks.repository;

import net.ckj46.JTM.app.sys.Clock;
import net.ckj46.JTM.app.sys.SystemClock;
import net.ckj46.JTM.tasks.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TasksCrudRepositoryTest {

    private Clock clock = new SystemClock();

    @Autowired
    private TasksCrudRepository tasksRepository;

    @Test
    public void shouldLoadEntity(){
        // given
        Task task = new Task("Kupić lodówkę", "", "Dom", 1, clock.time(), clock.time());

        // when
        tasksRepository.save(task);
        List<Task> tasks = tasksRepository.findAll();

        // then
        assertThat(tasks.size()).isEqualTo(1);
    }

    @Test
    public void findByTitle(){
        // given
        Task task = new Task("Kupić lodówkę", "", "Dom", 1, clock.time(), clock.time());

        // when
        tasksRepository.save(task);
        List<Task> tasks = tasksRepository.findByTitle("Kupić lodówkę");

        // then
        assertThat(tasks.size()).isEqualTo(1);
    }

    @Test
    public void shouldLoadView(){
        // given
        Task task = new Task("Kupić lodówkę", "", "Dom", 1, clock.time(), clock.time());

        // when
        tasksRepository.save(task);
        List<TaskView> taskView = tasksRepository.findAllBy();

        // then
        assertThat(taskView.size()).isEqualTo(1);
        assertThat(taskView.get(0).getTitle()).isEqualToIgnoringCase("Kupić lodówkę");
    }
}