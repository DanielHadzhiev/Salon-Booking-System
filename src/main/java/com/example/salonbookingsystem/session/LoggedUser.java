package com.example.salonbookingsystem.session;

import com.example.salonbookingsystem.model.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
@Component
@SessionScope
public class LoggedUser {

    private long id;

    private String email;

    private String name;

    private String role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void login(User user){
        setId(user.getId());
        setName(user.getName());
        setEmail(user.getEmail());
        setRole(user.getRole().getRole().name());
    }
    public void logout(){
        setId(0);
        setName(null);
        setEmail(null);
    }

}
