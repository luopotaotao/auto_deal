//package com.tt.dao;
//
//import com.tt.DataBaseConfig;
//import com.tt.RootConfig;
//import com.tt.model.TestUser;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * Created by tt on 2016/11/29.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {RootConfig.class, DataBaseConfig.class})
//@Transactional
//public class UserRepositoryTest {
//    @Autowired
//    TestUserRepository userRepository;
//
//    @Test
//    public void findOne() throws Exception {
//        System.out.println(userRepository.findOne(1));
//    }
//
//    @Test
//    public void save() throws Exception {
//        TestUser user = userRepository.findOne(1);
//        TestUser user1 = new TestUser();
//        BeanUtils.copyProperties(user, user1,"id");
//        user1.setUsername(user1.getUsername()+"1");
//        user1.setGender(TestUser.GENDER.MALE);
//        Long id = userRepository.save(user1);
//        System.out.println(id);
//    }
//
//    @Test
//    public void loadLatestUser(){
//        TestUser user = userRepository.loadLatestUser("admin");
//        System.out.println(user);
//    }
//}