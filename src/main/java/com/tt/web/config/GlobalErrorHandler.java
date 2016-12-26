package com.tt.web.config;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tt on 2016/11/26.
 */
@ControllerAdvice
public class GlobalErrorHandler {

    private static final Logger logger = Logger.getLogger(GlobalErrorHandler.class);

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseBody
    public ErrorMsg handleErr(RuntimeException e){
        e.printStackTrace();
        return new ErrorMsg(e.getMessage());
    }
    @ExceptionHandler
    /**
     * 统一的异常处理
     *
     * @param request
     * @param ex
     * @return
     */
    public String exp(HttpServletRequest request, Exception ex)
    {
        // 遍历所有异常信息
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());
        StringBuffer sb = new StringBuffer();
        for (int k = 0; k < ex.toString().length() + 10; k++ )
        {
            sb.append("*");
        }
        logger.error(sb.toString());
        logger.error("*");
        logger.error("*     执行[" + url + "]请求发生异常");
        logger.error("*     异常信息：" + ex.toString());
        logger.error("*     相关位置:");
        for (int i = 0; i < ex.getStackTrace().length; i++ )
        {
            StackTraceElement stackTraceElement = ex.getStackTrace()[i];// 得到异常棧的元素
            // 查看类文件，如果包名是laser开头的（laser可以自己定义）
            if (stackTraceElement.getClassName().startsWith("tt"))
            {

                logger.error("*             " + stackTraceElement.toString());
            }
        }
        logger.error("*");
        logger.error(sb.toString());
        logger.error("");
        request.setAttribute("ex", ex);
        return "/error/error";
    }
}

class ErrorMsg{
    private String msg;
    private boolean err;

    public ErrorMsg(String msg) {
        this.msg = msg;
        this.err = true;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isErr() {
        return err;
    }

    public void setErr(boolean err) {
        this.err = err;
    }
}
