package Blind.Sight.Commnunity.util.random;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RandomId {
    private static Random random;
    private static final AtomicInteger idCounter = new AtomicInteger(1);

    private RandomId(){}

    // you can use new random instead of using setter injection (your option)
    public static void setRandom(Random random) {
        RandomId.random = random;
    }

    /**
     * Random code [a-z]+[A-Z]+[0-9]
     *
     * @return - string
     */
    public static String randomCode() {
        String str = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder stringBuilder = new StringBuilder();

        // Generate the first part (agh4)
        for (int i = 0; i < 4; i++) {
            stringBuilder.append(str.charAt(random.nextInt(str.length())));
        }

        // Insert a hyphen
        stringBuilder.append('-');

        // Generate the second part (fjju)
        for (int i = 0; i < 4; i++) {
            stringBuilder.append(str.charAt(random.nextInt(str.length())));
        }

        // Insert a hyphen
        stringBuilder.append('-');

        // Generate the third part (458f)
        for (int i = 0; i < 4; i++) {
            stringBuilder.append(str.charAt(random.nextInt(str.length())));
        }

        return stringBuilder.toString();
    }

    /**
     * Generate next value increment
     *
     * @param name - using name combine with value
     * @return - string
     */
    public static String generateCounterIncrement(String name) {
        int nextId = idCounter.getAndIncrement();
        return name + nextId;
    }

    public static String randomSimpleCode(String name, int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(random.nextInt(chars.length())));
        return name + sb;
    }
}
