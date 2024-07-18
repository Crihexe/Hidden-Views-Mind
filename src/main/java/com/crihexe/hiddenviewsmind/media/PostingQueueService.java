package com.crihexe.hiddenviewsmind.media;

import com.crihexe.hiddenviewsmind.db.mongo.PostingQueueMongo;
import com.crihexe.hiddenviewsmind.db.repositories.PostRepository;
import com.crihexe.hiddenviewsmind.db.repositories.PostingQueueRepository;
import com.crihexe.hiddenviewsmind.dto.Post;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostingQueueService {

    @Value("${hvm.media.cache.directory}")
    private String mediaCacheDirectory;

    @Autowired
    private PostingQueueRepository postingQueueRepository;

    @Autowired
    private PostRepository postRepository;

    private Path rootLocation;

    @PostConstruct
    public void init() {
        rootLocation = Paths.get(mediaCacheDirectory);
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    public String push(Post post, byte[] fileContent) {

        post.setAddedAt(LocalDateTime.now());   // TODO set addedAt to NOW

        PostingQueueMongo item = new PostingQueueMongo(post);
        postingQueueRepository.insert(item);

        // peccato. devo fare prima un insert poi un update con save perche' mi serve l'id autogenerato come filename

        String filename = item.getId()+".hvmcache";
        item.setFilename(filename);

        postingQueueRepository.save(item);

        store(filename, fileContent);

        return filename;
    }

    public void pop() {

    }

    public Resource get(String filename) throws MalformedURLException {
        PostingQueueMongo post = postingQueueRepository.findByFilename(filename);

        Path file = this.rootLocation.resolve(Paths.get(filename)).normalize().toAbsolutePath();

        return new UrlResource(file.toUri());
    }

    public void markAsPosted(String filename) {
        PostingQueueMongo post = postingQueueRepository.findByFilename(filename);
        post.setPosted(true);

        postingQueueRepository.delete(post);


    }

    public void cleanupOldFiles() {
        LocalDateTime expiryTime = LocalDateTime.now().minusHours(24);
        List<PostingQueueMongo> oldFiles = postingQueueRepository.findAllByPostedAtBefore(expiryTime);

        oldFiles.forEach(file -> {
            try {
                String filename = file.getFilename();
                PostingQueueMongo item = postingQueueRepository.findByFilename(filename);

                // TODO usa item per salvarlo nel db mysql logs

                Files.deleteIfExists(rootLocation.resolve(file.getFilename()));
                postingQueueRepository.delete(item);
            } catch(IOException e) {
                e.printStackTrace();
            }
        });

    }

    public byte[] load(String filename) {
        Path sourceFile = this.rootLocation.resolve(Paths.get(filename)).normalize().toAbsolutePath();
        if(!sourceFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
            // TODO exception
            throw new RuntimeException("Invalid path. (previene eventuali path ../ attacks)");
        }
        byte[] content;
        try {
            content = Files.readAllBytes(sourceFile);
        } catch (IOException e) {
            // TODO exception
            throw new RuntimeException(e);
        }
        return content;
    }

    private void store(String filename, byte[] content) {
        try {
            Path destinationFile = this.rootLocation.resolve(Paths.get(filename)).normalize().toAbsolutePath();
            if(!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // TODO exception
                throw new RuntimeException("Invalid path. (previene eventuali path ../ attacks)");
            }
            Files.write(destinationFile, content);
        } catch(IOException e) {
            // TODO
        }
    }

    private void remove(String filename) {

    }

}
