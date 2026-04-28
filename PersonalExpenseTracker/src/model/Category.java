package model;

public class Category {
    private String id;
    private String name;
    private String parentId;
    private CategoryType type;


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
