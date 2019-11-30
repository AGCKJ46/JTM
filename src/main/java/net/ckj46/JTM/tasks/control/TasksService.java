package net.ckj46.JTM.tasks.control;

import net.ckj46.JTM.Clock;
import net.ckj46.JTM.tasks.boundary.TasksRepository;
import net.ckj46.JTM.tasks.entity.Task;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class TasksService {
    private final TasksRepository tasksRepository;
    private final Clock clock;
    private final AtomicLong nextTaskId = new AtomicLong(0L);

    public TasksService(TasksRepository tasksRepository, Clock clock) {
        this.tasksRepository = tasksRepository;
        this.clock = clock;
    }

    public void addTask(String title, String description, String project, int prio) {
        tasksRepository.add(
                new Task(
                        nextTaskId.getAndIncrement(),
                        title,
                        description,
                        project,
                        prio,
                        clock.time(),
                        clock.time()
                )
        );
    }

    public void updateTask(Long id, String title, String description, String project, int prio) {
        tasksRepository.update(id, title, description, project, prio, clock.time());
    }
}
