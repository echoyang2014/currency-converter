package de.nazaruk.persistence;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by nazaruk on 11/5/16.
 */
@Entity
@Table(name = "USER", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;
    @Transient
    private String passwordConfirm;
}