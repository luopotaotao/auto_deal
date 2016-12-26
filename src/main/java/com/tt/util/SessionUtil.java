package com.tt.util;

import com.tt.ext.security.Authority;
import com.tt.ext.security.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Set;

/**
 * Created by tt on 2016/11/28.
 */
public class SessionUtil {
    public static HttpSession get() {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest().getSession();
        return session;
    }

    public static <T> T getAttribute(String name, Class<T> type) throws ClassCastException {
        return (T) get().getAttribute(name);
    }

    public static void setAttribute(String key, Object val) {
        get().setAttribute(key, val);
    }

    public static void removeAttribute(String name) {
        get().removeAttribute(name);
    }

    public static MyUserDetails getUser() {
        return (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean hasRole(String... roles) {
        Authority authority = getUser().getAuthority();

        if (authority == null) {
            return false;
        }else{
            String cur_role = authority.getAuthority();
            if(cur_role==null||cur_role.trim().isEmpty()){
                return false;
            }
            return Arrays.stream(roles).anyMatch(role -> cur_role.equals(role));
        }
    }
}
