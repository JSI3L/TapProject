/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tap_u3tv.classes;

/**
 *
 * @author angel
 */
public class Comment {

    private int id;
    private String content;
    private String createdAt;
    private int userdId;
    private int themeId;

    public Comment() {
    }

    public Comment(int id, String content, String createdAt, int userdId, int themeId) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.userdId = userdId;
        this.themeId = themeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getUserdId() {
        return userdId;
    }

    public void setUserdId(int userdId) {
        this.userdId = userdId;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }
    
}
