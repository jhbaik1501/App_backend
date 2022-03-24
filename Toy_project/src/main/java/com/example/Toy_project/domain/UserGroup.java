package com.example.Toy_project.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class UserGroup {

    @Id @GeneratedValue
    @Column(name = "group_id")
    private Long id;

    @Column(name = "group_name")
    private String name;

    @Column(name = "group_password")
    private String password;


}
