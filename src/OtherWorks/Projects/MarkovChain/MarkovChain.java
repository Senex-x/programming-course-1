/*package OtherWorks.Projects.MarkovChain;

import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

public class MarkovChain {
    private static final String PATH_TEXT1 = "src/OtherWorks/Projects/MarkovChain/data/text1.txt";
    private static final String PATH_TEXT2 = "src/OtherWorks/Projects/MarkovChain/data/text2.txt";

    public static void main(String[] args) {
        WordsHandler wordsHandler = new WordsHandler();
        wordsHandler.parseFile(PATH_TEXT2);
        wordsHandler.getText(500);

        //WordsHandler.parseExcelTo("src/OtherWorks/Projects/MarkovChain/data/bugurts.xls", PATH_TEXT2);
    }


    static class WordsHandler {
        ArrayList<Word> words = new ArrayList<>();
        String base;

        static void clearText(String path) {
            StringBuilder text = new StringBuilder();
            try (BufferedReader buf = new BufferedReader(new FileReader(path))) {
                String line;
                while ((line = buf.readLine()) != null) {
                    String[] words = line.split(" ");
                    for (int i = 0; i < words.length; i++) {
                        if (!words[i].contains(";") && !words[i].contains("http") && !words[i].contains("#")) {
                           text.append(words[i] + " ");
                        }
                    }
                    text.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            writeTo(text.toString(), path);
        }


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
                            addWord(word);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void parseExcelTo(String path, String file) {
            try {
                HSSFWorkbook myExcelBook = new HSSFWorkbook(new FileInputStream(path));
                HSSFSheet myExcelSheet = myExcelBook.getSheet("Worksheet");
                StringBuilder text = new StringBuilder();
                int count = 0;
                while (true) {
                    HSSFRow row = myExcelSheet.getRow(count++);
                    if (row != null && row.getCell(0) != null && row.getCell(0).getCellType() == CellType.STRING) {
                        text.append(row.getCell(0).getStringCellValue() + "\n");
                        System.out.println(count);
                        continue;
                    }
                    if (row != null && row.getCell(0) != null && row.getCell(0).getCellType() == CellType.NUMERIC) {
                        text.append(row.getCell(0).getNumericCellValue() + "\n");
                        System.out.println(count);
                        continue;
                    }
                    break;
                }
                writeTo(text.toString(), file);
                myExcelBook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        static void writeTo(String text, String path) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
                writer.write(text);
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
                if(word.contains("@")) {
                    System.out.println("\n" + word);
                } else {
                    System.out.print(word + " ");
                }
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
*/