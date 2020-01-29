package net.ckj46.JTM.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.ckj46.JTM.tags.boundary.TagsResponse;
import net.ckj46.JTM.tags.entity.Tag;
import net.ckj46.JTM.tasks.entity.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class TaskViewResponse {
    private Long id;
    private String title;
    private String description;
    private Long projectId;
    private int prio;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    // TODO private Set attachments;
    private Set<Tag> tags;

    public static TaskViewResponse from(Task task, Set<Tag> tags){
        StringBuilder result= new StringBuilder("");

        /*
        for (Tag tag: tags) {
            result.append(tag.getName()+" ");
        }
        */

        return new TaskViewResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getProjectId(),
                task.getPrio(),
                task.getCreatedAt(),
                task.getEditedAt(),
                tags);
    }
}
