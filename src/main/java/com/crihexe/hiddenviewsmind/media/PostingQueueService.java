package com.crihexe.hiddenviewsmind.media;

import com.crihexe.hiddenviewsmind.db.repositories.PostingQueueRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MediaCacheService {

    @Value("${media.cache.directory}")
    private String mediaCacheDirectory;


    private final PostingQueueRepository repository;
    private Path rootLocation;

    @Autowired
    public MediaCacheService(PostingQueueRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        rootLocation = Paths.get(mediaCacheDirectory);
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    public void store(String filename, byte[] content) {
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

}
