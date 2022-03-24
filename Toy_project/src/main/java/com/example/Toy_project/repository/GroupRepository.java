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

    public UserGroup findGroup(Long groupId){
        return em.find(UserGroup.class, groupId);
    }

    public boolean checkPassword(Long group_id, String password) {
        UserGroup group = em.find(UserGroup.class, group_id);
        if ( group.getPassword().equals(password) ) { // 패스워드가 같다면
            return true;
        }
        else{
            return false;
        }
    }
}
