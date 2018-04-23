package com.blackhammers.kalisz.planuz;

/**
 * Created by kalis on 2018-04-09.
 */

public class Faculties {
    String name, address;

    public Faculties() {
    }

    public Faculties(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }
}
