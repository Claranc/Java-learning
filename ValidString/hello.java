import java.util.Scanner;

public class hello {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Please input your Four arithmetic expressions: ");
        String str = scan.nextLine();

        System.out.println(str.substring(2,3));
//        StringBuilder str2 = new StringBuilder();
//        for(int i = 0; i < str.length(); i++) {
//            if(str.charAt(i) != ' ') {
//                str2.append(str.charAt(i));
//            }
//        }
        //System.out.println("isValid: "+ recursive.biaodashi(str2.toString(), 0));
    }
}
