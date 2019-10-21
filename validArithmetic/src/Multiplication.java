/*
 * 类名：Multiplication
 * 功能：乘法节点（左节点的乘以右节点的值）
 */
public class Multiplication implements Node {
    public Node left;
    public Node right;

    public int GetValue() {
        return left.GetValue() * right.GetValue();
    }
}