package com.yeensight.core.domain.model;

public class Insight {
    private final InsightId id;
    private final String title;
    private final String content;

    private Insight(InsightId id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static Insight create(String title, String content) {
        return restore(InsightId.generate(), title, content);
    }

    public static Insight restore(InsightId id, String title, String content) {
        if (id == null) {
            throw new IllegalArgumentException("Insight id must not be null");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Insight title must not be blank");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Insight content must not be blank");
        }
        return new Insight(id, title, content);
    }

    public InsightId id() { return id; }
    public String title() { return title; }
    public String content() { return content; }
}
