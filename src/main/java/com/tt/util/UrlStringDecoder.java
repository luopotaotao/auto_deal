package com.tt.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by tt on 2016/11/22.
 */
public class UrlStringDecoder {
    public static String decode(String str,String encode){
        String ret = null;
        if (str!=null&&!str.trim().isEmpty()) {
            try {
                ret = URLDecoder.decode(str,encode);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
    public static String decode(String str){
        return decode(str,"utf-8");
    }
}
