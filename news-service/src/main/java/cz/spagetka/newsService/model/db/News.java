package cz.spagetka.newsService.model.db;

import cz.spagetka.newsService.model.db.common.BasicEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;


@Entity(name = "news")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"thumbnail", "version","creator","comments"})
public class News extends BasicEntity {
    @Id
    @SequenceGenerator(
            name = "newsId_sequence_generator",
            sequenceName = "newsId_sequence_generator",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "newsId_sequence_generator"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private long id;
    @Column(
            name = "title",
            nullable = false,
            length = 50
    )
    @Size(min = 3,max = 50,message = "Title can be between 3 to 50 chars!")
    @NotBlank
    private String title;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length=16777215)
    private byte[] thumbnail;

    @Column(name = "text",nullable = false, length = 512)
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
    private User creator;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "news",
            orphanRemoval = true
    )
    private List<Comment> comments;
}
