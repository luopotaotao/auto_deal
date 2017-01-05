import threading
from tkinter import *

import sys
import time

import requests
from bs4 import BeautifulSoup
from requests import Request


class WebTask:
    def __init__(self, account='376488259', password='mawei68394', timeout=10, msg_handler=print):
        super().__init__()
        self.counter = 0
        self.account = account
        self.password = password
        self.timeout = timeout
        self.msg_handler = msg_handler
        self.g_headers = {
            'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
            'Accept-Encoding': 'deflate, sdch',
            'Accept-Language': 'zh-CN,zh;q=0.8',
            'Host': 'dtnew.co',
            'Connection': 'keep-alive',
            'Upgrade-Insecure-Requests': '1',
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2922.1 Safari/537.36'
        }
        self.user_data = {
            "AJAXREQUEST": "LGForm:j_id3",
            "LGForm": "LGForm",
            "txtName": self.account,
            "txtPsw": self.password,
            "javax.faces.ViewState": "j_id1",
            "LGForm:j_id4": "LGForm:j_id4",
            "pwd": self.password,
            "un": self.account
        }
        self.g_cookies = dict()

        self.url_login = 'http://dtnew.co/login.shtml'  # 登录页面
        self.url_main = 'http://dtnew.co/main.shtml'  # 主页面
        self.url_ssccq = 'http://dtnew.co/ssccq.shtml'  # 重庆时时彩页面,用来抓取当前期数
        self.url_service = 'http://dtnew.co/LotteryService.aspx'  # 投注页面

        self.s = requests.Session()

        self.codes_last2 = None
        self.codes_last3 = None
        self.codes_AC = None

    def execute(self, method, url, data=None):
        '''全局Http请求执行函数'''
        self.msg_handler('正在访问页面: %s ' % url)
        req = Request(method, url, data=data, headers=self.g_headers, cookies=self.g_cookies)
        pre_req = self.s.prepare_request(req)
        try:
            res = self.s.send(pre_req, timeout=self.timeout)
        except Exception as e:
            raise RuntimeError(e)
        return res

    def validate_cookies(self, cookies):
        # if cookies is None or cookies.get('JSESSIONID') is None \
        #         or cookies.get('incap_ses_574_896019') is None \
        #         or cookies.get('visid_incap_896019') is None:
        #     raise RuntimeError('cookie 获取失败')
        self.g_cookies = cookies.get_dict(domain='.dtnew.co').copy()
        self.g_cookies.update(cookies.get_dict(domain='dtnew.co'))
        return True

    @staticmethod
    def validate_main_url(res):
        if res.headers.get('Location') is None:
            raise RuntimeError("获取主页url失败!")

    @staticmethod
    def print_html(res):
        if res is None or res.text is None:
            raise RuntimeError()
            self.msg_handler(res.text)

    @staticmethod
    def parse_num(res):
        html = res.text;
        soup = BeautifulSoup(html, "html.parser")
        num = soup.find(id='current_issue').get_text()
        if num is None:
            raise RuntimeError('获取期数失败!')
        return num

    def step_visit_login_page(self):
        res = self.execute('GET', self.url_login)
        self.validate_cookies(res.cookies)
        return res

    def step_do_login(self):
        res = self.execute('POST', self.url_login, data=self.user_data)
        self.validate_main_url(res)
        return res

    def step_get_num(self):
        res_ssccq = self.execute('GET', self.url_ssccq)
        return WebTask.parse_num(res_ssccq)

    def step_query_balance(self):
        res = self.execute('POST', self.url_service, data={'flag': 'balance'})
        if res.text.endswith('--'):
            self.msg_handler(str(time.time()) + ' 余额:' + res.text)
        else:
            raise RuntimeError('查询余额失败!')

    def step_bid(self, lt_issue_start, mode, times, which):
        form = {'1': None, '2': self.get_form_last2, '3': self.get_form_last3}.get(str(which))(lt_issue_start, mode, times)
        self.msg_handler(form)
        res = self.execute('POST', self.url_service, form)
        self.msg_handler(res.text)
        if res.text != 'success':
            raise RuntimeError('投注失败!')
        else:
            self.msg_handler('投注成功!')
        return res

    def step_validate(self):
        self.msg_handler('获取是否可投注(当前直接确定)')
        return True

    def login(self):
        self.step_visit_login_page()
        self.step_do_login()
        # num = self.step_get_num()
        # self.step_bid(num)
        # for i in range(0, 100):
        #     self.step_query_balance()
        #     time.sleep(10)

    def getForm(self, num, mode, times, codes, methodid, lt_sel_dyprize, ViewState):
        project = {'type': 'input',
                   'methodid': methodid,
                   'codes': '&'.join(codes),
                   'zip': 0,
                   'nums': len(codes),
                   'times': times,
                   'money': round(len(codes) * times * 2 * (10 ** (1 - mode) * 1000)) / 1000,
                   'mode': mode,
                   'point': '0',
                   'curtimes': int(time.time() * 1000)}
        form = {
            'mainForm': 'mainForm',
            'lotteryid': '1',
            'flag': 'save',
            'lt_sel_dyprize': lt_sel_dyprize,
            'lt_project[]': str(project),
            'lt_issue_start': num,
            'lt_total_nums': project.get('nums'),
            'lt_total_money': project.get('money'),
            'lt_trace_times_margin': '1',
            'lt_trace_margin': '50',
            'lt_trace_times_same': '1',
            'lt_trace_diff': '1',
            'lt_trace_times_diff': '2',
            'lt_trace_count_input': '10',
            'lt_trace_money': '0',
            'javax.faces.ViewState': ViewState
        }
        return form

    def read_file(self, filename):
        codes = []
        f = open(filename, 'r')
        for l in f:
            codes += l.strip().split(' ')
        f.close()
        return codes

    def get_form_last2(self, num, mode, times):
        if self.codes_last2 is None:
            self.codes_last2 = self.read_file('last2-40.txt')
        return self.getForm(num, mode, times, self.codes_last2, 47, '194|0', 'j_id7')

    def get_form_last3(self, num, mode, times):
        if self.codes_last3 is None:
            self.codes_last3 = self.read_file('last3-430.txt')
        return self.getForm(num, mode, times, self.codes_last3, 11, '1940|0', 'j_id51')


