package net.ckj46.JTM.tasks.boundary;

import lombok.Data;

@Data
class CreateTaskRequest {
    String title;
    String description;
    String project;
    int prio;
    Long tagId;
}
