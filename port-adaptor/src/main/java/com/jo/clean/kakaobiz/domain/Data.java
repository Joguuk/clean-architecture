package com.jo.clean.kakaobiz.domain;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class Data {
    boolean first;
    boolean last;
    boolean empty;
    int numberOfElements;
    int totalElements;
    int number;
    int totalPages;
    List<Content> content;

    public int currentOffset() {
        return number;
    }

    public int nextOffset() {
        return number + 1;
    }

    public boolean isDelay() {
        return false;
    }

    public Data() {
    }
}
