package com.crihexe.hiddenviewsmind.dto;

import com.crihexe.hiddenviewsmind.db.entities.CarouselChildEntity;
import com.crihexe.hiddenviewsmind.db.entities.KeywordsEntity;
import com.crihexe.hiddenviewsmind.db.entities.PostEntity;
import com.crihexe.hiddenviewsmind.db.mongo.PostingQueueMongo;
import com.crihexe.hiddenviewsmind.db.mongo.UserTagEntityAndMongo;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link PostEntity}
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    private Long id;

    private Long instagram_id;

    private PostType mediaType;     // only carousel, reels or stories
    private String imageURL;        // only images. required
    private String videoURL;        // only reels or videos. required
    private String caption;         // not on carousel children
    private Boolean carouselItem;   // only images and video. true for carousel children
    @Builder.Default private List<Long> children = new ArrayList<>();          // only carousels. required
    @Builder.Default private Set<UserTagImage> userTags = new LinkedHashSet<>(); // only images and videos
    private String audioName;       // only reels
    private Boolean shareToFeed;    // only reels
    private String locationID;      // not on carousel children
    private String coverURL;        // only reels
    private Integer thumbOffset;    // only reels or videos

    @Builder.Default private Set<String> keywords = new LinkedHashSet<>();
    private LocalDateTime addedAt;
    private LocalDateTime postedAt;

    public static Post from(PostEntity entity) {
        Post post = Post.builder().build();

        post.id = entity.getId();
        post.instagram_id = entity.getInstagram_id();
        post.mediaType = entity.getMediaType();
        post.imageURL = entity.getImageURL();
        post.videoURL = entity.getVideoURL();
        post.caption = entity.getCaption();
        post.carouselItem = entity.getCarouselItem();

        for(CarouselChildEntity c : entity.getChildren())
            post.children.add(c.getContainerID());

        for(UserTagEntityAndMongo u : entity.getUserTags())
            post.userTags.add(new UserTagImage(u.getUsername(), u.getX(), u.getY()));

        post.audioName = entity.getAudioName();
        post.shareToFeed = entity.getShareToFeed();
        post.locationID = entity.getLocationID();
        post.coverURL = entity.getCoverURL();
        post.thumbOffset = entity.getThumbOffset();

        for(KeywordsEntity e : entity.getKeywords())
            post.keywords.add(e.getKeyword());

        post.addedAt = entity.getAddedAt();
        post.postedAt = entity.getPostedAt();

        return post;
    }

    public static Post from(PostingQueueMongo entity) {
        Post post = Post.builder().build();

        //post.id = entity.getId(); // commentato perche' ho scoperto che mongo usa gli id string
        post.instagram_id = entity.getInstagram_id();
        post.mediaType = entity.getMediaType();
        post.imageURL = entity.getImageURL();
        post.videoURL = entity.getVideoURL();
        post.caption = entity.getCaption();
        post.carouselItem = entity.getCarouselItem();

        post.children.addAll(entity.getChildren());

        for(UserTagEntityAndMongo u : entity.getUserTags())
            post.userTags.add(new UserTagImage(u.getUsername(), u.getX(), u.getY()));

        post.audioName = entity.getAudioName();
        post.shareToFeed = entity.getShareToFeed();
        post.locationID = entity.getLocationID();
        post.coverURL = entity.getCoverURL();
        post.thumbOffset = entity.getThumbOffset();

        post.keywords.addAll(entity.getKeywords());

        post.postedAt = entity.getPostedAt();
        post.addedAt = entity.getAddedAt();

        return post;
    }

}
