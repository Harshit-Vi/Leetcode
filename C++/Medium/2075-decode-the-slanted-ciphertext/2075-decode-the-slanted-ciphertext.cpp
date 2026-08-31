class Solution {
public:
    string decodeCiphertext(string encodedText, int rows) {
        int n = encodedText.size();
        int cols = n / rows;

        string ans;

        // Start from every column in the first row
        for (int startCol = 0; startCol < cols; startCol++) {

            int row = 0;
            int col = startCol;

            // Move diagonally down-right
            while (row < rows && col < cols) {
                ans += encodedText[row * cols + col];

                row++;
                col++;
            }
        }

        // Remove trailing spaces
        while (!ans.empty() && ans.back() == ' ') {
            ans.pop_back();
        }

        return ans;
    }
};