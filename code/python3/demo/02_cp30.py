import requests

def main():
    url = 'https://www.allcpp.cn/allcpp/ticket/getTicketTypeList.do?eventMainId=1729'  # 替换为你的接口地址
    response = requests.get(url)

    if response.status_code == 200:
        json_data = response.json()
        print(json_data)
    else:
        print(f"请求失败，状态码：{response.status_code}")

if __name__ == "__main__":
    main()
