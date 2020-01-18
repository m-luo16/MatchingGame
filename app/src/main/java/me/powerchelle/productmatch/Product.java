package me.powerchelle.productmatch;

public class Product {
    private String id;
    private String title;
    private String imgSrc;
    private Boolean flipped = false;

    Product(String id, String title, String imgSrc) {
        this.id = id;
        this.title = title;
        this.imgSrc = imgSrc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public Boolean getFlipped() {
        return flipped;
    }

    public void setFlipped(Boolean flipped) {
        this.flipped = flipped;
    }
}


