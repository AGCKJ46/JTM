package net.ckj46.JTM.tasks.boundary;

import lombok.Data;

@Data
class UpdateTaskRequest {
    String title;
    String description;
    String projectTitle;
    int prio;
}
