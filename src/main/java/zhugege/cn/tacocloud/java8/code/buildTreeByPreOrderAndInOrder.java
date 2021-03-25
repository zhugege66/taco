package zhugege.cn.tacocloud.java8.code;

import java.util.Map;

public class buildTreeByPreOrderAndInOrder {

    public static void main(String[] args){


    }

    public static TreeNode buildTree(int[] preOrder, int preStart, int preEnd,
                                     int[] inOrder, int inStart, int inEnd,
                                     Map<Integer, Integer> inMap){

        if(preStart > preEnd || inStart > inEnd){
            return null;
        }

        TreeNode root = new TreeNode(preOrder[preStart]);
        int rootIndex = inMap.get(root.val);
        int numsLeft = rootIndex - preStart;

        root.left = buildTree(preOrder,preStart + 1, preStart + numsLeft,
                inOrder,inStart,rootIndex - 1,inMap);
        root.right = buildTree(preOrder,preStart + numsLeft + 1,preEnd,
                inOrder,rootIndex + 1,inEnd,inMap);
        return root;
    }


}

class TreeNode{

    int val;
    TreeNode left, right;
    public TreeNode(int val){
        this.val = val;
    }
}