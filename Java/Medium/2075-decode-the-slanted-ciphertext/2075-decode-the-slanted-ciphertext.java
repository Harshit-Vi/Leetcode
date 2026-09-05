class Solution {
    public String decodeCiphertext(String encodedText, int rows) {
        int n = encodedText.length();

        if (rows == 1) {
            return encodedText;
        }

        int cols = n / rows;
        StringBuilder result = new StringBuilder();

        for (int start = 0; start < cols; start++) {
            int index = start;

            while (index < n) {
                result.append(encodedText.charAt(index));
                index += cols + 1;
            }
        }

        // Remove trailing spaces
        while (result.length() > 0 &&
               result.charAt(result.length() - 1) == ' ') {
            result.deleteCharAt(result.length() - 1);
        }

        return result.toString();
    }
}