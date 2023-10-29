package cz.spagetka.newsService.repository;

import cz.spagetka.newsService.model.db.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    Page<Comment> findAllByNews_IdOrderByCreatedAtDesc(long newsId, Pageable pageable);

    Optional<Comment> findByNews_IdAndAuthor_AuthId(long newsId,String authId);
}
