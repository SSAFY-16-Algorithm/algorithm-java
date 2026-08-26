import java.io.*;
import java.util.*;

class Atom {
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

class Position {
	int x;
	int y;
	
	Position(int x, int y){
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(x), Integer.valueOf(y));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		return x == other.x && y == other.y;
	}
}

public class Solution {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int testCase = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= testCase; tc++) {
			int atom_num = Integer.parseInt(br.readLine());
			List<Atom> atoms = new ArrayList<>();
		
			for (int i = 0; i < atom_num; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken())*2;
				int y = Integer.parseInt(st.nextToken())*2;
				int dir = Integer.parseInt(st.nextToken());
				int energy = Integer.parseInt(st.nextToken());
				
				atoms.add(new Atom(x, y, dir, energy));
			}
			
			int answer = 0;
			int[] dx = {0,0,-1,1};
			int[] dy = {1,-1,0,0};
			
			while(!atoms.isEmpty()) {
				Map <Position, List<Atom>> map = new HashMap<>();
				
				for (Atom atom : atoms) {
				    atom.x += dx[atom.dir];
				    atom.y += dy[atom.dir];

				    if (atom.x > 2000 || atom.x < -2000
				            || atom.y > 2000 || atom.y < -2000) {
				        continue;
				    }

				    Position pos = new Position(atom.x, atom.y);

				    if (!map.containsKey(pos)) {
				        map.put(pos, new ArrayList<>());
				    }

				    map.get(pos).add(atom);
				}
				
				List<Atom> nextAtoms = new ArrayList<>();
				
				for (List<Atom> list : map.values()) {

				    if (list.size() >= 2) {
				        for (Atom atom : list) {
				            answer += atom.energy;
				        }
				    } else {
				        nextAtoms.add(list.get(0));
				    }
				}
				
				atoms = nextAtoms;
			}
			System.out.println("#" + tc + " " + answer);
		}
	
	}
}