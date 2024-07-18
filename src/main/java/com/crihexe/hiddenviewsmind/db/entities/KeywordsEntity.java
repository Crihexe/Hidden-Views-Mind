package com.crihexe.hiddenviewsmind.db.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "keywords_entity")
public class KeywordsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "keyword", nullable = false, length = 80)
    private String keyword;

    @ManyToMany
    @JoinTable(name = "keywords_post",
            joinColumns = @JoinColumn(name = "keywords_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    private Set<PostEntity> posts = new LinkedHashSet<>();

}