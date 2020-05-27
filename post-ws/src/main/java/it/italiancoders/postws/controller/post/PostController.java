package it.italiancoders.postws.controller.post;

import feign.FeignException;
import it.italiancoders.postws.model.dto.Comment;
import it.italiancoders.postws.model.dto.CommentRequest;
import it.italiancoders.postws.model.dto.Post;
import it.italiancoders.postws.model.dto.PostRequest;
import it.italiancoders.postws.service.comment.CommentServiceClient;
import it.italiancoders.postws.service.post.PostService;
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
public class PostController {

    private PostService postService;
    private CommentServiceClient commentServiceClient;

    @Value("${instance.instance-id}")
    private String instanceId;

    void log() {
        log.info("Operation executed By Istance [{}]", instanceId);
    }

    @Autowired
    PostController(PostService postService, CommentServiceClient commentServiceClient) {
        this.postService = postService;
        this.commentServiceClient = commentServiceClient;
    }

    @RequestMapping(value = "/posts",
            method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Post> insertPost(@Valid @RequestBody PostRequest postRequest) throws Exception {
        log();
        Post post = this.postService.insert(postRequest);
        return ResponseEntity.ok(post);
    }

    @RequestMapping(value = "/posts/{id}",
            method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updatePost(@PathVariable Long id, @Valid @RequestBody PostRequest postRequest) throws Exception {
        log();
        this.postService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        this.postService.replacePostText(id, postRequest.getText());
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/posts/{id}",
            method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deletePost(@PathVariable Long id) throws Exception {
        log();
        this.postService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        try {
            commentServiceClient.deleteAllCommentsByPostId(id);
        } catch (FeignException exc) {
            log.error("Exception during comment del of post [{}]: [{}]", id, exc);
        }
        this.postService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/posts/{id}",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> findById(@PathVariable Long id) throws Exception {
        log();
        Post post = this.postService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        return ResponseEntity.ok(post);
    }

    @RequestMapping(value = "/posts",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> findAll() throws Exception {
        log();
        List<Post> posts = this.postService.findAll();
        return ResponseEntity.ok(posts);
    }

    @RequestMapping(value = "/posts/{id}/comments",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Comment>> findCommentOfPost(@PathVariable Long id) throws Exception {
        log();
        this.postService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        List<Comment> comments = null;
        try {
            comments = commentServiceClient.getComments(id);
        } catch (FeignException exc) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exc.getMessage());
        }
        return ResponseEntity.ok(comments);
    }

    @RequestMapping(value = "/posts/{id}/comments",
            method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Comment> inserComment(@PathVariable Long id, @Valid @RequestBody CommentRequest commentRequest) throws Exception {
        log();
        this.postService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        log.info("before to call Comment-ws");
        Comment comment = null;
        try {
            comment = commentServiceClient.insertComment(id, commentRequest);
        } catch (FeignException exc) {
            log.error("Exception during insert comment [{}] of post [{}]: [{}]", commentRequest, id, exc);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exc.getMessage());
        }
        log.info("after call Comment-ws");
        return ResponseEntity.ok(comment);
    }
}
