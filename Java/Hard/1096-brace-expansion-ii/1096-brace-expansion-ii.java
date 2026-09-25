import java.util.*;

class Solution {

    String s;
    int index;

    public List<String> braceExpansionII(String expression) {

        s = expression;
        index = 0;

        Set<String> result = parseExpression();

        return new ArrayList<>(result);
    }

    // Handles UNION: a,b,c
    Set<String> parseExpression() {

        Set<String> result = new TreeSet<>();

        while (index < s.length() && s.charAt(index) != '}') {

            result.addAll(parseTerm());

            if (index < s.length() && s.charAt(index) == ',') {
                index++;
            }
        }

        return result;
    }

    // Handles CONCATENATION: ab{c,d}
    Set<String> parseTerm() {

        Set<String> result = new TreeSet<>();

        // Empty string is needed for concatenation
        result.add("");

        while (index < s.length()
                && s.charAt(index) != ','
                && s.charAt(index) != '}') {

            Set<String> current = parseFactor();

            result = combine(result, current);
        }

        return result;
    }

    // Handles a single letter or {...}
    Set<String> parseFactor() {

        if (s.charAt(index) == '{') {

            index++; // skip '{'

            Set<String> result = parseExpression();

            index++; // skip '}'

            return result;

        } else {

            Set<String> result = new TreeSet<>();

            result.add(String.valueOf(s.charAt(index)));

            index++;

            return result;
        }
    }

    // Cartesian product for concatenation
    Set<String> combine(Set<String> a, Set<String> b) {

        Set<String> result = new TreeSet<>();

        for (String x : a) {
            for (String y : b) {
                result.add(x + y);
            }
        }

        return result;
    }
}