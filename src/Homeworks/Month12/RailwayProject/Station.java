package Homeworks.Month12.RailwayProject;

import java.util.ArrayList;

public class Station {
    private int id;
    private String name;
    ArrayList<Way> ways;

    public Station(int id, String name, ArrayList<Way> ways) {
        this.id = id;
        this.name = name;
        this.ways = ways;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /*
    enum Stations {
        VAHITOVO,
        NOVATOROV,
        DERBYSHKI,
        KAMAEVO,
        ARSK,
        KUKMOR,
        YAGUL,
        SOSNOVKA,
        KIZNER,
        MOZHGA,
        PICHAS,
        KARAMBAI;
    }*/

}
