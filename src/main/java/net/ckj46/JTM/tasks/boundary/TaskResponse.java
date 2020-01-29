package net.ckj46.JTM.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.ckj46.JTM.attachments.boundary.AttachmentResponse;
import net.ckj46.JTM.attachments.entity.Attachment;
import net.ckj46.JTM.tags.boundary.TagsResponse;
import net.ckj46.JTM.tags.entity.Tag;
import net.ckj46.JTM.tasks.entity.Task;


import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public
class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private Long projectId;
    private int prio;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    private Set<AttachmentResponse> attachments;
    private Set<TagsResponse> tags;

    public static TaskResponse from(Task task, Set<Tag> tags, Set<Attachment> attachments){
        return new TaskResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getProjectId(),
                        task.getPrio(),
                        task.getCreatedAt(),
                        task.getEditedAt(),
                        attachments.stream()
                                .map((attachment)-> AttachmentResponse.from(attachment))
                                .collect(Collectors.toSet()),
                        tags.stream()
                                .map((tag)->TagsResponse.from(tag))
                                .collect(Collectors.toSet()));
    }
}
