package model;

public class Category {
    private final String id;
    private final String name;
    private final String parentId;
    private final CategoryType type;


    // constructor
    public Category(String id, String name, String parentId, CategoryType type){
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.type = type;
    }

    // getters
    public String getName(){return name;}
    public String getId(){return id;}
    public String getParentId(){return parentId;}
    public CategoryType getType(){return type;}
}
