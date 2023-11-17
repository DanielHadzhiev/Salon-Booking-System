package com.example.salonbookingsystem.model.entity;

import com.example.salonbookingsystem.model.enums.GenderEnum;

import javax.persistence.*;

@Entity
@Table(name = "genders")
public class Gender{
    public Gender() {
    }

    public Gender(GenderEnum gender) {
        this.gender = gender;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "gender",
            nullable = false)
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }
}
