package com.tt.schedule;

import com.tt.exception.httpclient.InitException;
import com.tt.exception.httpclient.LoginException;
import com.tt.util.KeyValMap;
import com.tt.util.StringUtil;
import com.tt.web.controller.schedule.ScheduleController;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by tt on 2016/12/26.
 */
@Component
public class WebTask extends SuspendableTask {
    private static Queue<Msg> msgQueue=new LinkedBlockingQueue<>();

    private String account;
    private String password;

    private AtomicBoolean hasLogined = new AtomicBoolean(false);

    /**
     * 总流程
     */
    @Override
    public void run() throws Exception {
        if (!hasLogined.get()) {
            if (StringUtil.isNullOrEmpty(account) || StringUtil.isNullOrEmpty(password)) {
                addMsg("未设定用户名和密码");
                return;
            }
            init(3);
            String main_url = login(account, password);
            if (main_url == null || main_url.trim().isEmpty()) {
                throw new Exception("登录错误");
            }
            hasLogined.set(true);
        }
        if (!hasLogined.get()) {
            return;
        } else {
            addMsg("获取是否投注信息");
            String avail = isRight();
            if (avail != null && !avail.trim().isEmpty()) {
                addMsg("开始投注");
                if(buy(avail)){//"20161224-%s"
                    addMsg("投注成功");
                }else{
                    addMsg("投注失败!请联系管理员");
                }

            }else {
                addMsg("时机未到,不投注");
            }
        }
    }

    HttpClient client;
    String incap_ses_428_896019;
    String incap_ses_572_896019;
    String visid_incap_896019;
    String JSESSIONID;


    /**
     * 初始化HttpClient,
     * 访问登录页面,获取全局Cookie信息
     *
     * @param sec 登录超时时间(秒)
     */
    public void init(int sec) throws IOException {
        client = new HttpClient();//定义client对象
        client.getHttpConnectionManager().getParams().setConnectionTimeout(sec * 1000);//设置连接超时时间为2秒（连接初始化时间）
        visitLoginPageAndSetCookie();
        anotherCookie();
        System.out.println("初始化完成!");

    }

    public void visitLoginPageAndSetCookie() {
        KeyValMap headers = new KeyValMap();
        headers
                .add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .add("Accept-Encoding", "deflate, sdch")
                .add("Accept-Language", "zh-CN,zh;q=0.8")
                .add("Host", "dtnew.co")
                .add("Upgrade-Insecure-Requests", "1")
                .add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2922.1 Safari/537.36");

        HttpMethodBase method = getMethod(true, "http://dtnew.co/login.shtml", headers);

        int main_statusCode = 0;
        try {
            main_statusCode = client.executeMethod(method);
        } catch (IOException e) {
            throw new InitException("初始化IO异常!");
        }

        if (main_statusCode != HttpStatus.SC_OK) {
            System.out.println("err");
        }
        HeaderElement[] cookie = method.getResponseHeader("Set-Cookie").getElements();
        if (cookie.length < 1) {
            throw new InitException("初始化获取Cookie异常!");
        }
        Arrays.stream(cookie).forEach(item -> {
            if (item.getName() != null && item.getValue() != null) {
                String val = item.getValue();
                switch (item.getName()) {
                    case "incap_ses_428_896019":
                        this.incap_ses_428_896019 = val;
                        break;
                    case "visid_incap_896019":
                        this.visid_incap_896019 = val;
                        break;
                    case "JSESSIONID":
                        this.JSESSIONID = val;
                        break;
                }
            }
            System.out.println(String.format("%s:%s", item.getName(), item.getValue()));
        });
        if (this.incap_ses_428_896019 == null || this.visid_incap_896019 == null || this.JSESSIONID == null) {
            throw new InitException("初始化获取Cookie异常!");
        }
        method.releaseConnection();
        addMsg("访问登录页面,初始化Cookie完成");

    }

    public void anotherCookie() throws IOException {
        KeyValMap headers = new KeyValMap();
        HttpMethodBase method = getMethod(true, String.format("http://dtnew.co/a4j/s/3_3_3.Finalorg/richfaces/renderkit/html/css/basic_classes.xcss/DATB/eAELXT5DOhSIAQ!sA18_.shtml;jsessionid=%s", this.JSESSIONID), headers);

        client.executeMethod(method);
        HeaderElement[] cookie = method.getResponseHeader("Set-Cookie").getElements();
        if (cookie.length < 1) {
            throw new InitException("初始化获取Cookie[incap_ses_572_896019] 异常!");
        }
        Arrays.stream(cookie).filter(item -> "incap_ses_572_896019".equals(item.getName())).findFirst().ifPresent(r -> this.incap_ses_572_896019 = r.getValue());
        if (this.incap_ses_572_896019 == null) {
            throw new InitException("初始化获取Cookie[incap_ses_572_896019] 异常!");
        }
        addMsg("初始化额外Cookie完成");
    }

