package net.ckj46.JTM.tags.boundary;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.tags.control.TagsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
@RequestMapping(path = "/api/tags")
@RestController
public class TagsControler {
    private TagsService tagsService;

    @PostConstruct
    void init() {
        tagsService.addTag("WAIT");
        tagsService.addTag("FIN");
        // INSERT INTO tags_tasks VALUES (1, 4);
    }

    @PostMapping
    public void addTag(HttpServletResponse response, @RequestBody CreateTagRequest tag) throws IOException {
        log.info("addTag: {}", tag.toString());
        tagsService.addTag(tag.getName());
        response.setStatus(HttpStatus.CREATED.value());
    }
}
