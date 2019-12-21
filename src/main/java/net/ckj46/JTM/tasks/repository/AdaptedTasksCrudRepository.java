package net.ckj46.JTM.tasks.repository;

import lombok.RequiredArgsConstructor;
import net.ckj46.JTM.app.exceptions.NotFoundException;
import net.ckj46.JTM.tasks.entity.Task;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.naming.CannotProceedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
@Repository
@Primary
public class AdaptedTasksCrudRepository implements TasksRepository {
    private final TasksCrudRepository tasksCrudRepository;

    // TODO Dariusz Mydlarz Możesz też pójść o krok dalej i zamiast rzucać wyjątkiem, zrobić sobie enum np "CrudResult" z wartościami: "NotFound, Saved, Failed" itd i zwracać ten enum zamiast voida z metod
    @Override
    public void add(Task task) {
        tasksCrudRepository.save(task);
    }

    @Override
    public List<Task> fetchAll() {
        return StreamSupport.stream(tasksCrudRepository.findAll().spliterator(), false)
                .collect(toList());
    }

    @Override
    public Task fetchById(Long id) {
        return tasksCrudRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find task with id: " + id));
    }

    @Override
    public void update(Long id, String title, String description, String project, int prio, LocalDateTime editedAt) {
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
        tasksCrudRepository
                .findById(id)
                .ifPresentOrElse(task -> tasksCrudRepository.deleteById(task.getId()), () -> {
                    throw new NotFoundException("Cannot find task with id: " + id);
                });
    }

    @Override
    public void save(Task task) {
        tasksCrudRepository.save(task);
    }

}
