package net.ckj46.JTM.tasks.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.app.exceptions.NotFoundException;
import net.ckj46.JTM.projects.entity.Project;
import net.ckj46.JTM.tasks.entity.Task;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Slf4j
@Primary
@RequiredArgsConstructor
@Repository
public class AdaptedTasksCrudRepository implements TasksRepository {
    private final TasksCrudRepository tasksCrudRepository;

    // TODO Dariusz Mydlarz Możesz też pójść o krok dalej i zamiast rzucać wyjątkiem, zrobić sobie enum np "CrudResult" z wartościami: "NotFound, Saved, Failed" itd i zwracać ten enum zamiast voida z metod
    @Override
    public void add(Task task) {
        tasksCrudRepository.save(task);
    }

    @Override
    public List<Task> fetchAll() {
        return StreamSupport
                .stream(tasksCrudRepository.findAll().spliterator(), false)
                .collect(toList());
    }

    @Override
    public Task fetchById(Long id) {
        log.info("fetchTaskById - taskId: {}", id);
        Task task = tasksCrudRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find task with id: " + id));
        return task;
    }

    @Override
    public void update(Long id, String title, String description, int prio, LocalDateTime editedAt, Project project) {
        tasksCrudRepository
                .findById(id)
                .map(task -> {
                    task.setTitle(title);
                    task.setDescription(description);
                    task.setProject(project);
                    task.setPrio(prio);
                    task.setEditedAt(editedAt);
                    return task;
                })
                .ifPresentOrElse(task -> tasksCrudRepository.save(task), () -> {
                    throw new NotFoundException("Cannot find task with id: " + id);
                });
    }

    @Override
    public void deleteById(Long id) {
        log.info("Task {} is deleting ...", id);

        tasksCrudRepository
                .findById(id)
                .ifPresentOrElse(task -> tasksCrudRepository.deleteById(task.getId()), () -> {
                    throw new NotFoundException("Cannot find task with id: " + id);
                });

        log.info("Task {} is deleted ...", id);
    }

    @Override
    public void save(Task task) {
        log.info("Saving a task: {}", task.toString());
        tasksCrudRepository.save(task);
        log.info("Task: {} is saved", task.toString());
    }

    @Override
    public List<Task> findByTitle(String title) {
        return tasksCrudRepository.findByTitle(title);
    }

}