    /**
     * 登录服务器,返回跳转页面
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录成功跳转页面url
     */
    public String login(String username, String password) throws IOException {
        HttpMethodBase method = getMethod(false, "http://dtnew.co/login.shtml", null, new NameValuePair[]{
                new NameValuePair("AJAXREQUEST", "LGForm:j_id3"),
                new NameValuePair("LGForm", "LGForm"),
                new NameValuePair("txtName", username),
                new NameValuePair("txtPsw", password),
                new NameValuePair("javax.faces.ViewState", "j_id1"),
                new NameValuePair("LGForm:j_id4", "LGForm:j_id4"),
                new NameValuePair("pwd", password),
                new NameValuePair("un", username)
        });

        int statusCode = client.executeMethod(method);
        if (statusCode != HttpStatus.SC_OK) {
            throw new LoginException("登录失败!" + statusCode);
        }
        Header header = method.getResponseHeader("Location");
        String res = getResponseAsString(method);
        System.out.println(res);
        String main_url;
        if (header != null) {
            String val = header.getValue();
            if (val != null && !val.trim().isEmpty()) {
                main_url = val.trim();
            } else {
                throw new LoginException("登录失败!未能获取跳转url" + statusCode);
            }
        } else {
            throw new LoginException("登录失败!未能获取跳转url" + statusCode);
        }
        addMsg(String.format("账号:%s -登录成功!",account));
        addMsg(String.format("获取主页地址:%s",main_url));
        return main_url;
    }


    /**
     * 检测当前是都需要投注
     *
     * @return
     */
    public String isRight() {
        //TODO 校验是否满足条件,返回要投注的期数
        return ScheduleController.num;
    }

    /**
     * 投注,并返回投注结果
     *
     * @param lt_issue_start 投注各个参数 //TODO ,待考察各个参数含义
     * @return
     */
    public boolean buy(String lt_issue_start) throws IOException {
        KeyValMap headers = new KeyValMap();
        headers.add("Accept", "*/*")
                .add("Accept-Encoding", "deflate")
                .add("Accept-Language", "zh-CN,zh;q=0.8")
                .add("Connection", "keep-alive")
//                .add("Content-Length", "575")
                .add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
//                .add("Cookie", String.format("JSESSIONID=%s; modes=3; dypoint=0", this.JSESSIONID))
                .add("Host", "dtnew.co")
                .add("Origin", "http://dtnew.co")
                .add("Referer", "http://dtnew.co/ssccq.shtml")
                .add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2922.1 Safari/537.36")
                .add("X-Requested-With", "XMLHttpRequest");
        KeyValMap params = new KeyValMap();
        params
                .add("mainForm", "mainForm")
                .add("lotteryid", "1")
                .add("flag", "save")
                .add("lt_sel_dyprize", "194|0")
                .add("lt_project[]", String.format("{'type':'digital','methodid':47,'codes':'8&9|5','zip':0,'nums':2,'times':1,'money':0.04,'mode':3,'point':'0','desc':'[后二_复式] -,-,-,89,5','curtimes':'%s'}", System.currentTimeMillis()))
                .add("lt_issue_start", lt_issue_start)
                .add("lt_total_nums", "2")
                .add("lt_total_money", "0.04")
                .add("lt_trace_times_margin", "1")
                .add("lt_trace_margin", "50")
                .add("lt_trace_times_same", "1")
                .add("lt_trace_diff", "1")
                .add("lt_trace_times_diff", "2")
                .add("lt_trace_count_input", "10")
                .add("lt_trace_money", "0")
                .add("javax.faces.ViewState", "j_id15");
        HttpMethodBase method = getMethod(false, "http://dtnew.co/LotteryService.aspx", headers, params.toNameValuePairArray());
        client.executeMethod(method);
        addMsg("发送投注请求");
        String response = getResponseAsString(method);

        //TODO 根据响应返回结果
        if("success".equals(response)){
            addMsg("投注成功!");
            return true;
        }
        addMsg(String.format("投注失败,响应信息:%s",response));
        return false;
    }

    public void logout() {

    }

    private HttpMethodBase getMethod(boolean isGet, String url, KeyValMap headers, NameValuePair... params) {
        HttpMethodBase method = isGet ? new GetMethod(url) : new PostMethod(url);
        String cookie_str = String.format("visid_incap_896019=%s; incap_ses_572_896019=%s; JSESSIONID=%s; incap_ses_428_896019=%s", this.visid_incap_896019, this.incap_ses_572_896019, this.JSESSIONID, this.incap_ses_428_896019);
        method.setRequestHeader("Cookie", cookie_str);
        System.out.println(cookie_str);
        if (headers != null && !headers.isEmpty()) {
            headers.forEach((key, val) -> method.setRequestHeader((String) key, (String) val));
        }
        if (!isGet) {
            ((PostMethod) method).setRequestBody(params);
        }

        return method;
    }


    private String getResponseAsString(HttpMethodBase method) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), method.getRequestCharSet()));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, String> getStatus() {
        Map<String, String> map = new HashMap<>();
        String status_str;
        if (status.get()) {
            if (hasLogined.get()) {
                status_str = String.format("账号:%s -运行中", account);
            } else {
                if (StringUtil.isNullOrEmpty(account) || StringUtil.isNullOrEmpty(password)) {
                    status_str = "运行中,但未登录!";
                } else {
                    status_str = String.format("运行中,账号:%s,尚未登录!",account);
                }
            }

        } else {
            status_str = "已停止";
        }
        map.put("status", status_str);
        return map;
    }

    public static Msg getMsg(){
        if(msgQueue==null||msgQueue.isEmpty()){
            return null;
        }else{
            return msgQueue.remove();
        }
    }
    public static void addMsg(String msg){
        msgQueue.add(new Msg(msg,new Date()));
    }
}
