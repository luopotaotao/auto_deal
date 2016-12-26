//package com.tt.service;
//
//import com.tt.DataBaseConfig;
//import com.tt.model.TestUser;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import static org.junit.Assert.*;
//
///**
// * Created by tt on 2016/11/23.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {DataBaseConfig.class})
//public class UserServiceImplTest {
//    @Autowired
//    private TestUserService userService;
//    @Test
//    public void list() throws Exception {
//
//    }
//
//    @Test
//    public void findUserByName() throws Exception {
//        TestUser user = userService.findUserByName("admin");
//        assertNotNull("has user",user);
//    }
//
//}