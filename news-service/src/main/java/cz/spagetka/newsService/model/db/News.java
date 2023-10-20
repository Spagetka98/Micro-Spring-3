package cz.spagetka.newsService.model.db;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "news")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News {
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
    @Column(name="thumbnail_img")
    private byte[] thumbnail;

    @Column(name = "text",nullable = false, length = 512)
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
    private User creator;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "news",
            orphanRemoval = true
    )
    private List<Comment> comments;
}
