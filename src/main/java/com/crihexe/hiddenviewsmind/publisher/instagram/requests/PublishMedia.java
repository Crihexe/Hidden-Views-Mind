package com.crihexe.hiddenviewsmind.contentpublisher.instagram.requests;

import com.crihexe.japi.annotations.*;
import com.crihexe.japi.annotations.Method.Auth;
import com.crihexe.japi.annotations.Method.Methods;

@Method(method = Methods.POST, auth = Auth.QueryToken)
@Endpoint("/v20.0/{userID}/media_publish")
public class PublishMedia {

	@AuthKey(name = "access_token")
	public String _authorization;
	
	@PathParam(name = "userID")
	public String userID;
	
	@QueryParam(name = "creation_id")
	public String creationID;

	public PublishMedia(String userID, String creationID) {
		this.userID = userID;
		this.creationID = creationID;
	}
	
}
