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
class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private String project;
    private int prio;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    // TODO private Set attachments;
    private List<TagsResponse> tags;

    public static TaskResponse from(Task task, Set<Tag> tags){
        return new TaskResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getProject(),
                        task.getPrio(),
                        task.getCreatedAt(),
                        task.getEditedAt(),
                        tags.stream()
                                .map((tag)->TagsResponse.from(tag))
                                .collect(Collectors.toList()));
    }
}
