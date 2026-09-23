import java.util.*;

class Node {
    int num;
    int x;
    int y;
    Node left;
    Node right;

    Node(int num, int x, int y) {
        this.num = num;
        this.x = x;
        this.y = y;
    }
}

class Solution {
    
    static ArrayList<Integer> pre;
    static ArrayList<Integer> post;
    
    void insert(Node currentNode, Node newNode) {
        if (newNode.x < currentNode.x) {

            if (currentNode.left == null) {
                currentNode.left = newNode;
            } else {
                insert(currentNode.left, newNode);
            }

        } else {

            if (currentNode.right == null) {
                currentNode.right = newNode;
            } else {
                insert(currentNode.right, newNode);
            }
        }
    }

    void preorder(Node node){
        if (node == null) {
            return;
        }
        pre.add(node.num);     
        preorder(node.left);
        preorder(node.right);        
    }
    
    void postorder(Node node){
        if (node == null) {
            return;
        }
        postorder(node.left);
        postorder(node.right);      
        post.add(node.num);    
    }
    
    public int[][] solution(int[][] nodeinfo) {
        int[][] answer = new int[2][nodeinfo.length];
        
        // 1. 트리 만들기 
        // 노드 저장
        ArrayList<Node> nodeList=new ArrayList<>();
        for(int i=0;i<nodeinfo.length;i++){
            nodeList.add(new Node(i+1, nodeinfo[i][0], nodeinfo[i][1]));
        }
        
        //y 가 큰 순서대로 sort하면 제일 y가 큰 맨 앞 노드가 루트가 됨
        nodeList.sort((a, b) -> b.y - a.y);      
        Node root=nodeList.get(0);
        
        for(int i=1;i<nodeList.size();i++){
            insert(root, nodeList.get(i));
        }
        
        //2. 전위, 후위 순회 만들기
        pre = new ArrayList<>();
        post = new ArrayList<>();
            
        preorder(root);
        postorder(root);
        
        for(int i=0;i<pre.size();i++){
            answer[0][i]=pre.get(i);
            answer[1][i]=post.get(i);
        }
                         
        return answer;
    }
}
