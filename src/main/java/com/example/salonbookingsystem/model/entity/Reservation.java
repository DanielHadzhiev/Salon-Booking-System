package com.example.salonbookingsystem.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "comment",
    nullable = false)
    private String comment;

    @Column(name = "date_and_hour",
    nullable = false)
    private LocalDateTime dateAndHour;

    @Column(name = "additional_washing",
    nullable = false)
    private boolean additionalWashing;

    @ManyToOne
    private Services service;

    @ManyToOne
    private UserEntity user;


    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getDateAndHour() {
        return dateAndHour;
    }

    public void setDateAndHour(LocalDateTime dateAndHour) {
        this.dateAndHour = dateAndHour;
    }

    public boolean isAdditionalWashing() {
        return additionalWashing;
    }

    public void setAdditionalWashing(boolean additionalWashing) {
        this.additionalWashing = additionalWashing;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }
}
