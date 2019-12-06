package net.ckj46.JTM.tasks.control;

import net.ckj46.JTM.app.sys.Clock;
import net.ckj46.JTM.tasks.repository.TasksRepository;
import net.ckj46.JTM.tasks.entity.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TasksService {
    private final TasksRepository tasksRepository;
    private final Clock clock;
    private final AtomicLong nextTaskId = new AtomicLong(0L);

    public TasksService(TasksRepository tasksRepository, Clock clock) {
        this.tasksRepository = tasksRepository;
        this.clock = clock;
    }

    public Task addTask(String title, String description, String project, int prio) {
        Task task = new Task(
                nextTaskId.getAndIncrement(),
                title,
                description,
                project,
                prio,
                clock.time(),
                clock.time(),
                null
        );
        tasksRepository.add(task);
        return task;
    }

    public void updateTask(Long id, String title, String description, String project, int prio) {
        tasksRepository.update(id, title, description, project, prio, clock.time());
    }

    public List<Task> fetchAll(){
        return tasksRepository.fetchAll();
    }

    public List<Task> filterAllByQuery(String query){
        return tasksRepository.fetchAll()
                .stream()
                .filter(task -> task.getTitle().contains(query)
                        || task.getDescription().contains(query))
                .collect(Collectors.toList());
    }

    public void deleteTask(Long id) {
        tasksRepository.deleteById(id);
        // TODO add delete of tasks attachments
    }
}
