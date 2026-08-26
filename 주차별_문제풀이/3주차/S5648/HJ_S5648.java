import java.io.*;
import java.util.*;

public class Solution {
	
	static final int LIMIT = 2000;
    static final int SIZE = 4001;

    // (x, y)를 하나의 key로 바꾼 뒤 상태를 저장
    // 0  : 아직 원자 없음
    // 양수: moved 리스트의 index + 1
    // -1 : 이미 충돌이 발생한 위치
    static int[] occupied = new int[SIZE * SIZE];
	
	static class Atom {
		int x;
		int y;
		int dir;
		int energy;
		
		Atom(int x, int y, int dir, int energy) {
			this.x = x;
			this.y = y;
			this.dir = dir;
			this.energy = energy;
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int testCase = Integer.parseInt(br.readLine());
		
		int[] dx = {0,0,-1,1};
		int[] dy = {1,-1,0,0};
		
		for (int tc = 1; tc <= testCase; tc++) {
			int atomNum = Integer.parseInt(br.readLine());
			List<Atom> atoms = new ArrayList<>();
		
			for (int i = 0; i < atomNum; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken())*2;
				int y = Integer.parseInt(st.nextToken())*2;
				int dir = Integer.parseInt(st.nextToken());
				int energy = Integer.parseInt(st.nextToken());
				
				atoms.add(new Atom(x, y, dir, energy));
			}
			
			int answer = 0;
			
			while(!atoms.isEmpty()) {
				List<Atom> moved = new ArrayList<>(atoms.size());
				
				int[] touched = new int[atoms.size()];
                int touchedCount = 0;
				
				for (Atom atom : atoms) {
				    atom.x += dx[atom.dir];
				    atom.y += dy[atom.dir];

				    if (atom.x > 2000 || atom.x < -2000
				            || atom.y > 2000 || atom.y < -2000) {
				        continue;
				    }

				    int key = (atom.x + LIMIT) * SIZE + (atom.y + LIMIT);

                    if (occupied[key] == 0) {
                    	moved.add(atom);
                    	occupied[key] = moved.size();
                    	touched[touchedCount++] = key;
                    } else if (occupied[key] > 0) {
                    	int index = occupied[key] - 1;

                        Atom first = moved.get(index);

                        // 첫 번째 원자 + 현재 원자 에너지
                        answer += first.energy;
                        answer += atom.energy;

                        // 첫 번째 원자도 소멸
                        moved.set(index, null);

                        // 이 좌표는 이미 충돌한 위치
                        occupied[key] = -1;
                    } else {
                    	answer += atom.energy;
                    }
				}
				
				List<Atom> nextAtoms = new ArrayList<>(moved.size());

                for (Atom atom : moved) {
                    if (atom != null) {
                        nextAtoms.add(atom);
                    }
                }
				
				for (int i = 0; i < touchedCount; i++) {
                    occupied[touched[i]] = 0;
                }

                atoms = nextAtoms;
			}
			
			sb.append('#').append(tc).append(' ').append(answer).append('\n');
		}
		
		System.out.print(sb);
	}
}