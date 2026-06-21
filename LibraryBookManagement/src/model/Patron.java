package model;

public class Patron {
    private String id;
    private String name;
    private String email;
    private int maxBooksAllowed;

    public Patron(String id, String name, String email, int maxBooksAllowed) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.maxBooksAllowed = maxBooksAllowed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMaxBooksAllowed() {
        return maxBooksAllowed;
    }

    public void setMaxBooksAllowed(int maxBooksAllowed) {
        this.maxBooksAllowed = maxBooksAllowed;
    }
}
