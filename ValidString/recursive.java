public class recursive {
    public static int i;
    public static boolean isDigit(Character c) {
        return c >= '0' && c <= '9';
    }

    public static boolean isOperator(Character c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }



    public static boolean biaodashi(String str) {
        if(isOperator(str.charAt(i))) {
            return false;
        } else if(isDigit(str.charAt(i))) {
            return xiang(str);
        } else if(str.charAt(i) == '(') {
            i++;
            return biaodashi(str);
        }
        return false;
    }

    public static boolean xiang(String str) {
        if(isDigit(str.charAt(i))) {

        }
        return false;
    }

    public static boolean shu(String str) {
        String curNumber = "";
        int p;
        for(p = i; p < str.length(); p++) {
            if(isDigit(str.charAt(p))) {
            } else if(isOperator(str.charAt(p)) || str.charAt(p) == ')') {
                break;
            } else {
                throw new NumberFormatException();
            }
        }
        curNumber = str.substring(i, p);
        String pattern1 = "^[0-9]";
        String pattern2 = "^[1-9][0-9]*";
        return curNumber.matches(pattern1) || curNumber.matches(pattern2);
    }
}
