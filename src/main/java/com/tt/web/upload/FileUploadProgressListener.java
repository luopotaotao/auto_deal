package com.tt.web.upload;

import org.apache.commons.fileupload.ProgressListener;
import com.tt.util.SessionUtil;

/**
 * Created by tt on 2016/11/28.
 */
public class FileUploadProgressListener implements ProgressListener {
    public static String SESSION_PROGRESS_KEY ="file_progress";

    public FileUploadProgressListener() {
        ProgressEntity ps = new ProgressEntity();
        SessionUtil.setAttribute(this.SESSION_PROGRESS_KEY, ps);
    }

    public FileUploadProgressListener(String session_progress_key) {
        this.SESSION_PROGRESS_KEY = session_progress_key;
        ProgressEntity ps = new ProgressEntity();
        SessionUtil.setAttribute(this.SESSION_PROGRESS_KEY, ps);
    }
    @Override
    public void update(long pBytesRead, long pContentLength, int pItems) {
        ProgressEntity ps =  SessionUtil.getAttribute(this.SESSION_PROGRESS_KEY,ProgressEntity.class);
        ps.setpBytesRead(pBytesRead);
        ps.setpContentLength(pContentLength);
        ps.setpItems(pItems);
//        System.out.println(ps);
        //更新
        SessionUtil.setAttribute(this.SESSION_PROGRESS_KEY, ps);
    }


}
