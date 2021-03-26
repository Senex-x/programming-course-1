package OtherWorks.Projects.MarkovChain;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MarkovChain {
    private static final String PATH = "src/OtherWorks/Projects/MarkovChain/data/text.txt";

    public static void main(String[] args) {
        WordsHandler wordsHandler = new WordsHandler();
        wordsHandler.parseFile(PATH);
        wordsHandler.getText(500);
    }

    static class WordsHandler {
        ArrayList<Word> words = new ArrayList<>();
        String base;

        void parseFile(String path) {
            try (BufferedReader buf = new BufferedReader(new FileReader(path))) {
                String line;
                boolean firstTime = true;
                while ((line = buf.readLine()) != null) {
                    String[] words = line.split(" ");
                    for (String word : words) {
                        if (firstTime) {
                            addFirstBase(word);
                            firstTime = false;
                        } else {
                            // parser crutch
                            if (!word.contains("[") && !word.contains("]"))
                                addWord(word);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void addFirstBase(String word) {
            base = word;
        }

        void addWord(String next) { // new base word
            getBaseWord(base).addNext(next);
            words.add(new Word(next));
            base = next;
        }

        Word getBaseWord(String word) {
            for (Word w : words) {
                if (w.getText().equals(word)) {
                    return w;
                }
            }

            words.add(new Word(word));
            return getBaseWord(word);
        }

        boolean isFirst = true;

        void setFirstWord() {
            base = words.get(new Random().nextInt(words.size() / 10)).getText();
        }

        String getNext() {
            if (isFirst) {
                setFirstWord();
                isFirst = false;
            }
            Word next = getBaseWord(base).getNext();
            base = next.getText();
            return next.getText();
        }

        public void getText(int length) {
            String word = "";
            for (int i = 1; i < length + 1 && word != null; i++) {
                word = getNext();
                System.out.print(word + " ");
                if (i % 10 == 0) {
                    System.out.println();
                }
            }
        }

        @Override
        public String toString() {
            return "WordsHandler{" +
                    "words=" + words +
                    ", base='" + base + '\'' +
                    '}';
        }

        static class Word {
            ArrayList<Word> next = new ArrayList<>();
            String text;
            int count;

            public Word(String text) {
                this.text = text;
            }

            public void addNext(String word) {
                for (Word w : next) {
                    if (w.getText().equals(word)) {
                        w.incrementCounter();
                        return;
                    }
                }

                next.add(new Word(word));
                addNext(word);
            }

            Word getNext() {
                int total = 0;
                for (Word w : next) {
                    total += w.getCount();
                }
                //System.out.println(this);
                //System.out.println(next);

                int result = new Random().nextInt(total + 1);
                //System.out.println("r " + result + " t " + total);

                for (Word w : next) {
                    result -= w.getCount();
                    if (result <= 0) {
                        //System.out.println(w);
                        return w;
                    }
                }
                return null;
            }

            void incrementCounter() {
                count++;
            }

            public String getText() {
                return text;
            }

            public int getCount() {
                return count;
            }

            @Override
            public String toString() {
                return "\n\t\tWord{" +
                        "next=" + next +
                        ", text='" + text + '\'' +
                        ", count=" + count +
                        '}';
            }
        }
    }
}
