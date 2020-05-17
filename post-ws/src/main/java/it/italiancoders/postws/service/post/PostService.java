package it.italiancoders.postws.service.post;

import it.italiancoders.postws.model.dto.Comment;
import it.italiancoders.postws.model.dto.Post;
import it.italiancoders.postws.model.dto.PostRequest;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post insert(PostRequest request);
    Optional<Post> findById(Long id);
    void deleteById(Long id);
    void replacePostText(Long id, String text);
    List<Post> findAll();
}
