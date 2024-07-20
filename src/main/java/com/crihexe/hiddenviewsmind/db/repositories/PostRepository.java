package com.crihexe.hiddenviewsmind.db.repositories;

import com.crihexe.hiddenviewsmind.db.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {

}
