package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "flashcards")
@Getter @Setter @NoArgsConstructor
public class FlashCard {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, columnDefinition = "TEXT", name = "front")
    private String front;

    @Column(nullable = false, columnDefinition = "TEXT", name = "back")
    private String back;

    @Column(columnDefinition = "TEXT")
    private String example;

    @Column(length = 100)
    private String category;

    @Column(length = 50)
    private String level;

    @Column(name = "is_system", nullable = false)
    private Boolean isSystem = false;

    // Optional user who created this flashcard (null if it's a system card)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;
}
