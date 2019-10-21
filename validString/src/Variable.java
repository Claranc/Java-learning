/*
 * 类名：Variable
 * 功能：定义未知数的Class
 * 获取值的时候从对应的BUFFER里取
 */
public class Variable implements Node {
    private int value;
    private Buffer buf;

    //构造函数
    Variable(Buffer b) {buf = b;}

    private void SetValue() {
        value = buf.GetValue();
    }

    public int GetValue() {
        SetValue();
        return value;
    }
}
