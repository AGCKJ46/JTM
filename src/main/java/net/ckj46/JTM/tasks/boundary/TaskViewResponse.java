package net.ckj46.JTM.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.ckj46.JTM.projects.entity.Project;
import net.ckj46.JTM.tags.entity.Tag;
import net.ckj46.JTM.tasks.entity.Task;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
public class TaskViewResponse {
    private Long id;
    private String title;
    private String description;
    private Project project;
    private int prio;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    // TODO private Set attachments;
    private Set<Tag> tags;

    public static TaskViewResponse from(Task task, Set<Tag> tags){
        StringBuilder result= new StringBuilder("");

        return new TaskViewResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getProject(),
                task.getPrio(),
                task.getCreatedAt(),
                task.getEditedAt(),
                tags);
    }
}
