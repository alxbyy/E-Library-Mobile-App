package com.example.projectkelompok1e_library;

public class BookItem {
    private String title;
    private String author;
    private String description;
    private String image_url;
    private String pdfFileName;

    // Constructor
    public BookItem(String title, String author, String description, String image_url, String pdfFileName) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.image_url = image_url;
        this.pdfFileName = pdfFileName;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPdfFileName() {
        return pdfFileName;
    }

    public void setPdfFileName(String pdfFileName) {
        this.pdfFileName = pdfFileName;
    }
}
