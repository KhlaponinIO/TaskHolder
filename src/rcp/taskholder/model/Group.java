package rcp.taskholder.model;

import java.util.ArrayList;

public class Group {

    private int id;

    private ArrayList<Person> students = new ArrayList<>();

    public Group(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Person> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Person> students) {
        this.students = students;
    }

    public void setStudent(Person student) {
        students.add(student);
    }

    public Person getStudent(int id) {
        return students.get(id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Group other = (Group) obj;
        if (id != other.id)
            return false;
        return true;
    }

}
