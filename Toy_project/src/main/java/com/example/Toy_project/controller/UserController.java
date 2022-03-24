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
    public String createGroup(@RequestBody GroupForm groupForm){

        UserGroup usergroup = new UserGroup();
        System.out.println("그룹 폼 id : " + groupForm.getUser_id());

        usergroup.setUser(userService.findUser(groupForm.getUser_id()));

        usergroup.setName(groupForm.getGroupname());
        usergroup.setPassword(groupForm.getPassword());

        boolean b = groupService.save(usergroup);
        if(b){
            return "그룹 등록 완료!";
        }
        else{
            return "그룹은 최대 2개만 등록 가능합니다.";
        }
    }


    @PostMapping("/api/joinGroup")
    public String joinGroup(@RequestBody GroupForm groupForm){

        UserGroup usergroup = new UserGroup();
        System.out.println("가입할 그룹 id : " + groupForm.getUser_id());

        usergroup.setUser(userService.findUser(groupForm.getUser_id()));

        usergroup.setName(groupForm.getGroupname());
        usergroup.setPassword(groupForm.getPassword());

        boolean b = groupService.save(usergroup);
        if(b){
            return "그룹 등록 완료!";
        }
        else{
            return "그룹은 최대 2개만 등록 가능합니다.";
        }
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


    @GetMapping("/api/UserGroup")
    public List<UserGroup> getUserGroup (@RequestParam("id") Long id){
        return userService.findAllGroupByUser(id);
    }

    @GetMapping("/api/UserGroupTime")
    public List<TimeFormContainName> getTimeGroup (@RequestParam("id") Long groupId){
        return userService.findAllTimeByUser(groupId);
    }




    @GetMapping("/api/TimeList")
    public List<TimeFormContainName> getTimeList(){
        return userService.findAllTimeList();
    }


}
