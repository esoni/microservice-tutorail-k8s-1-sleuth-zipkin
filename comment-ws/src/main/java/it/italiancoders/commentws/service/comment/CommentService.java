package it.italiancoders.commentws.service.comment;

import it.italiancoders.commentws.model.dto.Comment;
import it.italiancoders.commentws.model.dto.CommentRequest;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    void deleteAllByPostId(Long postId);
    void deleteById(Long id);
    void updateTextById(Long id, String text);
    Optional<Comment> findById(Long id);
    List<Comment> findAllByPostId(Long postId);
    Comment insertComment(Long postId, CommentRequest request);
}
