package com.crihexe.hiddenviewsmind.db.mongo;

import com.crihexe.hiddenviewsmind.dto.Post;
import com.crihexe.hiddenviewsmind.dto.PostType;
import com.crihexe.hiddenviewsmind.dto.UserTagImage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "posting_queue")
public class PostingQueueMongo {

    private @Id String id;

    private Long instagram_id;
    private PostType mediaType;     // only carousel, reels or stories
    private String imageURL;        // only images. required
    private String videoURL;        // only reels or videos. required
    private String caption;         // not on carousel children
    private Boolean carouselItem;   // only images and video. true for carousel children
    private List<Long> children = new ArrayList<>();            // only carousels. required
    private Set<UserTagEntityAndMongo> userTags = new LinkedHashSet<>(); // only images and videos
    private String audioName;       // only reels
    private Boolean shareToFeed;    // only reels
    private String locationID;      // not on carousel children
    private String coverURL;        // only reels
    private Integer thumbOffset;    // only reels or videos
    private Set<String> keywords = new LinkedHashSet<>();
    private LocalDateTime postedAt;

    private String filename;
    private Boolean posted;
    private LocalDateTime addedAt;

    public PostingQueueMongo(Post post) {
        this.instagram_id = post.getInstagram_id();
        this.mediaType = post.getMediaType();
        this.imageURL = post.getImageURL();
        this.videoURL = post.getVideoURL();
        this.caption = post.getCaption();
        this.carouselItem = post.getCarouselItem();
        this.children.addAll(post.getChildren());

        for(UserTagImage u : post.getUserTags())
            this.userTags.add(new UserTagEntityAndMongo(u.usernames, u.x, u.y));

        this.audioName = post.getAudioName();
        this.shareToFeed = post.getShareToFeed();
        this.locationID = post.getLocationID();
        this.coverURL = post.getCoverURL();
        this.thumbOffset = post.getThumbOffset();
        this.keywords.addAll(post.getKeywords());
        this.postedAt = post.getPostedAt();
        //this.filename // non c'e' ancora
        //this.posted = ; // quando viene creato e' null, non e' ancora stato postato
        //this.addedAt = ;    // ora
    }

}
