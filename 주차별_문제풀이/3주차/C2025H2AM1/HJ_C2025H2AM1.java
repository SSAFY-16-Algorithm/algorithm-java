import java.io.*;
import java.util.*;

public class Main {

    static int N, M;
    static List<Box> boxes = new ArrayList<>();

    static class Box {
        int k;
        int h;
        int w;

        // 0-based 좌표
        int left;
        int bottom;

        boolean alive = true;

        Box(int k, int h, int w, int left) {
            this.k = k;
            this.h = h;
            this.w = w;
            this.left = left;
        }

        int right() {
            return left + w;
        }

        int top() {
            return bottom + h;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // 1. 택배를 순서대로 투입하고 중력 적용
        for (int i = 0; i < M; i++) {

            st = new StringTokenizer(br.readLine());

            int k = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken()) - 1;

            Box box = new Box(k, h, w, c);

            dropNewBox(box);

            boxes.add(box);
        }

        StringBuilder sb = new StringBuilder();

        // 처음에는 왼쪽 하차
        boolean leftTurn = true;

        for (int removed = 0; removed < M; removed++) {

            Box target = null;

            // 현재 방향으로 꺼낼 수 있는 택배 중
            // 번호가 가장 작은 택배 선택
            for (Box box : boxes) {

                if (!box.alive) {
                    continue;
                }

                if (!canUnload(box, leftTurn)) {
                    continue;
                }

                if (target == null || box.k < target.k) {
                    target = box;
                }
            }

            // 하차
            target.alive = false;

            sb.append(target.k).append('\n');

            // 택배 하나를 빼고 나면
            // 남은 택배들이 다시 아래로 떨어진다.
            settle();

            // 왼쪽 -> 오른쪽 -> 왼쪽 -> ...
            leftTurn = !leftTurn;
        }

        System.out.print(sb);
    }

    // ---------------------------------------------------
    // 처음 새로운 택배가 들어왔을 때 아래로 떨어뜨린다.
    // ---------------------------------------------------

    static void dropNewBox(Box box) {

        int bottom = 0;

        for (Box other : boxes) {

            if (!other.alive) {
                continue;
            }

            // 가로 범위가 겹치는 택배만
            // 새 택배의 낙하를 막을 수 있다.
            if (overlapHorizontal(box, other)) {
                bottom = Math.max(bottom, other.top());
            }
        }

        box.bottom = bottom;
    }

    // ---------------------------------------------------
    // target을 현재 방향으로 빼낼 수 있는지 확인
    //
    // leftTurn == true  : 왼쪽
    // leftTurn == false : 오른쪽
    // ---------------------------------------------------

    static boolean canUnload(Box target, boolean leftTurn) {

        for (Box other : boxes) {

            if (!other.alive || other == target) {
                continue;
            }

            // 높이 영역이 겹치지 않는다면
            // 옆으로 이동해도 부딪히지 않는다.
            if (!overlapVertical(target, other)) {
                continue;
            }

            if (leftTurn) {

                // target의 왼쪽에 다른 택배가 있다면
                // 왼쪽으로 빼는 도중 부딪힌다.
                if (other.right() <= target.left) {
                    return false;
                }

            } else {

                // target의 오른쪽에 다른 택배가 있다면
                // 오른쪽으로 빼는 도중 부딪힌다.
                if (other.left >= target.right()) {
                    return false;
                }
            }
        }

        return true;
    }

    // ---------------------------------------------------
    // 하나를 제거한 뒤 남은 모든 택배를 중력으로 재배치
    // ---------------------------------------------------

    static void settle() {

        List<Box> aliveBoxes = new ArrayList<>();

        for (Box box : boxes) {
            if (box.alive) {
                aliveBoxes.add(box);
            }
        }

        // 현재 아래에 있는 택배부터 처리한다.
        aliveBoxes.sort(Comparator.comparingInt(box -> box.bottom));

        List<Box> settled = new ArrayList<>();

        for (Box box : aliveBoxes) {

            int newBottom = 0;

            // 이미 아래쪽에 자리 잡은 택배들 중
            // 가로 영역이 겹치는 것 위에 올라가야 한다.
            for (Box below : settled) {

                if (overlapHorizontal(box, below)) {
                    newBottom = Math.max(newBottom, below.top());
                }
            }

            box.bottom = newBottom;

            settled.add(box);
        }
    }

    // ---------------------------------------------------
    // 두 택배의 가로 구간이 겹치는가?
    // [left, right) 형태
    // ---------------------------------------------------

    static boolean overlapHorizontal(Box a, Box b) {

        return Math.max(a.left, b.left)
                < Math.min(a.right(), b.right());
    }

    // ---------------------------------------------------
    // 두 택배의 세로 구간이 겹치는가?
    // [bottom, top) 형태
    // ---------------------------------------------------

    static boolean overlapVertical(Box a, Box b) {

        return Math.max(a.bottom, b.bottom)
                < Math.min(a.top(), b.top());
    }
}