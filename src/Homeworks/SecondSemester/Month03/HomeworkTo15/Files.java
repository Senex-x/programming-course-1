package Homeworks.SecondSemester.Month03.HomeworkTo15;


import java.io.File;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class Files {
    public static void main(String[] args) {
        File file = new File(".");
        recursiveFileDisplay(file);
        //stackFileDisplay(file);
        //queueFileDisplay(file);
    }

    static void recursiveFileDisplay(File file) {
        if(file.isDirectory()) {
            for(File f : file.listFiles()) {
                System.out.println("Directory: " + f.getName());
                recursiveFileDisplay(f);
            }
        } else if(file.isFile()) {
            System.out.println("File: " + file.getName());
        }
    }

    static void stackFileDisplay(File file) {
        Stack<File> stack = new Stack<>();
        stack.push(file);

        while(!stack.isEmpty()) {
            File f = stack.pop();
            if(f.isDirectory()) {
                for(File cur : f.listFiles()) {
                    System.out.println("Directory: " + cur.getName());
                    stack.push(cur);
                }
            } else if(f.isFile()) {
                System.out.println("File: " + f.getName());
            }
        }
    }

    static void queueFileDisplay(File file) {
        ArrayDeque<File> d = new ArrayDeque<>();
        d.add(file);
        while(!d.isEmpty()) {
            File cur = d.pollFirst();
            if(cur.isDirectory()) {
                for(File f : cur.listFiles()) {
                    System.out.println("Directory: " + f.getName());
                    d.add(f);
                }
            } else if(cur.isFile()) {
                System.out.println("File: " + cur.getName());
            }
        }
    }


}
