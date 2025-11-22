package com.example.batch.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Item {

    private int id;
    private String name;
    private LocalDate created;

    @JacksonXmlElementWrapper(localName = "tags")    // <tags> ... </tags>
    @JacksonXmlProperty(localName = "tag")           // <tag>value</tag>
    private List<String> tags;

    private BigDecimal price;

    // ===== Getter / Setter =====

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
