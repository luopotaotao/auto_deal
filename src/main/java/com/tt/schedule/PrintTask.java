package com.tt.schedule;

import org.springframework.stereotype.Component;

/**
 * Created by tt on 2016/12/26.
 */
//@Component("printTask")
public class PrintTask extends SuspendableTask {
    @Override
    void run() {
        System.out.println(System.currentTimeMillis());
    }
}
