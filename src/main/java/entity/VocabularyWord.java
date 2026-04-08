package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "vocabulary_words")
@Getter @Setter @NoArgsConstructor
public class VocabularyWord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private VocabularyTopic topic;

    @Column(nullable = false, length = 100)
    private String word;

    @Column(name = "word_type", length = 50)
    private String type; // E.g., noun, verb, adjective

    @Column(nullable = false, columnDefinition = "TEXT")
    private String meaning;

    @Column(columnDefinition = "TEXT")
    private String example;
}
