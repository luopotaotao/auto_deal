//package com.tt.service.impl;
//
//import com.tt.DataBaseConfig;
//import com.tt.RootConfig;
//import com.tt.model.Project;
//import com.tt.service.ProjectServiceI;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.junit.Assert.*;
//
///**
// * Created by tt on 2016/12/11.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {RootConfig.class, DataBaseConfig.class})
//@Transactional
//public class ProjectServiceImplTest {
//    @Autowired
//    ProjectServiceI projectService;
//    @Test
//    public void list() throws Exception {
//        List<Project> projects = projectService.list();
//        System.out.println(projects.size());
//    }
//
//}