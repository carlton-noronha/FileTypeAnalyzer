package analyzer;

public class KMPAlgorithm implements StringMatcher{
    @Override
    public boolean isPatternFound(String text, String pattern) {
        return performKMP(generatePrefixFunction(pattern), text, pattern);
    }

    private boolean performKMP(int[] prefixFunction, String text, String pattern) {
        int j= 0;
        int textLength = text.length();
        int patternLength = pattern.length();

        for(int i = 0; i < textLength; ++i) {
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = prefixFunction[j - 1];
            }

            if (text.charAt(i) == pattern.charAt(j)) {
                j += 1;
            }

            if (j == patternLength) {
                return true;
            }
        }

        return false;
    }

    private int[] generatePrefixFunction(String pattern) {
        int length = pattern.length();
        int[] prefixFunction = new int[length];

        for(int i = 1; i < length; ++i) {
            int longestBorder = i - 1;

            do {
                if (pattern.charAt(prefixFunction[longestBorder]) == pattern.charAt(i)) {
                    prefixFunction[i] = prefixFunction[longestBorder] + 1;
                    break;
                } else {
                    longestBorder = prefixFunction[longestBorder] - 1;
                }
            } while (longestBorder >= 0);

        }

        return prefixFunction;
    }
}
