import java.util.Scanner;

/*
 * 类名：CalculatorTree
 * 功能：
 * 1. 判断四则运算表达式的合法性
 * 2. 计算四则运算表达式的值
 * 3. 计算带有一个未知数的四则运算表达式的值(用x表示未知数)
 */
public class CalculatorTree {
    private static final char TERM = '$';
    private static Buffer buf = new Buffer();
    private static boolean hasVariable = false;

    private static int i = 0; //保存当前处理的位置

    private static boolean isDigit(Character c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isOperator(Character c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public static int calculate(String str) {
        String calStr = str + TERM;
        Node result = expression(calStr);
        if (calStr.charAt(i) != TERM) {
            throw new NumberFormatException();
        }
        if(!hasVariable) {
            return result.GetValue();
        } else {
            Scanner scan = new Scanner(System.in);
            while(true) {
                System.out.print("your variable value:");
                int x = scan.nextInt();
                buf.SetValue(x);
                System.out.println("your result = " + result.GetValue());
            }
        }
    }

    // expression 表达式
    private static Node expression(String str) {
        Node curItem = item(str);
        Node result = curItem;
        while (str.charAt(i) != TERM) {
            if (str.charAt(i) != '+' && str.charAt(i) != '-') {
                break;
            }
            char op = str.charAt(i++);
            if (op == '+') {
                Addition curOperator = new Addition();
                curOperator.left = curItem;
                curOperator.right = expression(str);
                result = curOperator;
            } else {
                Substration curOperator = new Substration();
                curOperator.left = curItem;
                curOperator.right = expression(str);
                result = curOperator;
            }
        }
        return result;
    }

    // item 项
    private static Node item(String str) {
        Node curFactor = factor(str);
        Node result = curFactor;
        //多个因子之间进行乘除法
        while (str.charAt(i) != TERM) {
            if (str.charAt(i) != '*' && str.charAt(i) != '/') {
                break;
            }
            char op = str.charAt(i++);

            if (op == '*') {
                Multiplication curOperator = new Multiplication();
                curOperator.left = curFactor;
                curOperator.right = item(str);
                result = curOperator;
            } else {
                Division curOperator = new Division();
                curOperator.left = curFactor;
                curOperator.right = item(str);
                result = curOperator;
            }
        }
        return result;
    }

    // factor 因子
    private static Node factor(String str) {
        Node result;
        if (str.charAt(i) == '(') {
            //出现括号，跳转至表达式处理
            i++;
            result = expression(str);
            if (str.charAt(i) != ')') {
                throw new NumberFormatException();
            }
            i++;
        } else {
            //数字字符则进行数字转换
            result = number(str);
        }
        return result;
    }

    // number 数
    private static Node number(String str) {
        //提取数字，找到后续第一个非数字字符停止
        String result = "";
        int p = i;
        for (; p < str.length()-1; p++) {
            if (isDigit(str.charAt(p)) || str.charAt(p) == 'x') {
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
        String pattern3 = "x";
        i = p;
        if (result.matches(pattern1) || result.matches(pattern2)) {
            return new Operand(Integer.parseInt(result));
        } else if(result.matches(pattern3)) {
            hasVariable = true;
            return new Variable(buf);
        } else {
            throw new NumberFormatException();
        }
    }
}