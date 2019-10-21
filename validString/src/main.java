import java.util.Scanner;

/*
 * 验证四则运算表达式的合法性以及计算结果
 * 输入: 四则运算表达式 e.g: (3-2)*5-1
 * 输出: 合法表达式-计算结果/非法表达式-抛出异常
 */
class main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Please input your Four arithmetic expressions: ");
        String str = scan.nextLine();
        StringBuilder str2 = new StringBuilder(str.length());
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) != ' ') {
                str2.append(str.charAt(i));
            }
        }
//        System.out.println("Result: "+ recursive.biaodashi(str2.toString()));
        System.out.println("Result: "+ CalculatorTree.calculate(str2.toString()));
    }
}