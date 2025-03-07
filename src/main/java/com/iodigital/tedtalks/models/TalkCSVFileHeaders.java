package com.iodigital.tedtalks.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum TalkCSVFileHeaders {
    TITLE("title"),
    DATE("date"),
    VIEWS("views"),
    LINK("link"),
    LIKES("likes"),
    AUTHOR("author");
    private String value;
}
