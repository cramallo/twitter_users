package com.twitter.users.infraestructure.repositories.follow;

import com.twitter.users.infraestructure.entities.FollowEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaFollowRepository extends JpaRepository<FollowEntity, Long> {

    Page<FollowEntity> findByFollowerName(String followerName, Pageable pageable);

    Page<FollowEntity> findByFolloweeName(String followeeName, Pageable pageable);
}
