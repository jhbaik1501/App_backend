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

    public List<UserGroup> findUserGroup(Long id){ // 그룹의 모든 정보 찾기
        List<UserGroup> list = em.createQuery("SELECT P FROM UserGroup P JOIN P.user U WHERE U.id = :id", UserGroup.class)
                .setParameter("id", id)
                .getResultList();
        return list;
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

    public List<TimeFormContainName> findAllTimeByGroup(Long id) {
        List<User> users = em.createQuery("SELECT U FROM UserGroup P JOIN P.user U WHERE U.id = :id ORDER BY U.time desc")
                .setParameter("id", id)
                .getResultList();
        List<TimeFormContainName> list = new ArrayList<TimeFormContainName>();
        for(User user : users){
            TimeFormContainName temp = new TimeFormContainName();
            temp.setName(user.getName());
            temp.setLocalTime(user.getTime());
            list.add(temp);
            System.out.println("TEMP -> " + temp.getName() + ", " + temp.getLocalTime());
        }

        return list;
    }
}
