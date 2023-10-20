package cz.spagetka.newsService.repository;

import cz.spagetka.newsService.model.db.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News,Long> {
}
