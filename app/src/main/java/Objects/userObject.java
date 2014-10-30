package Objects;

/**
 * Created by agustis on 21.10.2014.
 */
public class userObject {
    private String[] projects;
    private String[] tasks;
    private String number;
    private String name;

    public String[] getProject() {
        return projects;
    }

    public void setProject(String[] project) {
        this.projects = project;
    }

    public String[] getTasks() {
        return tasks;
    }

    public void setTasks(String[] tasks) {
        this.tasks = tasks;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
