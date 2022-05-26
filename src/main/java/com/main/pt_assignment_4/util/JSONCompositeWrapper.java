package com.main.pt_assignment_4.util;

public class JSONCompositeWrapper {
    String title, dish1, dish2, dish3, dish4;

    public JSONCompositeWrapper(){};

    public JSONCompositeWrapper( String title, String dish1, String dish2, String dish3, String dish4 ){
        this.title = title;
        this.dish1 = dish1;
        this.dish2 = dish2;
        this.dish3 = dish3;
        this.dish4 = dish4;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDish1() {
        return dish1;
    }

    public void setDish1(String dish1) {
        this.dish1 = dish1;
    }

    public String getDish2() {
        return dish2;
    }

    public void setDish2(String dish2) {
        this.dish2 = dish2;
    }

    public String getDish3() {
        return dish3;
    }

    public void setDish3(String dish3) {
        this.dish3 = dish3;
    }

    public String getDish4() {
        return dish4;
    }

    public void setDish4(String dish4) {
        this.dish4 = dish4;
    }
}
