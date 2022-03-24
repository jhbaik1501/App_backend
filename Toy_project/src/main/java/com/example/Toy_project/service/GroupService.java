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
    public boolean save(UserGroup group){

        System.out.println(group.getId() + group.getUser().getName() );
        Long userId = group.getUser().getId();
        if(checkGroupNum(userId)){
            groupRepository.save(group);
            return true;
        }
        else{
            return false; // 그룹 개수가 2개 이상이므로 false 반환
        }
    }

    public boolean checkGroupNum(Long userId){

        if(userRepository.findUserGroup(userId).size() >= 2){
            return false;
        }
        return true;
    }



}
