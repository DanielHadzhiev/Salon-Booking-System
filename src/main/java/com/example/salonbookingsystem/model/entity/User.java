package com.example.salonbookingsystem.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "userPhoto",
            nullable = false)
    @Lob
    private byte[] userPhoto;

    @Column(name = "name",
            nullable = false)
    private String name;

    @Column(name = "email",
            unique = true,
            nullable = false)
    private String email;

    @Column(name = "password",
            nullable = false)
    private String password;

    @ManyToOne
    private Role role;

    @ManyToOne
    private Gender gender;

    @OneToMany(targetEntity = Reservation.class,
            mappedBy = "user")
    private List<Reservation> reservations;

    public User() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(byte[] userPhoto) {
        this.userPhoto = userPhoto;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
