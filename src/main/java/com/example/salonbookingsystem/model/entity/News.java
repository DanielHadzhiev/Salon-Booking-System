package com.example.salonbookingsystem.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "content",
    columnDefinition = "TEXT",
            nullable = false)
    private String content;

    @ManyToOne
    private UserEntity publisher;

    public UserEntity getPublisher() {
        return publisher;
    }

    public void setPublisher(UserEntity publisher) {
        this.publisher = publisher;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
