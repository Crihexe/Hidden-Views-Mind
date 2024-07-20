package com.crihexe.hiddenviewsmind;

import com.crihexe.hiddenviewsmind.dto.Post;
import com.crihexe.hiddenviewsmind.publisher.ContentPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final ContentPublisher cp;

    @Autowired
    public TestController(ContentPublisher cp) {
        this.cp = cp;
    }

    @GetMapping("/remote")
    public String callTest() {
        cp.testPublishImagePost();
        return "done";
    }

    @GetMapping("/local")
    public String callLocalTest() {
        return cp.testPublishImagePostLocal();
    }

    @GetMapping
    public String test() throws JsonProcessingException {
        String post = "{\"id\":null,\"instagram_id\":null,\"mediaType\":null,\"imageURL\":null,\"videoURL\":null,\"caption\":\"swag @crih.exe @_viola.scarda_\",\"carouselItem\":null,\"children\":[],\"userTags\":[],\"audioName\":null,\"shareToFeed\":null,\"locationID\":null,\"coverURL\":null,\"thumbOffset\":null,\"keywords\":[],\"addedAt\":null,\"postedAt\":null}";
        Post p = new ObjectMapper().readValue(post, Post.class);
        return new ObjectMapper().writeValueAsString(p);
    }

}
