package Homeworks.SecondSemester.Month04.HomeworkTo29.task;

public class Item {
    final int id;
    final String name;

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Item " +
                "(ID:" + Type.IdHandler.formatId(id) +
                ") '" + name + '\'';
    }
}
