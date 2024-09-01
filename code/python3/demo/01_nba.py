# pip install requests
# pip install lxml

import requests
from lxml import etree

# 发送的地址
url = 'https://nba.hupu.com/stats/players'
headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36'
}

# 发送请求
resp = requests.get(url, headers)
# print(resp.text)

# 处理结果
e = etree.HTML(resp.text)

# 解析响应的数据, 使用XPath, 对应Java中的JSoup
nos = e.xpath('//table[@class="players_table"]//tr/td[1]/text()')
names = e.xpath('//table[@class="players_table"]//tr/td[2]/a/text()')
teams = e.xpath('//table[@class="players_table"]//tr/td[3]/a/text()')
scores = e.xpath('//table[@class="players_table"]//tr/td[4]/text()')
# 是否保存

with open('nba.txt', 'w', encoding='utf-8') as f:
    for no, name, team, score in zip(nos, names, teams, scores):
        print(f'排名: {no}, 姓名: {name}, 球队: {team}, 得分: {score} ')
        f.write(f'排名: {no}, 姓名: {name}, 球队: {team}, 得分: {score} \n')

