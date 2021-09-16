from selenium import webdriver
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.webdriver import Chrome
import selenium.common.exceptions
import json
import csv
import time
import base64


# 使用无头浏览器获取页面中js使用ajax获取的数据
class DDSpider():

    def open_browser(self):
        # 关闭浏览器弹窗
        # chrome_opts=webdriver.ChromeOptions()
        # chrome_opts.add_argument("--headless")
        # , chrome_options=chrome_opts

        # self.browser = webdriver.Chrome(executable_path=r"D:\soft\Anaconda\chromedriver.exe")
        self.browser = Chrome()
        self.browser.implicitly_wait(1)
        self.wait = WebDriverWait(self.browser, 1)

    def crawl(self):
        self.open_browser()
        self.browser.get(
            "https://ding.qingflow.com/passport/login?redirectUrl=%2Ftag%2F1031%2Fapp%2F2e0b7bd6%2Fall;type%3D8")

        imgBase64 = self.browser.find_element(
            By.TAG_NAME, "iframe").screenshot_as_base64
        return imgBase64


if __name__ == "__main__":
    spider = DDSpider()
    imgBase64 = spider.crawl()
    print(imgBase64)

    img_data = base64.b64decode(imgBase64)
    with open("./img/001.png", "wb") as f:
        f.write(img_data)

    print("存储完成!")
