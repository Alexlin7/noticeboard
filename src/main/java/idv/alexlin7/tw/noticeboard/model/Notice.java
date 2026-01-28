package idv.alexlin7.tw.noticeboard.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.proxy.HibernateProxy;

import com.fasterxml.uuid.Generators;

@Entity
@Table(name = "notice")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "announcer", nullable = false)
    private String announcer;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @OneToMany(
        mappedBy = "notice",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<NoticeImage> images = new ArrayList<>();

    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "commit")
    private String commit;

    @Column(name = "`index`") // Using backticks for 'index' as it's a SQL keyword
    private String index;

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            this.id = Generators.timeBasedEpochGenerator().generate();
        }
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Helper methods for managing the bidirectional relationship
    public void addImage(NoticeImage image) {
        images.add(image);
        image.setNotice(this);
    }

    public void removeImage(NoticeImage image) {
        images.remove(image);
        image.setNotice(null);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Notice notice = (Notice) o;
        return id != null && Objects.equals(id, notice.id);
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
