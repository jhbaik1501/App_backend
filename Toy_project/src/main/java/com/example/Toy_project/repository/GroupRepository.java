package com.example.Toy_project.repository;


import com.example.Toy_project.domain.UserGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class GroupRepository {

    private final EntityManager em;

    public void save(UserGroup group){
        em.persist(group);
    }

}
