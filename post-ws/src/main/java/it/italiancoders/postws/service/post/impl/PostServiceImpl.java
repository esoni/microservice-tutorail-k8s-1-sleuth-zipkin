package it.italiancoders.postws.service.post.impl;

import it.italiancoders.postws.dao.post.PostRepository;
import it.italiancoders.postws.model.dto.Comment;
import it.italiancoders.postws.model.dto.Post;
import it.italiancoders.postws.model.dto.PostRequest;
import it.italiancoders.postws.model.entity.PostEntity;
import it.italiancoders.postws.service.comment.CommentServiceClient;
import it.italiancoders.postws.service.post.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper modelMapper;
    private  CommentServiceClient commentServiceClient;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper, CommentServiceClient commentServiceClient) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.commentServiceClient = commentServiceClient;
    }

    @Override
    @Transactional
    public Post insert(PostRequest request) {
        PostEntity postEntity = modelMapper.map(request, PostEntity.class);
        postEntity.setCreatedAt(OffsetDateTime.now());
        postEntity = postRepository.saveAndFlush(postEntity);
        return modelMapper.map(postEntity, Post.class);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository
                .findById(id)
                .map((pr) -> modelMapper.map(pr, Post.class));

    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public void replacePostText(Long id, String text) {
        postRepository.updateTextById(id, text);
    }

    @Override
    public List<Post> findAll() {
        return postRepository
                .findAll().stream()
                .map((pr) -> modelMapper.map(pr, Post.class))
                .collect(Collectors.toList());
    }

}
