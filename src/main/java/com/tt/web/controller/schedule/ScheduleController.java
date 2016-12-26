package com.tt.web.controller.schedule;

import com.tt.schedule.WebTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by tt on 2016/12/26.
 */
@Controller
@RequestMapping("schedule")
public class ScheduleController {
    @Autowired
    private WebTask task;
    public static String num;

    @RequestMapping("index")
    public String index(){
        return "schedule/index";
    }

    @RequestMapping("login")
    @ResponseBody
    public Map login(@RequestParam String account, @RequestParam String password) {
        task.setAccount(account);
        task.setPassword(password);
        return task.getStatus();
    }
    @RequestMapping("start")
    @ResponseBody
    public Map start() {
        task.resume();
        return task.getStatus();
    }
    @RequestMapping("status")
    @ResponseBody
    public Map status() {
        return task.getStatus();
    }

    @RequestMapping("stop")
    @ResponseBody
    public Map stop() {
        task.pause();
        return task.getStatus();
    }

    @RequestMapping("setnum/{number}")
    @ResponseBody
    public String setNum(@PathVariable String number) {
        num = number;
        return num;
    }
}
