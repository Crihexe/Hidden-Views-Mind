package com.crihexe.hiddenviewsmind.db.entities;

import com.crihexe.hiddenviewsmind.dto.PostType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "posting_queue")
@Getter
@Setter
public class PostingQueueMongo {

    private @Id Integer id;

    private Long instagram_id;
    private PostType mediaType;     // only carousel, reels or stories
    private String imageURL;        // only images. required
    private String videoURL;        // only reels or videos. required
    private String caption;         // not on carousel children
    private Boolean carouselItem;   // only images and video. true for carousel children
    private List<CarouselChildEntity> children = new ArrayList<>();  // only carousels. required
    private Set<UserTagEntity> userTags = new LinkedHashSet<>();        // only images and videos
    private String audioName;       // only reels
    private Boolean shareToFeed;    // only reels
    private String locationID;      // not on carousel children
    private String coverURL;        // only reels
    private Integer thumbOffset;    // only reels or videos
    private Set<KeywordsEntity> keywords = new LinkedHashSet<>();
    private LocalDateTime postedAt;

    private String filename;
    private Boolean posted;
    private LocalDateTime addedAt;

}
