package net.ckj46.JTM;

import lombok.Data;

@Data
class Task {
    private long id;
    private String title;
    private int prio;
    private String project;
}
