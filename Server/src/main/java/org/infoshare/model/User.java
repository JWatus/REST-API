package org.infoshare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Random;


public class User implements Serializable {
    private static final long serialVersionUID = -1L;

    private String name;
    private String surname;
    private int id;
    @JsonIgnore
    private Credentials credentials;

    public User() {
    }

    public User(String name, String surname, Credentials credentials) {
        this.name = name;
        this.surname = surname;
        this.id = generateId();
        this.credentials = credentials;
    }

    private int generateId() {
        Random r = new Random();
        return r.nextInt(Integer.MAX_VALUE);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getId() {
        return id;
    }
    @JsonIgnore
    public Credentials getCredentials() {
        return credentials;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", id=" + id +
                ", credentials=" + credentials +
                '}';
    }
}


