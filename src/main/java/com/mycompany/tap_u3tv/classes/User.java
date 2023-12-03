/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tap_u3tv.classes;

/**
 *
 * @author angel
 */
public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private String pfpPath;
    private boolean isAdmin;
    private boolean isActive;

    public User(int id, String name, String email, String password, String pfpPath, boolean isAdmin, boolean isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.pfpPath = pfpPath;
        this.isAdmin = isAdmin;
        this.isActive = isActive;
    }

    public User() {
    }

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

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getPfpPath() {
        return pfpPath;
    }

    public void setPfpPath(String pfpPath) {
        this.pfpPath = pfpPath;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", isAdmin=" + isAdmin + ", isActive=" + isActive + '}';
    }

}
