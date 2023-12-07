package com.twitter.users.infraestructure.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "follow")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "id")
    private Long follower;

    //@ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "id")
    private Long followee;
}
