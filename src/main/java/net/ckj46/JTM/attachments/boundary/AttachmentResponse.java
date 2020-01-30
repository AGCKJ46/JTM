package net.ckj46.JTM.attachments.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.ckj46.JTM.attachments.entity.Attachment;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AttachmentResponse {
    private Long id;
    private String fileName;
    private LocalDateTime createdAt;

    public static AttachmentResponse from(Attachment attachment){
        return new AttachmentResponse(attachment.getId(),
                                        attachment.getFileName(),
                                        attachment.getCreatedAt());
    }

}
