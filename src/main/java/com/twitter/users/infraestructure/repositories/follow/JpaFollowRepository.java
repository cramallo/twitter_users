package com.twitter.users.infraestructure.repositories.follow;

import com.twitter.users.infraestructure.entities.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaFollowRepository extends JpaRepository<FollowEntity, Long> {
}
