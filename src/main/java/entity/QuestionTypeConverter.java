package entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts between Java QuestionType enum and PostgreSQL 'question_type' named enum.
 * DB stores: 'multiple-choice', 'text'
 * Java enum: MULTIPLE_CHOICE, TEXT
 */
@Converter
public class QuestionTypeConverter implements AttributeConverter<Question.QuestionType, String> {

    @Override
    public String convertToDatabaseColumn(Question.QuestionType attribute) {
        if (attribute == null) return null;
        return switch (attribute) {
            case MULTIPLE_CHOICE -> "multiple-choice";
            case TEXT -> "text";
        };
    }

    @Override
    public Question.QuestionType convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return switch (dbData) {
            case "multiple-choice" -> Question.QuestionType.MULTIPLE_CHOICE;
            case "text" -> Question.QuestionType.TEXT;
            default -> throw new IllegalArgumentException("Unknown question_type: " + dbData);
        };
    }
}
