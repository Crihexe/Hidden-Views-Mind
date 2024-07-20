package com.crihexe.hiddenviewsmind;

import com.crihexe.hiddenviewsmind.db.entities.PostEntity;
import com.crihexe.hiddenviewsmind.db.mongo.PostingQueueMongo;
import com.crihexe.hiddenviewsmind.db.repositories.PostRepository;
import com.crihexe.hiddenviewsmind.db.repositories.PostingQueueRepository;
import com.crihexe.hiddenviewsmind.dto.Post;
import com.crihexe.hiddenviewsmind.publisher.ContentPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private final ContentPublisher cp;
    private final PostRepository postRepo;
    private final PostingQueueRepository postingQueueRepo;

    @Autowired
    public TestController(ContentPublisher cp, PostRepository postRepo, PostingQueueRepository postingQueueRepo) {
        this.cp = cp;
        this.postRepo = postRepo;
        this.postingQueueRepo = postingQueueRepo;
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

    @GetMapping("/db/mysql/dump")
    public List<PostEntity> mysqlDump() {
        return postRepo.findAll();
    }

    @GetMapping("/db/mongo/dump")
    public List<PostingQueueMongo> mongoDump() {
        return postingQueueRepo.findAll();
    }

}
