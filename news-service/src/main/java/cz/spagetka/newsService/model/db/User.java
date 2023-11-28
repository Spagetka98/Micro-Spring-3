package cz.spagetka.newsService.model.db;

import cz.spagetka.newsService.model.db.common.BasicEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity(name = "user")
@Table(indexes = {
        @Index(name = "idx_auth_id",columnList = "auth_id",unique = true)
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"comments","news"})
@SequenceGenerator(name = "BASIC_SEQ_GENERATOR", sequenceName = "USER_SEQ", allocationSize = 1)
public class User extends BasicEntity {
    @Column(
            name = "auth_id",
            updatable = false
    )
    @NotBlank
    private String authId;

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

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "news_liked_by_users",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "news_id")
    )
    private Set<News> likedNews = new HashSet<>();

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "news_disliked_by_users",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "news_id")
    )
    private Set<News> dislikedNews = new HashSet<>();

    @PreRemove
    private void removeAll(){
        for (News news: this.getLikedNews()) {
            this.removeLikedNews(news);
        }
        for (News news: this.getDislikedNews()) {
            this.removeDislikedNews(news);
        }
    }

    public void addComment(@NotNull Comment comment){
        this.comments.add(comment);
        comment.setAuthor(this);
    }

    public void removeComment(@NotNull Comment comment){
        this.comments.remove(comment);
        comment.setAuthor(null);
    }

    public void addNews(@NotNull News news){
        this.news.add(news);
        news.setCreator(this);
    }

    public void removeNews(@NotNull News news){
        this.news.remove(news);
        news.setCreator(null);
    }

    public void addLikedNews(@NotNull News news){
        this.likedNews.add(news);
        news.getLikedByUsers().add(this);
    }

    public void removeLikedNews(@NotNull News news){
        this.likedNews.remove(news);
        news.getLikedByUsers().remove(this);
    }

    public void addDislikedNews(@NotNull News news){
        this.dislikedNews.add(news);
        news.getDislikedByUsers().add(this);
    }

    public void removeDislikedNews(@NotNull News news){
        this.dislikedNews.remove(news);
        news.getDislikedByUsers().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User )) return false;
        return super.getId() == (((User) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
