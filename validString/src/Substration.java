/*
 * 类名：Substration
 * 功能：减法节点（左节点的值减去右节点的值）
 */
public class Substration implements Node {
    public Node left;
    public Node right;

    public int GetValue() {
        return left.GetValue() - right.GetValue();
    }
}
