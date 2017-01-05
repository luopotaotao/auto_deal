import sys
import time

import requests
from bs4 import BeautifulSoup
from requests import Request


class WebTask:
    def __init__(self, account='376488259', password='mawei68394', timeout=10):
        super().__init__()
        self.counter = 0
        self.account = account
        self.password = password
        self.timeout = timeout
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
        print('-' * 25 + ' %s ' % url + '-' * 25)
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
        print(res.text)

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
            print(str(time.time()) + ' 余额:' + res.text)
        else:
            raise RuntimeError('查询余额失败!')

    def step_bid(self, lt_issue_start, mode, which):
        form = {'1': None, '2': self.getFormLast2, '3': self.getFormLast3}.get(which)(lt_issue_start, mode)
        print(form)
        res = self.execute('POST', self.url_service, form)
        print(res.text)
        if res.text != 'success':
            raise RuntimeError('投注失败!')
        else:
            print('投注成功!')
        return res

    def setp_validate(self):
        print('获取是否可投注(当前直接确定)')
        return True

    def login(self):
        self.step_visit_login_page()
        self.step_do_login()
        # num = self.step_get_num()
        # self.step_bid(num)
        # for i in range(0, 100):
        #     self.step_query_balance()
        #     time.sleep(10)

    def getForm(self, num, mode, codes, methodid, lt_sel_dyprize, ViewState):
        project = {'type': 'input',
                   'methodid': methodid,
                   'codes': '&'.join(codes),
                   'zip': 0,
                   'nums': len(codes),
                   'times': 1,
                   'money': round(len(codes) * 1 * 2 * (10 ** (1 - mode) * 1000)) / 1000,
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

        # print(form)
        return form

    def readFile(self, filename):
        codes = []
        f = open(filename, 'r')
        for l in f:
            codes += l.strip().split(' ')
        f.close()
        return codes

    def getFormLast2(self, num, mode):
        if self.codes_last2 is None:
            self.codes_last2 = self.readFile('last2-40.txt')
        return self.getForm(num, mode, self.codes_last2, 47, '194|0', 'j_id7')

    def getFormLast3(self, num, mode):
        if self.codes_last3 is None:
            self.codes_last3 = self.readFile('last3-430.txt')
        return self.getForm(num, mode, self.codes_last3, 11, '1940|0', 'j_id51')


# getForm('20170101-234', 2, ['01', '03'])
task = WebTask(timeout=15)
task.login()
while True:
    try:
        task.step_query_balance()
        print('请输入模式{1:元,2:角,3,分:4:厘}:\r\n')
        mode = int(sys.stdin.readline().strip())
        print('请输入方案,(1,2,3)\r\n')
        which = sys.stdin.readline().strip()

        time.sleep(10)
        if task.setp_validate():
            num = task.step_get_num()
            task.step_bid(num, mode, which)
    except BaseException as e:
        task.counter += 1
        if task.counter > 3:
            try:
                task = WebTask(timeout=15)
                task.login()
            except BaseException as err:
                print('系统重新登录失败,请人工处理!')
                break
        print(e)
