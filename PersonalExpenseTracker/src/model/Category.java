package model;

public class Category {
    private final String id;
    private final String name;
    private final String parentId;

    public enum type{
        SYSTEM, USER
    }

    // constructor
    public Category(String id, String name, String parentId){
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    // getters
    public String getName(){return name;}
    public String getId(){return id;}
    public String getParentId(){return parentId;}
}
