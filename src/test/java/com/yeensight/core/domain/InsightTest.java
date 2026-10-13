package com.yeensight.core.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.yeensight.core.domain.model.Insight;
import org.junit.jupiter.api.Test;

class InsightTest {
    @Test
    void createsInsightWithGeneratedId() {
        Insight insight = Insight.create("title", "content");
        assertThat(insight.id()).isNotNull();
        assertThat(insight.title()).isEqualTo("title");
    }

    @Test
    void rejectsBlankTitle() {
        assertThatThrownBy(() -> Insight.create(" ", "content")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsBlankContent() {
        assertThatThrownBy(() -> Insight.create("title", null)).isInstanceOf(IllegalArgumentException.class);
    }
}
