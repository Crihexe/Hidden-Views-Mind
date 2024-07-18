package com.crihexe.hiddenviewsmind.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
