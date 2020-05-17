package it.italiancoders.postws.service.comment;

import it.italiancoders.postws.model.dto.Comment;
import it.italiancoders.postws.model.dto.CommentRequest;
import it.italiancoders.postws.model.dto.PostRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "comment-ws", url = "${comments_ws.ip}:${comments_ws.port}/")
public interface CommentServiceClient {
    @GetMapping("/comments")
    List<Comment> getComments(@RequestParam Long postId);
    @PostMapping("/comments")
    Comment insertComment(@RequestParam Long postId, @RequestBody CommentRequest commentRequest);
    @DeleteMapping("/comments")
    void deleteAllCommentsByPostId(@RequestParam(value = "postId", required = true) final Long postId);
}
