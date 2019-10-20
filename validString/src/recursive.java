import java.util.Stack;

public class recursive {
    public static int i = 0; //保存当前处理的位置
    public static Stack<Character> sta = new Stack<>(); //验证括号的合法性

    public static boolean isDigit(Character c) {
        return c >= '0' && c <= '9';
    }

    public static boolean isOperator(Character c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    // biaodashi 表达式
    public static int biaodashi(String str) {
        if (i >= str.length()) {
            throw new NumberFormatException();
        }

        int result = 0;
        //处理第一个项
        if (isDigit(str.charAt(i))) {
            //以数字开头，跳至项的处理
            result = xiang(str);
        } else if (str.charAt(i) == '(') {
            //以正括号开头，特殊处理
            sta.push('(');
            i++;
            result = biaodashi(str);
            //表达式处理完应该为反括号
            if (i >= str.length() || str.charAt(i) != ')' || sta.empty() || sta.peek() != '(') {
                throw new NumberFormatException();
            }
            sta.pop();
            i++;
            //处理完括号内表达式，继续处理后续用乘除法连接的因子
            if (i < str.length()) {
                if (str.charAt(i) == '*' || str.charAt(i) == '/') {
                    char op = str.charAt(i);
                    i++;
                    int value = xiang(str);
                    if (op == '*') {
                        result *= value;
                    } else {
                        result /= value;
                    }
                }
            }
        } else {
            throw new NumberFormatException();
        }

        //如果有后续的项，进行项之间的加减法
        while (i < str.length()) {
            char op = str.charAt(i);
            if (str.charAt(i) == '+' || str.charAt(i) == '-') {
                if(isOperator(str.charAt(i-1))) {
                    //运算符不能相邻
                    throw new NumberFormatException();
                }
                //若为加减符号继续处理后面的项
                i++;
                int value = xiang(str);
                if (op == '+') {
                    result += value;
                } else {
                    result -= value;
                }
            } else if (str.charAt(i) == ')') {
                //防止出现多余的反括号
                if (sta.empty() || sta.peek() != '(') {
                    throw new NumberFormatException();
                }
                break;
            } else {
                //其他非法符号
                throw new NumberFormatException();
            }
        }
        return result;
    }

    // xiang 项
    public static int xiang(String str) {
        if (i >= str.length()) {
            throw new NumberFormatException();
        }

        //处理第一个因子
        int result = yinzi(str);
        //多个因子之间进行乘除法
        while (i < str.length()) {
            char op = str.charAt(i);
            if (op == '*' || op == '/') {
                if(isOperator(str.charAt(i-1))) {
                    //运算符不能相邻
                    throw new NumberFormatException();
                }
                i++;
                int value = yinzi(str);
                if (op == '*') {
                    result *= value;
                } else {
                    result /= value;
                }
            } else {
                break;
            }
        }
        return result;
    }

    // yinzi 因子
    public static int yinzi(String str) {
        if (i >= str.length()) {
            throw new NumberFormatException();
        }

        int result = 0;
        if (str.charAt(i) == '(') {
            //出现括号，跳转至表达式处理
            sta.push('(');
            i++;
            result = biaodashi(str);
            if (str.charAt(i) != ')' || sta.empty() || sta.peek() != '(') {
                throw new NumberFormatException();
            }
            sta.pop();
            i++;
        } else if (isDigit(str.charAt(i))) {
            //数字字符则进行数字转换
            result = shu(str);
        }
        return result;
    }

    // shu 数
    public static int shu(String str) {
        if (i >= str.length()) {
            throw new NumberFormatException();
        }

        //提取数字，找到后续第一个非数字字符停止
        String result = "";
        int p;
        for (p = i; p < str.length(); p++) {
            if (isDigit(str.charAt(p))) {
            } else if (isOperator(str.charAt(p)) || str.charAt(p) == ')') {
                break;
            } else {
                throw new NumberFormatException();
            }
        }
        result = str.substring(i, p);
        //判断数字的合法性
        String pattern1 = "^[0-9]";
        String pattern2 = "^[1-9][0-9]*";
        if (!(result.matches(pattern1) || result.matches(pattern2))) {
            throw new NumberFormatException();
        }
        i = p;
        return Integer.parseInt(result);
    }
}