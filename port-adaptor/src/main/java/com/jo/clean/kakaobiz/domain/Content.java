package com.jo.clean.kakaobiz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Content {
    int seq;
    String encUserId;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime submitAt;

    public Content() {
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public void setEncUserId(String encUserId) {
        this.encUserId = encUserId;
    }

    public void setSubmitAt(LocalDateTime submitAt) {
        this.submitAt = submitAt;
    }
}
