import java.util.Stack;

public class bruise {
    public static boolean isValidString(String str) {
        if(str.length() <= 2) {
            return false;
        }
        Stack<Character> p = new Stack<>();
        for(int i = 0; i < str.length(); i++) {
            if(isOperator(str.charAt(i))) {
                if(!((i > 0 && (isNumber(str.charAt(i-1)) || str.charAt(i-1) == ')')) && (i+1 < str.length() &&
                        (isNumber(str.charAt(i+1)) || str.charAt(i+1) == '(')))){
                    return false;
                }
            } else if(str.charAt(i) == '(') {
                if(i > 0 && isNumber(str.charAt(i-1))) return false;
                p.push(str.charAt(i));
            } else if(str.charAt(i) == ')') {
                if(p.empty() || p.peek() != '(' || str.charAt(i-1) == '(') {
                    return false;
                }
                boolean isOperateChar = false;
                for(int j = i;; j--) {
                    if(isOperator(str.charAt(j))) {
                        isOperateChar = true;
                    } else if(str.charAt(j) == '(') {
                        break;
                    }
                }
                if(!isOperateChar) return false;
                p.pop();
            } else if(!(isNumber(str.charAt(i)))) {
                return false;
            } else if(str.charAt(i) == '0') {
                if((i == 0 && isNumber(str.charAt(i+1))) || (i > 0 && !isNumber(str.charAt(i-1)) && isNumber(str.charAt(i+1)))) {
                    return false;
                }
            }
        }
        return p.empty();
    }

    public static boolean isNumber(Character c) {
        return c >= '0' && c <= '9';
    }

    public static boolean isOperator(Character c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
}
