//package com.tt.web;
//
//import com.tt.model.TestUser;
//import com.tt.service.TestUserService;
//import org.hamcrest.Matchers;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mockito;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.LinkedList;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//import static org.junit.Assert.*;
//
///**
// * Created by tt on 216/11/21.
// */
//public class UserControllerTest {
//    TestUserService userService = null;
//    UserController userController = null;
//    List<TestUser> users = null;
//
//    @Before
//    public void setUp() throws Exception {
//        int page = 1;
//        int rows = 2;
//        users = getUsersList(page, rows);
//        userService = Mockito.mock(TestUserService.class);
//        Mockito.when(userService.list(page, rows)).thenReturn(users);
//        userController = new UserController();
//        userController.setUserService(userService);
//
//    }
//
//    @Test
//    public void list() throws Exception {
//
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/list_page"))
//                .andExpect(MockMvcResultMatchers.view().name("users_list"))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
//                .andExpect(MockMvcResultMatchers.model().attribute("users", Matchers.hasItems(users.toArray())));
//    }
//
//    @Test
//    public void showUsersPage() throws Exception {
//        List<String> list = new LinkedList<>();
//        list.add("Java");
//        list.add("Java1");
//        assertThat("has java",list,Matchers.hasItems("Java","Java1"));
//    }
//
//    private List<TestUser> getUsersList(Integer page, Integer rows) {
//        return IntStream.range((page - 1) * rows, page * rows).mapToObj(i -> new TestUser(new Long(i), "name" + i, (Math.random() > 0.5 ? TestUser.GENDER.FEMALE : TestUser.GENDER.MALE))).collect(Collectors.toList());
//
//    }
//}