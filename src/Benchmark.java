import java.util.*;

public class Benchmark {

    private static final Random random = new Random(42);

    private static final int[] PATTERN_COUNTS = {
            10, 100, 1000
    };

    private static final int[] TEXT_LENGTHS = {
            1000, 10000, 100000
    };

    private static final int REPETITIONS = 5;

    public static String generateText(int length) {
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < length; i++) {
            text.append((char) ('a' + random.nextInt(4)));
        }

        return text.toString();
    }

    public static List<String> generatePatterns(int count) {
        List<String> patterns = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int length = 5 + random.nextInt(6);

            StringBuilder pattern = new StringBuilder();

            for (int j = 0; j < length; j++) {
                pattern.append((char) ('a' + random.nextInt(4)));
            }

            patterns.add(pattern.toString());
        }

        return patterns;
    }

    public static int kmpSearch(String text, String pattern) {

        int n = text.length();
        int m = pattern.length();

        int[] lps = new int[m];

        // Build LPS array
        int len = 0;
        int i = 1;

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        // Search
        int matches = 0;

        i = 0;
        int j = 0;

        while (i < n) {

            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }

            if (j == m) {
                matches++;
                j = lps[j - 1];
            } else if (i < n &&
                    text.charAt(i) != pattern.charAt(j)) {

                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return matches;
    }

    public static int runKMP(
            String text,
            List<String> patterns) {

        int totalMatches = 0;

        for (String pattern : patterns) {
            totalMatches += kmpSearch(text, pattern);
        }

        return totalMatches;
    }

    public static void main(String[] args) {

        System.out.println("AHO-CORASICK VS KMP BENCHMARK");

        System.out.printf(
                "%-12s %-12s %-20s %-20s%n",
                "Patterns",
                "Text Size",
                "Aho-Corasick (ms)",
                "KMP (ms)"
        );

        for (int patternCount : PATTERN_COUNTS) {

            for (int textLength : TEXT_LENGTHS) {

                List<String> patterns =
                        generatePatterns(patternCount);

                String text = generateText(textLength);

                long ahoTotal = 0;
                long kmpTotal = 0;

                int ahoMatches = 0;
                int kmpMatches = 0;

                AhoCorasick warmup = new AhoCorasick();

                for (String pattern : patterns) {
                    warmup.addPattern(pattern);
                }

                warmup.buildFailureLinks();
                warmup.search(text);

                runKMP(text, patterns);

                for (int r = 0; r < REPETITIONS; r++) {

                    // Aho-Corasick
                    long startAho = System.nanoTime();

                    AhoCorasick ac = new AhoCorasick();

                    for (String pattern : patterns) {
                        ac.addPattern(pattern);
                    }

                    ac.buildFailureLinks();

                    ahoMatches = ac.search(text);

                    long endAho = System.nanoTime();

                    ahoTotal += endAho - startAho;

                    long startKmp = System.nanoTime();

                    kmpMatches = runKMP(text, patterns);

                    long endKmp = System.nanoTime();

                    kmpTotal += endKmp - startKmp;
                }

                double ahoAverage =
                        ahoTotal / (double) REPETITIONS / 1_000_000;

                double kmpAverage =
                        kmpTotal / (double) REPETITIONS / 1_000_000;

                System.out.printf(
                        "%-12d %-12d %-20.4f %-20.4f%n",
                        patternCount,
                        textLength,
                        ahoAverage,
                        kmpAverage
                );

                if (ahoMatches != kmpMatches) {
                    System.out.println(
                            "WARNING: Different match counts!"
                    );
                }
            }
        }

        System.out.println("Experiments completed.");
    }
}