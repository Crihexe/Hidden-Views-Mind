package com.crihexe.hiddenviewsmind.publisher;

import com.crihexe.hiddenviewsmind.dto.Post;
import com.crihexe.hiddenviewsmind.media.PostingQueueService;
import com.crihexe.hiddenviewsmind.publisher.instagram.Instagram;
import com.crihexe.hiddenviewsmind.publisher.instagram.responses.BasicId;
import com.crihexe.japi.exception.JAPIException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@Service
public class ContentPublisher {

    @Autowired
    private Instagram instagram;

    @Autowired
    private PostingQueueService postingQueueService;

    public void testPublishImagePost() {
        // crawler qui

        // si sceglie dagli stack

        // genera post: emulato da:
        Post post = Post.builder()
                .caption("swag @crih.exe @_viola.scarda_")
                .build();

        // leggi media da disco dove l'ha salvato il crawler. emulato da:
        //byte[] content = new byte[1];
        //content[0] = 0x1f;
        byte[] content;
        try {
            InputStream in = new ClassPathResource("testimage.png").getInputStream();
            content = in.readAllBytes();
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String filename = postingQueueService.push(post, content);

        post.setImageURL("http://localhost:8080/media/" + filename);
        System.out.println(post.getImageURL());

        try {
            BasicId container_id = instagram.uploadImage(post);
            BasicId post_id = instagram.publishMedia(container_id.id);
        } catch (JsonProcessingException | JAPIException | IllegalAccessException | JSONException e) {
            throw new RuntimeException(e);
        }
    }



}
