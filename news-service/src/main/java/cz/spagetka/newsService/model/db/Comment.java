package cz.spagetka.newsService.model.db;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
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
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "author_id",
            referencedColumnName = "id"
    )
    private User author;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "news_id",
            referencedColumnName = "id"
    )
    private News news;
}
