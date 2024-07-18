package com.crihexe.hiddenviewsmind.contentpublisher.instagram;

import com.crihexe.hiddenviewsmind.contentpublisher.instagram.requests.PublishMedia;
import com.crihexe.hiddenviewsmind.contentpublisher.instagram.responses.BasicId;
import com.crihexe.hiddenviewsmind.contentpublisher.instagram.requests.CreateImageMediaContainer;
import com.crihexe.hiddenviewsmind.db.entities.PostEntity;
import com.crihexe.hiddenviewsmind.dto.Post;
import com.crihexe.japi.JAPI;
import com.crihexe.japi.exception.JAPIException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.boot.configurationprocessor.json.JSONException;

public class Instagram {

	// TODO capire come fare ad ottenere sto access token
	private static final String ACCESS_TOKEN = "EAAGiI8Y77pMBO4ALF9oeRyjfCWy6WxCeevSB5XReIq7VKhOHBHA8uPMzuMbAuIMaTZCGAg25Xf5ZAIvgtybDI7gs3DCKSLXrMu5yoaRNxNHeiY847wc2OMyOYeZA5KWtMf3zqZBONB3NW73lbZBZBO9ZAYp8HLDZBOmZBHCMFT77Hb483NDmVozwAJUFjaYouIaEjQtkcFjlO50UAXdZBNxQfx";
	private static final String userID = "17841466971303580";
	
	private JAPI japi;
	
	public Instagram() {
		japi = new JAPI("https://graph.facebook.com", ACCESS_TOKEN);
	}
	
	public BasicId uploadImage(String imageURL, String caption) throws JsonMappingException, JsonProcessingException, NullPointerException, JSONException, IllegalArgumentException, IllegalAccessException, JAPIException {
		CreateImageMediaContainer media = new CreateImageMediaContainer(userID, imageURL);
		media.caption = "swag test @crih.exe @_viola.scarda_"; // caption;

		return japi.send(media, BasicId.class);
	}
	
	public BasicId publishMedia(String id) throws JsonMappingException, JsonProcessingException, NullPointerException, JSONException, IllegalArgumentException, IllegalAccessException, JAPIException {
		return japi.send(new PublishMedia(userID, id), BasicId.class);
	}
	
}
