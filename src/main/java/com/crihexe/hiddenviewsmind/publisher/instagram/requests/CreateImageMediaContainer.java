package com.crihexe.hiddenviewsmind.contentpublisher.instagram.requests;

import com.crihexe.hiddenviewsmind.dto.Post;
import com.crihexe.hiddenviewsmind.dto.UserTagImage;
import com.crihexe.japi.annotations.*;
import com.crihexe.japi.annotations.Method.Auth;
import com.crihexe.japi.annotations.Method.Methods;

import java.util.List;

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
	public List<UserTagImage> userTags;
	
	// ci sarebbe product_tags // https://developers.facebook.com/docs/instagram-api/reference/ig-user/media#creating
	
	public CreateImageMediaContainer(Post post) {
		this.userID = userID;
		this.imageURL = imageURL;
	}
	
}
