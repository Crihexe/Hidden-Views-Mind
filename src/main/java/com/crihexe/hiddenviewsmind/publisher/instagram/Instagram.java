package com.crihexe.hiddenviewsmind.publisher.instagram;

import com.crihexe.hiddenviewsmind.dto.Post;
import com.crihexe.hiddenviewsmind.publisher.instagram.requests.CreateReelMediaContainer;
import com.crihexe.hiddenviewsmind.publisher.instagram.requests.PublishMedia;
import com.crihexe.hiddenviewsmind.publisher.instagram.responses.BasicId;
import com.crihexe.hiddenviewsmind.publisher.instagram.requests.CreateImageMediaContainer;
import com.crihexe.japi.JAPI;
import com.crihexe.japi.exception.JAPIException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.annotation.PostConstruct;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Instagram {

	// TODO https://stackoverflow.com/questions/12168452/long-lasting-fb-access-token-for-server-to-pull-fb-page-info/21927690#21927690
	// TODO a quanto pare nella risposta spiega come convertire un token normale in un LONG LIVED token, che quindi dura 2 mesi e dovro' fare a mano ogni volta, ci sta
	@Value("${secrets.ig.access-token.long-lived}")
	private String ACCESS_TOKEN;
	@Value("${secrets.ig.user-id}")
	private String userID;
	
	private JAPI japi;

	@PostConstruct
	private void init() {
		japi = new JAPI("https://graph.facebook.com", ACCESS_TOKEN);
	}
	
	public BasicId uploadImage(Post post) throws JsonProcessingException, NullPointerException, JSONException, IllegalArgumentException, IllegalAccessException, JAPIException {
		CreateImageMediaContainer media = new CreateImageMediaContainer(userID, post);
		// TODO manage exceptions like errors from meta api
		return japi.send(media, BasicId.class);
	}

	public BasicId uploadReel(Post post) throws JsonProcessingException, NullPointerException, JSONException, IllegalArgumentException, IllegalAccessException, JAPIException {
		CreateReelMediaContainer media = new CreateReelMediaContainer(userID, post);
		// TODO manage exceptions like errors from meta api
		return japi.send(media, BasicId.class);
	}
	
	public BasicId publishMedia(String id) throws JsonProcessingException, NullPointerException, JSONException, IllegalArgumentException, IllegalAccessException, JAPIException {
		return japi.send(new PublishMedia(userID, id), BasicId.class);
	}
	
}
