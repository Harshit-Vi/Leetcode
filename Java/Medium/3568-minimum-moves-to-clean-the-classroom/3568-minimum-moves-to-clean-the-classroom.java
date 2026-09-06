import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int sr = 0, sc = 0;
        List<int[]> litter = new ArrayList<>();

        // Find S and all L positions
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    sr = i;
                    sc = j;
                } else if (ch == 'L') {
                    litter.add(new int[]{i, j});
                }
            }
        }

        int k = litter.size();

        // No litter to collect
        if (k == 0) {
            return 0;
        }

        int allCollected = (1 << k) - 1;

        /*
         * State:
         * r, c      -> current position
         * mask      -> collected litter
         * energyLeft -> remaining energy
         */

        // visited[r][c][mask][energyLeft]
        boolean[][][][] visited =
                new boolean[m][n][1 << k][energy + 1];

        Queue<State> queue = new LinkedList<>();

        queue.offer(new State(sr, sc, 0, energy, 0));
        visited[sr][sc][0][energy] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            State cur = queue.poll();

            int r = cur.r;
            int c = cur.c;
            int mask = cur.mask;
            int e = cur.energy;
            int moves = cur.moves;

            // All litter collected
            if (mask == allCollected) {
                return moves;
            }

            // If energy is 0, we can only move if we're standing on R.
            if (e == 0 && classroom[r].charAt(c) != 'R') {
                continue;
            }

            // If standing on R, restore energy
            if (classroom[r].charAt(c) == 'R') {
                e = energy;
            }

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                // Outside grid
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                    continue;
                }

                // Obstacle
                if (classroom[nr].charAt(nc) == 'X') {
                    continue;
                }

                // Need energy to make a move
                if (e == 0) {
                    continue;
                }

                int newEnergy = e - 1;
                int newMask = mask;

                // Check if this cell contains litter
                for (int i = 0; i < k; i++) {
                    if (litter.get(i)[0] == nr &&
                        litter.get(i)[1] == nc) {

                        newMask |= (1 << i);
                        break;
                    }
                }

                // If the new cell is R, energy is restored
                if (classroom[nr].charAt(nc) == 'R') {
                    newEnergy = energy;
                }

                if (!visited[nr][nc][newMask][newEnergy]) {
                    visited[nr][nc][newMask][newEnergy] = true;

                    queue.offer(
                        new State(
                            nr,
                            nc,
                            newMask,
                            newEnergy,
                            moves + 1
                        )
                    );
                }
            }
        }

        return -1;
    }

    static class State {
        int r, c, mask, energy, moves;

        State(int r, int c, int mask, int energy, int moves) {
            this.r = r;
            this.c = c;
            this.mask = mask;
            this.energy = energy;
            this.moves = moves;
        }
    }
}