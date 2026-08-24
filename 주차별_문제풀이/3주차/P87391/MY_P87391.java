
public class MY_P87391 {
	static int N, M;
	static long xMin, xMax, yMin, yMax;
	static final int[] dx = { 0, 0, -1, 1 };
	static final int[] dy = { -1, 1, 0, 0 };
	static long answer;

	public static long solution(int n, int m, int x, int y, int[][] queries) {
		answer = 0;
		N = n;
		M = m;
		xMin = x; xMax = x; yMin = y; yMax = y;

		// 도착점에서 역으로 가는 대신,
		// 역으로 가는 방향의 반대 방향이 벽일 때
		// 역으로 가거나, 가지 않거나 두 개의 경우의 수가 나올 수 있음

		reverseMove(queries);

		answer = (long) (xMax - xMin + 1) * (yMax - yMin + 1);
		
		if (yMin >= M || xMin >= N || xMax < 0 || yMax < 0)
			answer = 0;
		
		return answer;
	}

	static void reverseMove(int[][] queries) {

	    for (int idx = queries.length - 1; idx >= 0; idx--) {

	        int d = queries[idx][0];
	        int num = queries[idx][1];

	        if (d == 0 && yMin == 0) {
	            yMax = Math.min(yMax + num, M - 1);

	        } else if (d == 0) {
	            yMin += num;
	            yMax += num;
	        }

	        if (d == 1 && yMax == M - 1) {
	            yMin = Math.max(yMin - num, 0);

	        } else if (d == 1) {
	            yMin -= num;
	            yMax -= num;
	        }

	        if (d == 2 && xMin == 0) {
	            xMax = Math.min(xMax + num, N - 1);

	        } else if (d == 2) {
	            xMin += num;
	            xMax += num;
	        }

	        if (d == 3 && xMax == N - 1) {
	            xMin = Math.max(xMin - num, 0);

	        } else if (d == 3) {
	            xMin -= num;
	            xMax -= num;
	        }

	        // 범위 전체가 격자 밖으로 나감
	        if (yMin >= M || xMin >= N || xMax < 0 || yMax < 0) {
	            return;
	        }

	        // 일부만 격자 밖으로 나간 경우 보정
	        yMax = Math.min(yMax, M - 1);
	        yMin = Math.max(yMin, 0);

	        xMax = Math.min(xMax, N - 1);
	        xMin = Math.max(xMin, 0);
	    }
	}

	static boolean isValid(long x, long y) {
		return x >= 0 && x < N && y >= 0 && y < M;
	}

	public static void main(String[] args) {
		System.out.println(solution(2, 2, 0, 0, new int[][] { { 2, 1 }, { 0, 1 }, { 1, 1 }, { 0, 1 }, { 2, 1 } }));
		System.out.println(
				solution(2, 5, 0, 0, new int[][] { { 3, 1 }, { 2, 2 }, { 1, 1 }, { 2, 3 }, { 0, 1 }, { 2, 1 } }));
	}

}
