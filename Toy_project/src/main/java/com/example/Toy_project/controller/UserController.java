package com.example.Toy_project.controller;


import com.example.Toy_project.domain.*;
import com.example.Toy_project.service.GroupService;
import com.example.Toy_project.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final GroupService groupService;

    @PostMapping("/api/createAccount")
    public String createUser(@RequestBody User user){
        userService.join(user);
        System.out.println( "등록 후 유저의 아이디는 : " + user.getId());
        return "{ id : " +  user.getId() + "}";
    } // 회원 가입


    @PostMapping("/api/createGroup")
    @Transactional
    public UserGroup createGroup(@RequestBody GroupForm groupForm) {

        UserGroup usergroup = new UserGroup();
        System.out.println("그룹 폼 id : " + groupForm.getUser_id());

        usergroup.setName(groupForm.getGroupname());
        usergroup.setPassword(groupForm.getPassword());
        groupService.save(usergroup);
        userService.setUserGroup(usergroup.getId(), groupForm.getUser_id());

        return usergroup;
    }

    @PostMapping("/api/joinGroup")
    public String joinGroup(@RequestBody GroupForm groupForm){

        System.out.println("가입할 그룹 id : " + groupForm.getGroup_id() );

        UserGroup userGroup = groupService.findOneById(groupForm.getGroup_id());

        if(userGroup == null){
            String str = " \"fail\" : \"id\" ";
            return "{" + str + "}";
        } else {

            boolean b = groupService.checkPassword(groupForm.getGroup_id(), groupForm.getPassword());
            if(b){
                userService.setUserGroup(groupForm.getGroup_id(), groupForm.getUser_id());
                String str = " \"fail\" : \"success\" ";
                return "{" + str + "}";
            }
            else{
                String str = " \"fail\" : \"password\" ";
                return "{" + str + "}";
            }
        }
    }

    @GetMapping("/api/UserGroup")
    public UserGroup getUserGroup (@RequestParam("id") Long id){ // 유저의 아이디를 넣으면, 속한 그룹을 반환.
        return userService.findAllGroupByUser(id);
    }

    @GetMapping("/api/UserGroupTime")
    public List<TimeFormContainName> getTimeGroup (@RequestParam("id") Long groupId){
        return userService.findAllTimeByUser(groupId);
    }




    @PostMapping("/api/UseCoin")
    @Transactional
    public String UseCoin(@RequestBody UserCoinForm userCoinForm){
        System.out.println("========= " + userCoinForm.getUseCoin() + userCoinForm.getUserId());

        int i = userService.useCoin(userCoinForm.getUserId(), userCoinForm.getUseCoin());
        int possible = 1;
        System.out.println(" i : " + i );

        if(i < 0){
            possible = 0;
            int a = userCoinForm.getUseCoin() * -1;
            i = userService.useCoin(userCoinForm.getUserId(), a);
        }

        return "{ " +
                "possible : " + possible +" ," +
                "coin : " + i + "}";
    } // 회원 가입


    @GetMapping("/api/UserCheck")
    public User getUser (@RequestParam("userId") Long id){
        System.out.println("=========== 유저 아이디 : " + id + " ============");
        return userService.findUser(id);
    }

    @GetMapping("/api/UserList")
    public List<User> getUserList(){
        return userService.findAllUser();
    }




    @GetMapping("/api/TimeList")
    public List<TimeFormContainName> getTimeList(){
        return userService.findAllTimeList();
    }


}
