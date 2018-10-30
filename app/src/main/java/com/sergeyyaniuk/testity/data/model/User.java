package com.sergeyyaniuk.testity.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "users", indices = {@Index(value = "id", unique = true)})
public class User {

    @PrimaryKey
    private Long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "login_with")
    private String loginWith;

    //private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getLoginWith() {
        return loginWith;
    }

    public void setLoginWith(String loginWith) {
        this.loginWith = loginWith;
    }

//    public User(Long id, String name, String email, String password){
//        this.id = id;
//        this.name = name;
//        this.email = email;
//        this.password = password;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        if (this == object) return true;
//        if (object == null || getClass() != object.getClass()) return false;
//
//        User that = (User) object;
//
//        if (email != null ? !email.equals(that.email) : that.email != null) return false;
//        return password != null ? password.equals(that.password) : that.password == null;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = email != null ? email.hashCode() : 0;
//        result = 31 * result + (password != null ? password.hashCode() : 0);
//        return result;
//    }
}
