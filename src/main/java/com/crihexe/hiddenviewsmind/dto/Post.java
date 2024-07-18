package com.crihexe.hiddenviewsmind.objects;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
public class Post {

    private String audioName;       // only reels
    private String caption;         // not on carousel children
    private List<String> children;  // only carousels. required
    private String coverURL;        // only reels
    private String imageURL;        // only images. required
    private Boolean carouselItem;   // only images and video. true for carousel children
    private String locationID;      // not on carousel children
    private PostType mediaType;     // only carousel, reels or stories
    private Boolean shareToFeed;    // only reels
    private Integer thumbOffset;    // only reels or videos
    private UserTagImage userTags;  // only images and videos
    private String videoURL;        // only reels or videos. required

}
