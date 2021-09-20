"""
给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。

有效字符串需满足：
左括号必须用相同类型的右括号闭合。
左括号必须以正确的顺序闭合。

示例 1：
输入：s = "()"
输出：true

示例 2：
输入：s = "()[]{}"
输出：true

示例 3：
输入：s = "(]"
输出：false

示例 4：
输入：s = "([)]"
输出：false

示例 5：
输入：s = "{[]}"
输出：true
"""


class Solution:

    def isValid(self, s: str) -> bool:

        dic = {
            ')': '(',
            ']': '[',
            '}': '{',
        }

        stack = []
        for i in s:
            
            # stack非空且i在字典中, in dic 只能判断key, 所以key为})]
            if stack and i in dic:
                
                # 栈顶元素若与字典里的值一样就释放
                if stack[-1] == dic[i]:
                    stack.pop()
                else:
                    return False
            
            # stack为空或者i不在字典的key中, 即为{([的情况
            else:
                stack.append(i)

        return not stack


def main():
    solution = Solution()
    print(solution.isValid("{()[()]}"))
    print(solution.isValid("{([()]}"))


if __name__ == "__main__":
    main()
