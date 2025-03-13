import json


def main():
    json_str = """
    {"a": "b"}
    """
    json1 = json.loads(json_str)
    print(json1)


if __name__ == "__main__":
    main()
