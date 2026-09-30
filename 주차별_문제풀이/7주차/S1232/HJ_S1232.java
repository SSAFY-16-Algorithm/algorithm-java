import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class HJ_S1232 {

    // 각 정점에 들어있는 값 또는 연산자
    static String[] nodeInfo;

    // 각 정점의 왼쪽 / 오른쪽 자식 번호
    static int[] left;
    static int[] right;

    // 현재 정점을 루트로 하는 서브트리 계산
    static double calculate(int node) {

        // 자식이 없다면 숫자 정점
        if (left[node] == 0 && right[node] == 0) {
            return Double.parseDouble(nodeInfo[node]);
        }

        // 왼쪽 서브트리 계산
        double leftValue = calculate(left[node]);

        // 오른쪽 서브트리 계산
        double rightValue = calculate(right[node]);

        // 현재 정점의 연산 수행
        if (nodeInfo[node].equals("+")) {
            return leftValue + rightValue;
        } else if (nodeInfo[node].equals("-")) {
            return leftValue - rightValue;
        } else if (nodeInfo[node].equals("*")) {
            return leftValue * rightValue;
        } else {
            return leftValue / rightValue;
        }
    }

    public static void main(String[] args) throws NumberFormatException, IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();
        StringTokenizer st;

        for (int testCase = 1; testCase <= 10; testCase++) {

            int N = Integer.parseInt(br.readLine());

            // 정점 번호가 1부터 N까지이므로 N + 1
            nodeInfo = new String[N + 1];
            left = new int[N + 1];
            right = new int[N + 1];

            for (int i = 0; i < N; i++) {

                st = new StringTokenizer(br.readLine());
                int tokenCount = st.countTokens();

                if (tokenCount == 2) {
                    // 숫자 정점
                    int node = Integer.parseInt(st.nextToken());
                    String value = st.nextToken();
                    nodeInfo[node] = value;

                } else if (tokenCount == 4) {

                    // 연산자 정점
                    int node = Integer.parseInt(st.nextToken());
                    String operator = st.nextToken();
                    int leftChild = Integer.parseInt(st.nextToken());
                    int rightChild = Integer.parseInt(st.nextToken());

                    nodeInfo[node] = operator;
                    left[node] = leftChild;
                    right[node] = rightChild;
                    
                }
            
            }

            // 루트 정점은 항상 1번
            double result = calculate(1);

            // 소수점 이하 버리기
            output.append('#').append(testCase).append(' ').append((int) result).append('\n');
            
        }

        System.out.print(output);
        
    }
    
}