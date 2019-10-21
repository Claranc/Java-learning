/*
 * 类名：Addition
 * 功能：加法节点（左节点的值加上右节点的值）
 */
public class Addition implements Node {
    public Node left;
    public Node right;

    public int GetValue() {
        return left.GetValue() + right.GetValue();
    }
}