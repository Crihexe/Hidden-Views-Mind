package com.crihexe.hiddenviewsmind.publisher.instagram.requests;

import com.crihexe.hiddenviewsmind.dto.UserTagImage;
import com.crihexe.japi.annotations.*;
import com.crihexe.japi.annotations.Method.Auth;
import com.crihexe.japi.annotations.Method.Methods;

import java.util.List;

@Method(method = Methods.POST, auth = Auth.QueryToken)
@Endpoint("/v20.0/{userID}/media")
public class CreateReelMediaContainer {
	
	@AuthKey(name = "access_token")
	public String _authorization;
	
	@PathParam(name = "userID")
	public String userID;
	
	@QueryParam(name = "media_type", required = true)
	public String mediaType = "REELS";
	
	@QueryParam(name = "video_url", required = true)
	public String videoURL;
	
	@QueryParam(name = "caption")
	public String caption;
	
	@QueryParam(name = "share_to_feed")
	public Boolean shareToFeed;
	
	@QueryParam(name = "collaborators")
	public List<String> collaborators;
	
	@QueryParam(name = "cover_url")
	public String coverUrl;
	
	@QueryParam(name = "audio_name")
	public String audioName;
	
	@QueryParam(name = "user_tags")
	public List<UserTagImage> userTags;
	
	@QueryParam(name = "location_id")
	public String locationID;
	
	@QueryParam(name = "thumb_offset")
	public Integer thumbOffset;

	public CreateReelMediaContainer(String userID, String mediaType, String videoURL) {
		this.userID = userID;
		this.mediaType = mediaType;
		this.videoURL = videoURL;
	}
	
}
