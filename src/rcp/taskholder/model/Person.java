package rcp.taskholder.model;

public class Person {

    private String name;
    private String group;
    private boolean taskDone;

    public Person(String name, String group, boolean taskIsDone) {
        super();
        this.name = name;
        this.group = group;
        this.taskDone = taskIsDone;
    }
    
    public Person() {
        this.name = "--empty--";
        this.group = "-1";
        this.taskDone = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isTaskDone() {
        return taskDone;
    }

    public void setTaskIsDone(boolean taskIsDone) {
        this.taskDone = taskIsDone;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((group == null) ? 0 : group.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + (taskDone ? 1231 : 1237);
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
        Person other = (Person) obj;
        if (group == null) {
            if (other.group != null)
                return false;
        } else if (!group.equals(other.group))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (taskDone != other.taskDone)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", group=" + group + ", taskDone=" + taskDone + "]";
    }
    
}
