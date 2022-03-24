package com.example.Toy_project.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @JsonManagedReference //순환참조 방지
    @OneToMany(mappedBy = "user")
    private List<UserGroup> groups = new ArrayList<>();




    private String name;
    private String age;
    private int coin;
    private LocalTime time;
}
