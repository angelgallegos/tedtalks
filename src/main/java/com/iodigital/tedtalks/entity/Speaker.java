package com.iodigital.tedtalks.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Formula;

import java.util.HashSet;
import java.util.Set;


@Entity
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Speaker extends BaseDBO {

    @NotBlank(message = "Name is mandatory")
    @Column(unique=true)
    @NonNull
    private String name;

    @OneToMany(mappedBy="speaker", cascade = CascadeType.ALL)
    @Builder.Default
    @NonNull
    private Set<Talk> talks = new HashSet<>();

    @Transient
    @Builder.Default
    @Formula("sum(talks.likes)/sum(talks.views) as average")
    private Long average = 0L;
}
