class Solution {
public:
    int robotSim(vector<int>& commands, vector<vector<int>>& obstacles) {
        // Store obstacles as "x,y"
        unordered_set<string> blocked;

        for (auto& obstacle : obstacles) {
            blocked.insert(to_string(obstacle[0]) + "," + to_string(obstacle[1]));
        }

        // North, East, South, West
        int dx[] = {0, 1, 0, -1};
        int dy[] = {1, 0, -1, 0};

        int x = 0, y = 0;
        int dir = 0; // starts facing North

        int ans = 0;

        for (int command : commands) {

            // Turn left
            if (command == -2) {
                dir = (dir + 3) % 4;
            }

            // Turn right
            else if (command == -1) {
                dir = (dir + 1) % 4;
            }

            // Move forward
            else {
                for (int step = 0; step < command; step++) {

                    int nx = x + dx[dir];
                    int ny = y + dy[dir];

                    string pos = to_string(nx) + "," + to_string(ny);

                    // Obstacle ahead
                    if (blocked.count(pos)) {
                        break;
                    }

                    x = nx;
                    y = ny;

                    ans = max(ans, x * x + y * y);
                }
            }
        }

        return ans;
    }
};
