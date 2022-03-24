package com.example.Toy_project.repository;


import com.example.Toy_project.domain.TimeFormContainName;
import com.example.Toy_project.domain.User;
import com.example.Toy_project.domain.UserGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user){
        user.setId(null);
        em.persist(user);
    }

    public User findOne(Long id){
        return em.find(User.class, id);
    }

    public List<User> findAll(){
        List<User> list = em.createQuery("SELECT U FROM User U").getResultList();
        return list;
    }

    public UserGroup findUserGroup(Long id){ // 유저의 그룹 정보 가져오기.

        User user = em.find(User.class, id);
        return user.getUserGroup();
    }


    public int useCoin(Long userId, int coin) {
        User user = findOne(userId);
        user.setCoin(user.getCoin() - coin);
        em.merge(user);
        return user.getCoin();
    }

    public void saveTime(Long id, LocalTime localTime) {
        User user = findOne(id);
        user.setTime(localTime);
        em.merge(user);
    }

    public void TimeReset(List<User> users) { // 원래대로면, 꺼내와서 하는 것보다.. sql문 써서하는게 효율이 훨씬 좋을듯
        for(User user : users){
            LocalTime time = user.getTime();
            int hour = time.getHour();

            LocalTime temp = LocalTime.of(00,00,00);
            user.setTime(temp);
            user.setCoin(user.getCoin() + hour);
            em.merge(user);
        }

    }

    public List<TimeFormContainName> findAllTime() {
        List<TimeFormContainName> list = em.createQuery("SELECT U.name, U.time FROM User U ORDER BY U.time desc")
                .setMaxResults(100)
                .getResultList();

        return list;
    }

    public List<TimeFormContainName> findAllTimeByGroup(Long groupid) { // 그룹 아이디를 받으면 그 그룹의 시간과 유저들의 이름을 반환.

        System.out.println("GROUP ID : " + groupid);

        List<TimeFormContainName> list = em.createQuery("SELECT U.name, U.time FROM User U JOIN U.userGroup g where g.id = :id")
                .setParameter("id", groupid)
                .getResultList();

        return list;
    }

    public void setUserGroup(UserGroup group, Long user_id) { // 유저의 그룹을 지정.
        User user = findOne(user_id);
        user.setUserGroup(group);
    }
}
