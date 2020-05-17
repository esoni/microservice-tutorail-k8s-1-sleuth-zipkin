package it.italiancoders.commentws.controller;

import it.italiancoders.commentws.model.dto.Comment;
import it.italiancoders.commentws.model.dto.CommentRequest;
import it.italiancoders.commentws.service.comment.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class CommentController {
    private CommentService commentService;
    @Value("${instance.instance-id}")
    private String instanceId;

    @Autowired
    CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    void log() {
        log.info("Operation executed By Istance [{}]", instanceId);
    }

    @RequestMapping(value = "/comments",
            method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Comment> insertComment(@RequestParam(value = "postId", required = true) final Long postId,
                                                 @Valid @RequestBody CommentRequest commentRequest) throws Exception {
        log();
        Comment comment = this.commentService.insertComment(postId, commentRequest);
        return ResponseEntity.ok(comment);
    }

    @RequestMapping(value = "/comments",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Comment>> insertComment(@RequestParam(value = "postId", required = true) final Long postId) throws Exception {
        log();
        List<Comment> comments = commentService.findAllByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @RequestMapping(value = "/comments",
            method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deleteAllCommentsOfPost(@RequestParam(value = "postId", required = true) final Long postId) throws Exception {
        log();
        commentService.deleteAllByPostId(postId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/comments/{id}",
            method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws Exception {
        log();
        commentService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/comments/{id}",
            method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateById(@PathVariable Long id, @Valid @RequestBody CommentRequest commentRequest) throws Exception {
        log();
        commentService.findById(id).orElseThrow((() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found")));
        commentService.updateTextById(id, commentRequest.getText());
        return ResponseEntity.noContent().build();
    }


}
