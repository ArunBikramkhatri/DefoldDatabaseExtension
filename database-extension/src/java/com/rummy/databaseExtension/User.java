package com.rummy.databaseExtension;

public class User {
    int _id;
    String _name;

    public User() {
    }

    public User(int id, String name) {
        this._id = id;
        this._name = name;
    }

    public int getID() {
        return this._id;
    }

    public void setID(int id) {
        this._id = id;
    }

    public String getName() {
        return this._name;
    }

    @Override
    public String toString() {
        return "StudentList{" +
                "_id=" + _id +
                ", _name='" + _name + '\'' +
                '}';
    }

    public void setName(String name) {
        this._name = name;
    }

}
