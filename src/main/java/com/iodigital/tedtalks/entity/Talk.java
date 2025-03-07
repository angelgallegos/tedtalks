package com.iodigital.tedtalks.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.URL;

import java.time.Instant;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Talk extends BaseDBO {

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotNull
    private Instant date;

    private Long views;

    private Long likes;

    @NotBlank(message = "Link is mandatory")
    @URL(regexp = "^(http|https).*")
    private String link;

    @ManyToOne
    @JoinColumn(name="speaker_id", nullable=false)
    private Speaker speaker;

}
