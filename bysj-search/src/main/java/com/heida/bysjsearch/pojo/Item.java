package com.heida.bysjsearch.pojo;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

public class Item implements Serializable {

    @Field("id")
    private Long id;
    @Field("title")
    private String title;

    @Field("sellPoint")
    private String sellPoint;
    @Field("price")
    private Long price;
    @Field("image")
    private String images;

    public String[] getImages() {
        return images.split(",");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setImages(String images) {
        this.images = images;
    }


}