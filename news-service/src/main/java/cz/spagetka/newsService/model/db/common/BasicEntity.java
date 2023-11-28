package cz.spagetka.newsService.model.db.common;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@MappedSuperclass
public abstract class BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BASIC_SEQ_GENERATOR")
    @Column(name = "id", updatable = false)
    private long id;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Version
    private Long version;
}
