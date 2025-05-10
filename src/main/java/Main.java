import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static AtomicInteger length3 = new AtomicInteger(0);
    static AtomicInteger length4 = new AtomicInteger(0);
    static AtomicInteger length5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Thread thread1 = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    incrementLengthCounter(text.length());
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                if (isSorted(text)) {
                    incrementLengthCounter(text.length());
                }
            }
        });
        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                if (isUniform(text)) {
                    incrementLengthCounter(text.length());
                }
            }
        });

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.printf("""
                Красивых слов с длиной 3: %d шт
                Красивых слов с длиной 4: %d шт
                Красивых слов с длиной 5: %d шт""", length3.get(), length4.get(), length5.get());

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String word) {
        int len = word.length();
        for (int i = 0; i < len / 2; i++) {
            if (word.charAt(i) != word.charAt(len - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isUniform(String word) {
        char first = word.charAt(0);
        for (int i = 1; i < word.length(); i++) {
            if (word.charAt(i) != first) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSorted(String word) {
        for (int i = 1; i < word.length(); i++) {
            if (word.charAt(i) < word.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static void incrementLengthCounter(int length) {
        switch (length) {
            case 3 -> length3.incrementAndGet();
            case 4 -> length4.incrementAndGet();
            case 5 -> length5.incrementAndGet();
        }
    }
}
