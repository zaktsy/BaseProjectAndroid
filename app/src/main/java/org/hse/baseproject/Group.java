package org.hse.baseproject;

public class Group {

    private static Integer idCounter = 0;

    private Integer id;
    private String name;

    public Group(String name) {
        this.id = ++idCounter;
        this.name = name;
    }

    public Integer getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }
}
