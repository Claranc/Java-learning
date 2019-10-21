/*
 * 类名：Division
 * 功能：除法节点（左节点的值除以右节点的值）
 */
public class Division implements Node {
    public Node left;
    public Node right;

    public int GetValue() {
        return left.GetValue() / right.GetValue();
    }
}