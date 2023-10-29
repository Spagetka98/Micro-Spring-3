package cz.spagetka.newsService.model.db;

import cz.spagetka.newsService.model.db.common.BasicEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;


@Entity(name = "user")
@Table(indexes = {
        @Index(name = "idx_auth_id",columnList = "auth_id",unique = true)
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"version", "comments","news"})
public class User extends BasicEntity {
    @Id
    @SequenceGenerator(
            name = "userId_sequence_generator",
            sequenceName = "userId_sequence_generator",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "userId_sequence_generator"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private long id;

    @Column(
            name = "auth_id",
            updatable = false
    )
    @NotBlank
    private String authId;

    @Version
    private Long version;

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<Comment> comments;

    @OneToMany(
            mappedBy = "creator",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<News> news;
}
