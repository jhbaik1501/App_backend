package com.example.Toy_project.service;


import com.example.Toy_project.domain.UserGroup;
import com.example.Toy_project.repository.GroupRepository;
import com.example.Toy_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupService {

    final private GroupRepository groupRepository;
    final private UserRepository userRepository;

    @Transactional
    public void save(UserGroup group){
        groupRepository.save(group);
    }

    @Transactional
    public UserGroup findOneById(Long groupId){
        return groupRepository.findGroup(groupId);
    }

    @Transactional
    public boolean checkPassword(Long group_id, String password) {
        return groupRepository.checkPassword(group_id, password);
    }
}
