package analyzer;

public class StringMatcherInitializer {
    private StringMatcher algorithm;

    public void setAlgorithm(StringMatcher algorithm) {
        this.algorithm = algorithm;
    }

    public boolean invokeAlgorithm(String text, String pattern) {
        return this.algorithm.isPatternFound(text, pattern);
    }
}
