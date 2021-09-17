import requests
from bs4 import BeautifulSoup

# 取登录token
login_token_response = requests.get("https://guanli.altabalink.com/login")
login_token_html = login_token_response.content.decode("utf-8")

soup = BeautifulSoup(login_token_html, "html.parser")
_token = soup.select(r"[name='_token']")[0].get("value")
print("登录参数: %s" % _token)

s = requests.Session()

# 登录
headers = {
    "Content-Type": "application/x-www-form-urlencoded",
    "Referer": "http://www.saywash.com/saywash/WashCallManager/login/login.do",
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.63 Safari/537.36"
}

body = {
    "j_username": "188",
    "j_password": "123"
}

url = "http://www.saywash.com/saywash/WashCallManager/j_spring_security_check"

login_response_data = s.post(url, data=body, headers=headers)
print(login_response_data.status_code)
# print(login_response_data.text)

order_body = {
    "type": "1",
    "merchantId": "3914",
    "startDate": "2021/09/17",
    "endDate": "2021/09/17"
}

# 调用获取订单接口
order_uri = "http://www.saywash.com/saywash/WashCallManager/merchant/order/search.do"
order_response_data = s.post(order_uri, data=order_body, headers=headers)
print(order_response_data.status_code)
print(order_response_data.text)

# 解析页面中数据
# 解析页码
order_html = order_response_data.content.decode("utf-8")
page_soup = BeautifulSoup(order_html, "html.parser")
page_content = page_soup.select(".paging_container>div")[0]
print("page_info :%s" % page_content)
