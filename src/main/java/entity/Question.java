package entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "questions")
@Getter @Setter @NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reading_id")
    private ReadingPassage readingPassage;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private QuestionType type = QuestionType.MULTIPLE_CHOICE;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(name = "correct_answer", nullable = false, columnDefinition = "TEXT")
    private String correctAnswer;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @Column(name = "part_number")
    private Integer partNumber;

    @Column(name = "audio_url")
    private String audioUrl;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "passage_text", columnDefinition = "TEXT")
    private String passageText;

    @Enumerated(EnumType.STRING)
    private SkillType skill;

    @Column(name = "order_index")
    private Integer orderIndex = 0;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @OrderBy("orderIndex ASC")
    private List<QuestionOption> options = new ArrayList<>();

    public enum QuestionType { MULTIPLE_CHOICE, TEXT }
    public enum SkillType { Listening, Reading, Grammar, Vocabulary }
}
