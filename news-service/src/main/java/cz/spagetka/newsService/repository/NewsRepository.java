package cz.spagetka.newsService.repository;

import cz.spagetka.newsService.model.db.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News,Long> {

    @Query("""
        SELECT n
        FROM news n
        WHERE (:titleOrAuthor IS NULL OR n.title LIKE %:titleOrAuthor%) OR (:titleOrAuthor IS NULL OR n.creator.authId LIKE %:titleOrAuthor%)
        ORDER BY n.createdAt
    """)
    Page<News> findNewsByTitleOrAuthorId(
            @Param("titleOrAuthor") String titleOrAuthor,
            Pageable pageable
    );
}
