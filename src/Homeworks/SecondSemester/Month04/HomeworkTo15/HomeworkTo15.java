package Homeworks.SecondSemester.Month04.HomeworkTo15;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;

public class HomeworkTo15 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Bot bot = new Bot();

        while (true) {
            String message = in.nextLine();
            if (message.equals("exit"))
                break;

            String answer = bot.processUserInput(message);
            System.out.println(answer);
        }
    }


    static class Bot {
        private final HashMap<String, Method> commands;

        public Bot() {
            commands = new HashMap<>();

            for (Method m : this.getClass().getDeclaredMethods()) {
                if (!m.isAnnotationPresent(Command.class))
                    continue;

                Command cmd = m.getAnnotation(Command.class);
                for (String name : cmd.aliases())
                    commands.put(name, m);
            }
        }


        public String processUserInput(String input) {
            if (input.isEmpty())
                return "i DoN't uNdeRStaNd!!1!11";
            String[] splitted = input.split(" ");
            String command = splitted[0].toLowerCase();
            String[] args = Arrays.copyOfRange(splitted, 1, splitted.length);

            Method m = commands.get(command);

            if (m == null)
                return "i DoN't uNdeRStaNd!!1!11";
            try {
                return (String) m.invoke(this, (Object) args);
            } catch (Exception e) {
                return "Что-то пошло не так, попробуйте ещё раз...";
            }
        }

        @Command(aliases = {"привет", "здаров", "q", "hi"},
                args = "",
                description = "А ну быстро поздоровался >:C",
                isInProgress = true)
        public String hello(String[] args) {
            return "Привет!";
        }

        @Command(aliases = {"пока", "конец", "end", "e", "finish"},
                args = "",
                description = "",
                isInProgress = false)
        public String bye(String[] args) {
            return "Возвращайся!";
        }

        @Command(aliases = {"помощь", "помоги", "команды", "help"},
                args = "Укажите команды, информацию по которым желаете получить",
                description = "Выводит список команд",
                isInProgress = false)
        public String help(String[] args) {
            StringBuilder builder = new StringBuilder("Я умею в такие команды:\n");

            for (Method m : this.getClass().getDeclaredMethods()) {
                if (!m.isAnnotationPresent(Command.class))
                    continue;
                Command cmd = m.getAnnotation(Command.class);
                if (args.length != 0) {
                    for (String arg : args) {
                        if (Arrays.stream(cmd.aliases()).anyMatch(s -> s.equalsIgnoreCase(arg))) {
                            builder.append(Arrays.toString(cmd.aliases()))
                                    .append(": ")
                                    .append(cmd.description())
                                    .append(" ")
                                    .append(cmd.args())
                                    .append("\n");
                        }
                    }
                } else {
                    builder.append(Arrays.toString(cmd.aliases()))
                            .append(": ")
                            .append(cmd.description())
                            .append(" ")
                            .append(cmd.args())
                            .append("\n");
                }
            }

            return builder.toString();
        }

        @Command(aliases = {"t", "time", "время", "сколько времени", "который час"},
                args = "",
                description = "",
                isInProgress = false)
        public String getTime(String[] args) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            return formatter.format(new Date());
        }

        @Command(aliases = {"getHash", "hash", "h", "хешировать", "хеш"},
                args = "Укажите после команды последовательность символов, которую хотите хешировать",
                description = "",
                isInProgress = true
        )
        public String getHash(String[] args) {
            StringBuilder code = new StringBuilder();
            long counter = 1;

            try {
                Object o = args[0];
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Строка для хеширования не была указана!");
                return "";
            }

            for (char c : args[0].toCharArray()) {
                counter *= c * c;
            }

            while (counter != 0) {
                code.append((char)(counter % 94 + 33));
                counter /= 94;
            }
            return code.toString();
        }

        @Command(aliases = {"s", "status", "статус"},
                args = "",
                description = "Показывает статус готовности методов",
                isInProgress = false
        )
        public String getStatus(String[] args) {

            ArrayList<Command> done = new ArrayList<>();
            ArrayList<Command> inProgress = new ArrayList<>();

            for (Method m : this.getClass().getDeclaredMethods()) {
                if (!m.isAnnotationPresent(Command.class))
                    continue;
                Command cmd = m.getAnnotation(Command.class);
                if (!cmd.isInProgress()) {
                    done.add(cmd);
                } else {
                    inProgress.add(cmd);
                }
            }
            StringBuilder builder = new StringBuilder("Стабильные команды:\n");
            for (Command cmd : done) {
                builder.append(Arrays.toString(cmd.aliases()))
                        .append(": ")
                        .append(cmd.description())
                        .append(" ")
                        .append(cmd.args())
                        .append("\n");
            }
            builder.append("Команды в разработке:\n");
            for (Command cmd : inProgress) {
                builder.append(Arrays.toString(cmd.aliases()))
                        .append(": ")
                        .append(cmd.description())
                        .append(" ")
                        .append(cmd.args())
                        .append("\n");
            }
            return builder.toString();
        }
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Command {
        String[] aliases(); // Возможные названия команды

        String args(); // описание аргументов команды

        String description(); // описание самой команды

        boolean isInProgress();
    }


}
