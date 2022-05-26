package com.main.pt_assignment_4.model;

import java.io.Serializable;

public abstract class MenuItem implements Serializable {
    protected String name;
    public abstract Double computePrice();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
