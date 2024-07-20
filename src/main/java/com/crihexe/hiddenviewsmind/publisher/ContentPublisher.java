package com.crihexe.hiddenviewsmind.publisher;

import com.crihexe.hiddenviewsmind.dto.Post;
import com.crihexe.hiddenviewsmind.media.PostingQueueService;
import com.crihexe.hiddenviewsmind.publisher.instagram.Instagram;
import com.crihexe.hiddenviewsmind.publisher.instagram.responses.BasicId;
import com.crihexe.japi.JAPI;
import com.crihexe.japi.exception.JAPIException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

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

        post.setImageURL("https://hvm-cache.crihexe.com/media/" + filename);
        System.out.println(post.getImageURL());

        try {
            BasicId container_id = instagram.uploadImage(post);
            BasicId post_id = instagram.publishMedia(container_id.id);
        } catch (JsonProcessingException | JAPIException | IllegalAccessException | JSONException e) {
            throw new RuntimeException(e);
        }
    }


    public String testPublishImagePostLocal() {
        Post post = Post.builder()
                .caption("swag @crih.exe @_viola.scarda_")
                .build();

        byte[] content;
        try {
            InputStream in = new ClassPathResource("testimage.png").getInputStream();
            content = in.readAllBytes();
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost("https://hvm-cache.crihexe.com/media/host");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        try {
            builder.addTextBody("post", new ObjectMapper().writeValueAsString(post), ContentType.APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        builder.addBinaryBody(
                "file",
                content,
                ContentType.APPLICATION_OCTET_STREAM,
                "testimage.png"
        );

        HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(uploadFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HttpEntity responseEntity = response.getEntity();
        System.out.println(responseEntity.toString());
        try {
            String cache = EntityUtils.toString(responseEntity, "UTF-8");
            HttpGet pubFile = new HttpGet("https://hvm-cache.crihexe.com/media/publish/" + cache);
            try {
                response = httpClient.execute(uploadFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            responseEntity = response.getEntity();
            return EntityUtils.toString(responseEntity, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
