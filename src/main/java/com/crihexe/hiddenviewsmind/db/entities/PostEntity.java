package com.crihexe.hiddenviewsmind.db.entities;

import com.crihexe.hiddenviewsmind.db.mongo.UserTagEntityAndMongo;
import com.crihexe.hiddenviewsmind.dto.PostType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "instagram_id", nullable = false)
    private Long instagram_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type", length = 10)
    private PostType mediaType;     // only carousel, reels or stories

    @Column(name = "image_url", length = 50)
    private String imageURL;        // only images. required

    @Column(name = "video_url", length = 50)
    private String videoURL;        // only reels or videos. required

    @Column(name = "caption", length = 2200)
    private String caption;         // not on carousel children

    @Column(name = "carousel_item")
    private Boolean carouselItem;   // only images and video. true for carousel children

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "post_id")
    @OrderBy
    private List<CarouselChildEntity> children = new ArrayList<>();  // only carousels. required

    @OneToMany
    @JoinColumn(name = "post_id")
    private Set<UserTagEntityAndMongo> userTags = new LinkedHashSet<>();        // only images and videos

    @Column(name = "audio_name", length = 200)
    private String audioName;       // only reels

    @Column(name = "share_to_feed")
    private Boolean shareToFeed;    // only reels

    @Column(name = "location_id", length = 200)
    private String locationID;      // not on carousel children

    @Column(name = "cover_url", length = 50)
    private String coverURL;        // only reels

    @Column(name = "thumb_offset")
    private Integer thumbOffset;    // only reels or videos

    @ManyToMany(mappedBy = "posts")
    private Set<KeywordsEntity> keywords = new LinkedHashSet<>();

    @Column(name = "added_at")
    private LocalDateTime addedAt;

    @Column(name = "posted_at")
    private LocalDateTime postedAt;

}