task = None


def add_msg(message):
    print(message)
    if listbox_status.size() > 20:
        listbox_status.delete(0)
    listbox_status.insert(END, message)


def init_task():
    global task
    task = WebTask(account=account.get(), password=password.get(), timeout=15, msg_handler=add_msg)


def do_login():
    global task
    init_task()
    task.login()


def auto_buy():
    exe_thread = threading.Thread(target=cycle_buy)
    # exe_thread.setDaemon(True)
    exe_thread.start()


def cycle_buy():
    global task
    global work_mode

    while True:
        if not work_mode.get():
            print('结束自动模式!')
            raise SystemExit('结束自动模式!')
        try:
            buy()
            time.sleep(10)
        except BaseException as e:
            task.counter += 1
            if task.counter > 3:
                try:
                    do_login()
                except BaseException as err:
                    add_msg('系统重新登录失败,请人工处理!')
                    break
            add_msg(e)


def buy():
    global task
    global times_last2
    global times_last3
    global times_AC
    task.step_query_balance()
    if task.step_validate():
        num = task.step_get_num()
        if method_AC.get():
            task.step_bid(num, 2, times_AC.get(), 1)
        if method_last2.get():
            task.step_bid(num, 2, times_last2.get(), 2)
        if method_last3.get():
            task.step_bid(num, 2, times_last3.get(), 3)


def switch_work_mode():
    if work_mode.get():
        add_msg('切换为自动模式')
        btn_buy.grid_forget()
        auto_buy()
    else:
        add_msg('切换为手动模式')
        btn_buy.grid(row=3, column=4)


win = Tk()  # 定义一个窗体
win.title('重庆时时彩-投注助手')  # 定义窗体标题
win.geometry('950x200')  # 定义窗体的大小，是950X200像素

listbox_status = Listbox(win, width=100)
listbox_status.insert(END, 'test')
listbox_status.grid(row=0, column=0, rowspan=8)

Label(win, text='账号:').grid(row=0, column=1)
account = StringVar(win)
Entry(win, textvariable=account).grid(row=0, column=2, columnspan=3)

Label(win, text='密码:').grid(row=1, column=1)
password = StringVar(win)
Entry(win, show='*', textvariable=password).grid(row=1, column=2, columnspan=3)

Button(win, text='登录', command=do_login).grid(row=2, column=2, columnspan=2)

Label(win, text='工作模式:').grid(row=3, column=1)
work_mode = BooleanVar(value=False)
Radiobutton(win, variable=work_mode, value=True, text='自动', command=switch_work_mode).grid(row=3, column=2)
Radiobutton(win, variable=work_mode, value=False, text='手动', command=switch_work_mode).grid(row=3, column=3)
btn_buy = Button(win, text='投注', command=buy)
btn_buy.grid(row=3, column=4)

Label(win, text='投注类型:').grid(row=4, column=1)
method_last2 = BooleanVar()
Checkbutton(win, variable=method_last2, text='后2').grid(row=4, column=2)
times_last2 = IntVar(value=1)
Spinbox(win, from_=1, to=999, increment=1, textvariable=times_last2).grid(row=4, column=3)

method_last3 = BooleanVar()
Checkbutton(win, variable=method_last3, text='后3').grid(row=5, column=2)
times_last3 = IntVar(value=1)
Spinbox(win, from_=1, to=999, increment=1, textvariable=times_last3).grid(row=5, column=3)

method_AC = BooleanVar()
Checkbutton(win, variable=method_AC, text='AC').grid(row=6, column=2)
times_AC = IntVar(value=1)
Spinbox(win, from_=1, to=999, increment=1, textvariable=times_AC).grid(row=6, column=3, columnspan=1)

Label(win, text='当前状态:').grid(row=7, column=1)
msg = StringVar()
Label(win, textvariable=msg).grid(row=7, column=2, columnspan=4)

# win.grid_columnconfigure(0, minsize=300)

mainloop()  # 进入主循环，程序运行
