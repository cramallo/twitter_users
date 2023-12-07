package com.twitter.users.infraestructure.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "follow", uniqueConstraints={@UniqueConstraint(columnNames = {"follower", "followee"})})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower", referencedColumnName="name")
    private UserEntity follower;

    @ManyToOne
    @JoinColumn(name = "followee", referencedColumnName="name")
    private UserEntity followee;
}
