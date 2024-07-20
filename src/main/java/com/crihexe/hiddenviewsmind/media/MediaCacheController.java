package com.crihexe.hiddenviewsmind.media;

import com.crihexe.hiddenviewsmind.dto.HostBody;
import com.crihexe.hiddenviewsmind.dto.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

@RestController
@RequestMapping("/media")
public class MediaCacheController {

    @Autowired
    private PostingQueueService postingQueueService;

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Resource resource = postingQueueService.get(filename);

            if (resource.exists() || resource.isReadable()) {
                postingQueueService.markAsPosted(filename); // TODO non so se e' questo il punto migliore. potrebbe non essere certo che e' stato pubblicato con successo
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }

    @PostMapping(value = "/host", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public String host(@RequestPart("post") String postStr, @RequestPart("file") MultipartFile file) throws IOException {
        Post post = new ObjectMapper().readValue(postStr, Post.class);
        String cacheFile = postingQueueService.host(post, file);
        return cacheFile;
    }

    @GetMapping("/publish/{filename:.+}")
    public String publish(@PathVariable String filename) {
        postingQueueService.publish(filename);
        return "done";
    }

}
