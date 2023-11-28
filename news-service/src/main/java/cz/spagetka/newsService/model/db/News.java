package cz.spagetka.newsService.model.db;

import cz.spagetka.newsService.model.db.common.BasicEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity(name = "news")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"thumbnail", "creator","comments"})
@SequenceGenerator(name = "BASIC_SEQ_GENERATOR", sequenceName = "NEWS_SEQ", allocationSize = 1)
public class News extends BasicEntity {
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

    @ManyToMany(
            mappedBy = "likedNews",
            fetch = FetchType.LAZY
    )
    private Set<User> likedByUsers = new HashSet<>();

    @ManyToMany(
            mappedBy = "dislikedNews",
            fetch = FetchType.LAZY
    )
    private Set<User> dislikedByUsers = new HashSet<>();

    @PreRemove
    private void removeAll(){
        for (User user: this.getLikedByUsers()) {
            this.removeLikedUser(user);
        }
        for (User user: this.getDislikedByUsers()) {
            this.removeDislikedUser(user);
        }
    }


    public void addComment(@NotNull Comment comment){
        this.comments.add(comment);
        comment.setNews(this);
    }

    public void removeComment(@NotNull Comment comment){
        this.comments.remove(comment);
        comment.setNews(null);
    }

    public void addLikedUser(@NotNull User user){
        this.likedByUsers.add(user);
        user.getLikedNews().add(this);
    }

    public void removeLikedUser(@NotNull User user){
        this.likedByUsers.remove(user);
        user.getLikedNews().remove(this);
    }

    public void addDislikedUser(@NotNull User user){
        this.dislikedByUsers.add(user);
        user.getDislikedNews().add(this);
    }

    public void removeDislikedUser(@NotNull User user){
        this.dislikedByUsers.remove(user);
        user.getDislikedNews().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News )) return false;
        return super.getId() == (((News) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
