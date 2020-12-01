package OtherWorks.Solutions;

public class Solution4 {
    public static void main(String[] args) {
        P32 p = new P32("aaabbczаббABC''{{");
        System.out.println(p.getCount('a'));
        System.out.println(p.getCount('c'));
        System.out.println(p.getCount('{'));
        System.out.println(p.getCount('A'));
        System.out.println(p.getCount('z'));
    }

    static private class P32 {
        private String string;
        private int[] charCounter = new int[26];

        private P32(String string) {
            this.string = string;

            for (char c : string.toCharArray()) {
                System.out.println(c + " = " + (int)c);
                if (c - 97 >= 0 && c - 97 <= 25)
                    charCounter[c - 97]++;
            }
        }

        private int getCount(char c) {
            return (c - 97 >= 0 && c - 97 <= 25) ? charCounter[c - 97] : 0;
        }

    }
}




