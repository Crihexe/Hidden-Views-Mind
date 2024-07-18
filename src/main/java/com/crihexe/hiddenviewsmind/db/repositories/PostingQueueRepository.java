package com.crihexe.hiddenviewsmind.db.repositories;

import com.crihexe.hiddenviewsmind.db.mongo.PostingQueueMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostingQueueRepository extends MongoRepository<PostingQueueMongo, String> {

    PostingQueueMongo findByFilename(String filename);
    List<PostingQueueMongo> findAllByPostedAtBefore(LocalDateTime expiryTime);

}
