package net.ckj46.JTM.imports.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ImportTask {
    private String title;
    private String description;
    private String projectTitle;
    private int prio;
}
