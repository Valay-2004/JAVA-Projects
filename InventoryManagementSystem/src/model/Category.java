package model;
import java.io.Serializable;
public class Category implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;

    public Category(String id, String name){
        this.id = id;
        this.name = name;
    }

    // Getters

    public String getId() {
        return id;
    }
    public String getName(){
        return name;
    }
}
