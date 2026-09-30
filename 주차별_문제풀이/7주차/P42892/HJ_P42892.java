import java.util.Arrays;

class Solution {

    static class Node {
        int number;
        int x;
        int y;

        Node left;
        Node right;

        Node(int number, int x, int y) {
            this.number = number;
            this.x = x;
            this.y = y;
        }
    }

    int preIndex;
    int postIndex;

    public int[][] solution(int[][] nodeinfo) {

        int n = nodeinfo.length;

        Node[] nodes = new Node[n];

        // 1. 좌표 + 원래 노드 번호 저장
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(
                i + 1,
                nodeinfo[i][0],
                nodeinfo[i][1]
            );
        }

        // 2. y가 높은 순서대로 정렬
        //    같은 y라면 x가 작은 순서
        Arrays.sort(nodes, (a, b) -> {
            if (a.y != b.y) {
                return Integer.compare(b.y, a.y);
            }

            return Integer.compare(a.x, b.x);
        });

        // 가장 높은 노드가 루트
        Node root = nodes[0];

        // 3. 나머지 노드들을 x 좌표 기준으로 삽입
        for (int i = 1; i < n; i++) {
            insert(root, nodes[i]);
        }

        int[][] answer = new int[2][n];

        preIndex = 0;
        postIndex = 0;

        // 4. 전위 순회
        preorder(root, answer[0]);

        // 5. 후위 순회
        postorder(root, answer[1]);

        return answer;
    }

    // x가 작으면 왼쪽, 크면 오른쪽
    private void insert(Node root, Node newNode) {

        Node current = root;

        while (true) {

            if (newNode.x < current.x) {

                if (current.left == null) {
                    current.left = newNode;
                    return;
                }

                current = current.left;

            } else {

                if (current.right == null) {
                    current.right = newNode;
                    return;
                }

                current = current.right;
            }
        }
    }

    // 전위 순회 : 현재 → 왼쪽 → 오른쪽
    private void preorder(Node node, int[] result) {

        if (node == null) {
            return;
        }

        result[preIndex++] = node.number;

        preorder(node.left, result);
        preorder(node.right, result);
    }

    // 후위 순회 : 왼쪽 → 오른쪽 → 현재
    private void postorder(Node node, int[] result) {

        if (node == null) {
            return;
        }

        postorder(node.left, result);
        postorder(node.right, result);

        result[postIndex++] = node.number;
    }
}