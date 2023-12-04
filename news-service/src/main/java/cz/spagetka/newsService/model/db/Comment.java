package cz.spagetka.newsService.model.db;

import cz.spagetka.newsService.model.db.common.BasicEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Entity(name = "Comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
        @Index(name = "idx_author_news",columnList = "author_id,news_id",unique = true)
})
@Builder
@ToString(exclude = {"author","news"})
@SequenceGenerator(name = "BASIC_SEQ_GENERATOR", sequenceName = "COMMENT_SEQ", allocationSize = 1)
public class Comment extends BasicEntity {
    @Column(name = "text",nullable = false)
    @NotBlank
    private String text;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "author_id",
            referencedColumnName = "id"
    )
    private User author;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "news_id",
            referencedColumnName = "id"
    )
    private News news;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment )) return false;
        return super.getId() == (((Comment) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
