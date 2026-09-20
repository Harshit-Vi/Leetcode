class Solution {
    public int reverseDegree(String s) {

        int sum = 0;

        for (int i = 0; i < s.length(); i++) {

            int position = i + 1;

            int reverseValue = 26 - (s.charAt(i) - 'a');

            sum += position * reverseValue;
        }

        return sum;
    }
}