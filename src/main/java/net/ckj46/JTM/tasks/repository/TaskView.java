package net.ckj46.JTM.tasks.repository;

import java.time.LocalDateTime;

interface TaskView {
    Long getId();
    String getTitle();
    String getDescription();
    Long getProjectId();
    int getPrio();
    LocalDateTime getCreatedAt();
    LocalDateTime getEditedAt();
}
