package net.ckj46.JTM.tags.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.ckj46.JTM.tags.entity.Tag;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TagsResponse {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;

    public static TagsResponse from(Tag tag){
        return new TagsResponse(tag.getId(), tag.getName(), tag.getCreatedAt(), tag.getEditedAt());
    }
}
