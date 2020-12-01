package OtherWorks.Solutions;

public class Solution2 {
    static int wow(String s) {
        String sub = "wow";
        int counter = 0;

        for (int i = 0; i < s.length() - sub.length() + 1; i++) {
            if (s.charAt(i) == sub.charAt(0)) {
                for (int j = i + 1; j < i + sub.length(); j++) {
                    if (s.charAt(j) != sub.charAt(j - i)) {
                        break;
                    }
                    if (j == i + sub.length() - 1) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }
}


