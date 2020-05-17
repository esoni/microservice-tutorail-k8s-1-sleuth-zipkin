package it.italiancoders.commentws.dao.comment;

import it.italiancoders.commentws.model.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository  extends JpaRepository<CommentEntity, Long> {
    void deleteAllByPostId(Long postId);
    void deleteById(Long id);
    List<CommentEntity> findAllByPostIdOrderByCreatedAtDesc(Long postId);
    @Modifying
    @Query("Update CommentEntity t SET t.text=:text WHERE t.id=:id")
    void updateTextById(@Param("id") Long id, @Param("text") String text);
}
