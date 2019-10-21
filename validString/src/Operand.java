/*
 * 类名：Operand
 * 功能：定义常量节点
 */
public class Operand implements Node {
    private int value;

    Operand(int x) {
        value = x;
    }

    void SetValue(int x) {
        value = x;
    }

    public int GetValue() {
        return value;
    }
}