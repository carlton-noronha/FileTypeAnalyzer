package analyzer;

public class NaiveAlgorithm implements StringMatcher{
    @Override
    public boolean isPatternFound(String text, String pattern) {
        return naiveAlgorithm(text, pattern);
    }

    private static boolean naiveAlgorithm(String text, String pattern) {
        int patternLength = pattern.length();
        int textLength = text.length();

        if (textLength < patternLength) {
            return false;
        }

        boolean matchFound;

        for (int i = 0; i < textLength - patternLength + 1; ++i) {
            matchFound = true;

            for(int j = 0; j < patternLength; ++j) {
                if(text.charAt(i + j) != pattern.charAt(j)) {
                    matchFound = false;
                    break;
                }
            }

            if (matchFound) {
                return true;
            }
        }

        return false;
    }
}
