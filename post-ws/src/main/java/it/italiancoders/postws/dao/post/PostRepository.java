package it.italiancoders.postws.dao.post;

import it.italiancoders.postws.model.dto.Post;
import it.italiancoders.postws.model.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository  extends JpaRepository<PostEntity, Long> {
    void deleteById(Long id);
    Optional<PostEntity> findById(Long id);
    List<PostEntity> findAllByOwner(String owner);
    void deleteByOwner(String owner);
    @Modifying
    @Query("Update PostEntity t SET t.text=:text WHERE t.id=:id")
    void updateTextById(@Param("id") Long id, @Param("text") String text);
}
