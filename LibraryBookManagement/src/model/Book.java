package model;

import java.util.List;

public class Book {
    private String id;
    private String title;
    private String isbn;
    private List<String> authorIds;

    public Book(String id, String title, String isbn, List<String> authorIds) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.authorIds = authorIds;
    }


    public String getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public List<String> getAuthorIds() {
        return authorIds;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAuthorIds(List<String> authorIds) {
        this.authorIds = authorIds;
    }
}
