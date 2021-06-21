package OtherWorks.Exams.Prep;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Task {
    private static class Group {
        int id;
        String name;
        String country;
        String genre;
        String leader;

        public Group(int id, String name, String country, String genre, String leader) {
            this.id = id;
            this.name = name;
            this.country = country;
            this.genre = genre;
            this.leader = leader;
        }
    }

    private static class Composition {
        int id;
        int releaseYear;
        String name;

        public Composition(int id, int releaseYear, String name) {
            this.id = id;
            this.releaseYear = releaseYear;
            this.name = name;
        }
    }

    private static class WhoseComposition {
        int compositionId;
        int musicianId;

        public WhoseComposition(int compositionId, int musicianId) {
            this.compositionId = compositionId;
            this.musicianId = musicianId;
        }
    }

    private static class Musician {
        int id;
        String name;
        int birthYear;
        String birthCountry;
        String currentCountry;

        public Musician(int id, String name, int birthYear, String birthCountry, String currentCountry) {
            this.id = id;
            this.name = name;
            this.birthYear = birthYear;
            this.birthCountry = birthCountry;
            this.currentCountry = currentCountry;
        }
    }

    private static class Play {
        int groupId;
        int compositionId;
        int year;

        public Play(int groupId, int compositionId, int year) {
            this.groupId = groupId;
            this.compositionId = compositionId;
            this.year = year;
        }
    }

    private static class Membership {
        int musicianId;
        int groupId;

        public Membership(int musicianId, int groupId) {
            this.musicianId = musicianId;
            this.groupId = groupId;
        }
    }

    private static ArrayList<String> readFromFileToArrayList(String path) {
        ArrayList<String> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.addAll(Arrays.asList(line.split("\\\\t")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static final ArrayList<Group> groups = parseGroup(
            readFromFileToArrayList("src/OtherWorks/Exams/Prep/data/group.txt"));
    private static final ArrayList<Composition> compositions = parseComposition(
            readFromFileToArrayList("src/OtherWorks/Exams/Prep/data/composition.txt"));
    private static final ArrayList<WhoseComposition> whoseCompositions = parseWhoseComposition(
            readFromFileToArrayList("src/OtherWorks/Exams/Prep/data/whos-composition.txt"));
    private static final ArrayList<Musician> musicians = parseMusician(
            readFromFileToArrayList("src/OtherWorks/Exams/Prep/data/musician.txt"));
    private static final ArrayList<Play> plays = parsePlay(
            readFromFileToArrayList("src/OtherWorks/Exams/Prep/data/play.txt"));
    private static final ArrayList<Membership> memberships = parseMembership(
            readFromFileToArrayList("src/OtherWorks/Exams/Prep/data/membership.txt"));

    private static ArrayList<Group> parseGroup(ArrayList<String> data) {
        ArrayList<Group> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i += 5) {
            result.add(new Group(
                    Integer.parseInt(data.get(i)),
                    data.get(i + 1),
                    data.get(i + 2),
                    data.get(i + 3),
                    data.get(i + 4)
            ));
        }
        return result;
    }

    private static ArrayList<Composition> parseComposition(ArrayList<String> data) {
        ArrayList<Composition> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i += 3) {
            result.add(new Composition(
                    Integer.parseInt(data.get(i)),
                    Integer.parseInt(data.get(i + 1)),
                    data.get(i + 2)
            ));
        }
        return result;
    }

    private static ArrayList<WhoseComposition> parseWhoseComposition(ArrayList<String> data) {
        ArrayList<WhoseComposition> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i += 2) {
            result.add(new WhoseComposition(
                    Integer.parseInt(data.get(i)),
                    Integer.parseInt(data.get(i + 1))
            ));
        }
        return result;
    }

    private static ArrayList<Musician> parseMusician(ArrayList<String> data) {
        ArrayList<Musician> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i += 5) {
            result.add(new Musician(
                    Integer.parseInt(data.get(i)),
                    data.get(i + 1),
                    Integer.parseInt(data.get(i + 2)),
                    data.get(i + 3),
                    data.get(i + 4)
            ));
        }
        return result;
    }

    private static ArrayList<Play> parsePlay(ArrayList<String> data) {
        ArrayList<Play> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i += 3) {
            result.add(new Play(
                    Integer.parseInt(data.get(i)),
                    Integer.parseInt(data.get(i + 1)),
                    Integer.parseInt(data.get(i + 2))
            ));
        }
        return result;
    }

    private static ArrayList<Membership> parseMembership(ArrayList<String> data) {
        ArrayList<Membership> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i += 2) {
            result.add(new Membership(
                    Integer.parseInt(data.get(i)),
                    Integer.parseInt(data.get(i + 1))
            ));
        }
        return result;
    }


    public static void main(String[] args) {
        task1();
    }

    private static void task1() {
        List<Integer> groupsIdList = groups.stream()
                .filter(group -> group.country.equals("England") || group.country.equals("Germany"))
                .flatMap((Function<Group, Stream<Integer>>) group -> IntStream.of(group.id).boxed())
                .collect(Collectors.toList());

        System.out.println(groupsIdList); // list of groups from given countries

        List<Integer> musiciansIdList = memberships.stream()
                .filter(membership -> groupsIdList.stream().anyMatch(groupId -> groupId == membership.groupId))
                .flatMap((Function<Membership, Stream<Integer>>) membership -> IntStream.of(membership.musicianId).boxed())
                .collect(Collectors.toList());

        System.out.println(musiciansIdList); // list of proper musicians id

        musicians.stream()
                .filter(musician -> musiciansIdList.stream().anyMatch(musicianId-> musicianId == musician.id))
                .forEach(musician -> System.out.println(musician.name));
    }
}
