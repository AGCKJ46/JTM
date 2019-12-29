package net.ckj46.JTM.comments;

public enum CommentType {
    PROJECTS_COMMENT("P"), TASKS_COMMNET("T"), FILE_COMMNET("F");
    private String type;

    private CommentType(String type) {
        this.type = type;
    }
}
