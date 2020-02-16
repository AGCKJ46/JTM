package net.ckj46.JTM.tasks.entity;

import net.ckj46.JTM.tags.entity.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    public void shouldAddTag(){
        // given
        Task task = new Task();
        Tag urgentTag = new Tag("pilne");

        // when
        task.addTag(urgentTag);

        // then
        assertTrue(task.getTags().contains(urgentTag));
    }
}