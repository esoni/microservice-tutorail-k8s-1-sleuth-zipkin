package it.italiancoders.commentws.service.comment.impl;

import it.italiancoders.commentws.dao.comment.CommentRepository;
import it.italiancoders.commentws.model.dto.Comment;
import it.italiancoders.commentws.model.dto.CommentRequest;
import it.italiancoders.commentws.model.entity.CommentEntity;
import it.italiancoders.commentws.service.comment.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private ModelMapper modelMapper;

    @Autowired
    CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void deleteAllByPostId(Long postId) {
        commentRepository.deleteAllByPostId(postId);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);

    }

    @Override
    public void updateTextById(Long id, String text) {
        commentRepository.updateTextById(id, text);

    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository
                .findById(id)
                .map((pr) -> modelMapper.map(pr, Comment.class));
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        return commentRepository.findAllByPostIdOrderByCreatedAtDesc(postId)
                .stream()
                .map((c) -> modelMapper.map(c, Comment.class))
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public Comment insertComment(Long postId, CommentRequest request) {
        CommentEntity commentEntity = modelMapper.map(request, CommentEntity.class);
        commentEntity.setPostId(postId);
        commentEntity.setCreatedAt(OffsetDateTime.now());
        commentEntity = commentRepository.saveAndFlush(commentEntity);
        return modelMapper.map(commentEntity, Comment.class);
    }
}
