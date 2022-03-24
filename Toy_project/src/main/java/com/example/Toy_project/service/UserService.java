package com.example.Toy_project.service;


import com.example.Toy_project.domain.TimeForm;
import com.example.Toy_project.domain.TimeFormContainName;
import com.example.Toy_project.domain.User;
import com.example.Toy_project.domain.UserGroup;
import com.example.Toy_project.repository.GroupRepository;
import com.example.Toy_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    final private UserRepository userRepository;
    final private GroupRepository groupRepository;

    @Transactional
    public User findUser(Long id){
        return userRepository.findOne(id);
    }

    @Transactional
    public List<User> findAllUser(){
        return userRepository.findAll();
    }

    @Transactional
    public UserGroup findAllGroupByUser(Long id){
        return userRepository.findUserGroup(id);
    }

    @Transactional
    public void join(User user) {
        userRepository.save(user);
    }

    @Transactional
    public int useCoin(Long userId, int coin) {
        return userRepository.useCoin(userId, coin);
    }

    @Transactional
    public void saveTime(Long id, LocalTime localTime) {
        userRepository.saveTime(id, localTime);
    }

    @Transactional
    @Scheduled(cron = "59 59 23 * * *")
    public void ResetTime(){ // 23시 59분 59초되면 시간 reset시키고, 코인 값 증가.
        List<User> allUser = findAllUser();
        userRepository.TimeReset(allUser);
    }

    @Transactional
    public List<TimeFormContainName> findAllTimeList() {
        return userRepository.findAllTime();
    }

    @Transactional
    public List<TimeFormContainName> findAllTimeByUser(Long id) {
        return userRepository.findAllTimeByGroup(id);
    }

    @Transactional
    public void setUserGroup(Long group_id, Long user_id) { // 유저의 그룹을 넣어주는 로직.
        UserGroup group = groupRepository.findGroup(group_id);
        userRepository.setUserGroup(group, user_id);
    }
}
