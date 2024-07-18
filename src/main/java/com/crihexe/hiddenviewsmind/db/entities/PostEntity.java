package com.crihexe.hiddenviewsmind.db.entities;

import com.crihexe.hiddenviewsmind.dto.PostType;
import com.crihexe.hiddenviewsmind.dto.UserTagImage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "post_entity_1")
public class PostEntity1 {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "audio_name")
    private String audioName;

    @Column(name = "caption")
    private String caption;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "post_entity_id")
    @OrderBy
    private List<ChildrenEntity> children = new ArrayList<>();

    @Column(name = "cover_url")
    private String coverURL;

    @Column(name = "image_url")
    private String imageURL;

    @Column(name = "carousel_item")
    private Boolean carouselItem;

    @Column(name = "location_id")
    private String locationID;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type", length = 10)
    private PostType mediaType;

    @Column(name = "share_to_feed")
    private Boolean shareToFeed;

    @Column(name = "thumb_offset")
    private Integer thumbOffset;

    @Transient
    private UserTagImage userTags;

    @Column(name = "video_url")
    private String videoURL;

}