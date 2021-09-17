import requests
from bs4 import BeautifulSoup

# 取登录token
login_token_response = requests.get("https://guanli.altabalink.com/login")
login_token_html = login_token_response.content.decode("utf-8")

soup = BeautifulSoup(login_token_html, "html.parser")
_token = soup.select(r"[name='_token']")[0].get("value")
print("登录参数: %s" % _token)

# 登录
headers = {
    "Content-Type": "application/x-www-form-urlencoded",
    "Referer": "http://www.saywash.com/saywash/WashCallManager/login/login.do",
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.63 Safari/537.36"
}

body = {
    "_token": _token,
    "username": "吉林城市职业学院",
    "password": "123456"
}
