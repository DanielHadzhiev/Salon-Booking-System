package com.example.salonbookingsystem.model.entity;

import com.example.salonbookingsystem.model.enums.RolesEnum;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    public Role() {
    }

    public Role(RolesEnum name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RolesEnum name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RolesEnum getRole() {
        return name;
    }

    public void setRole(RolesEnum role) {
        this.name = role;
    }
}
