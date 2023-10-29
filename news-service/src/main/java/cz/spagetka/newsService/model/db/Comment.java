package cz.spagetka.newsService.model.db;

import cz.spagetka.newsService.model.db.common.BasicEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Entity(name = "Comment")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"version","author","news"})
public class Comment extends BasicEntity {
    @Id
    @SequenceGenerator(
            name = "commentId_sequence_generator",
            sequenceName = "commentId_sequence_generator",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "commentId_sequence_generator"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private long id;

    @Column(name = "text",nullable = false)
    @NotBlank
    private String text;

    @Version
    private Long version;

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
}
