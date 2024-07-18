package com.crihexe.hiddenviewsmind.publisher.instagram.requests;

import com.crihexe.hiddenviewsmind.dto.Post;
import com.crihexe.hiddenviewsmind.dto.UserTagImage;
import com.crihexe.japi.annotations.*;
import com.crihexe.japi.annotations.Method.Auth;
import com.crihexe.japi.annotations.Method.Methods;

import java.util.List;
import java.util.Set;

@Method(method = Methods.POST, auth = Auth.QueryToken)
@Endpoint("/v20.0/{userID}/media")
public class CreateImageMediaContainer {
	
	@AuthKey(name = "access_token")
	public String _authorization;
	
	@PathParam(name = "userID")
	public String userID;
	
	@QueryParam(name = "image_url", required = true)
	public String imageURL;
	
	@QueryParam(name = "is_carousel_item")
	public Boolean carouselItem;
	
	@QueryParam(name = "caption")
	public String caption;
	
	@QueryParam(name = "location_id")
	public String locationID;
	
	@QueryParam(name = "user_tags")
	public Set<UserTagImage> userTags;

	public CreateImageMediaContainer(String userID, Post post) {
		this.userID = userID;
		this.imageURL = post.getImageURL();
		this.carouselItem = post.getCarouselItem();
		this.caption = post.getCaption();
		this.locationID = post.getLocationID();
		this.userTags = post.getUserTags();
	}
	
}
