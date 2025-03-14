package dk.haarmonika.haarmonika.backend.db.entities;

public class Service implements IEntity {
    /**
     * Id of the service
     */
    private Integer id;
    /**
     * The name of the service
     */
    private String name;
    /**
     * The time the service takes, in seconds.
     */
    private int duration;

    public Service(Integer id, String name, int duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    public Service(String name, int duration) {
        this(null, name, duration);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public void setName(String name) {
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
