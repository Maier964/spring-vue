package com.main.pt_assignment_4.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompositeProduct extends MenuItem{
    private List<MenuItem> menuItemList;



    public CompositeProduct(String name, List<MenuItem> menuItemList) {
        this.name = name;
        this.menuItemList = menuItemList;
    }

    public CompositeProduct(List<MenuItem> menuItemList) {
        this.menuItemList = menuItemList;
    }

    public CompositeProduct(String name){
        this.name = name;
        this.menuItemList = new ArrayList<MenuItem>();
    }


    @Override
    public Double computePrice() {
        if ( menuItemList.isEmpty() )
            return (double) 0;

        Double price = (double) 0;

        for( MenuItem items : menuItemList ){
            price += items.computePrice();
        }

        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeProduct that = (CompositeProduct) o;
        return name.equals(that.name) && menuItemList.equals(that.menuItemList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, menuItemList);
    }

    @Override
    public String toString() {
        return "CompositeProduct{" +
                "name='" + name + '\'' +
                ", menuItemList=" + menuItemList +
                '}';
    }

    public List<MenuItem> getMenuItemList() {
        return menuItemList;
    }

    public void setMenuItemList(List<MenuItem> menuItemList) {
        this.menuItemList = menuItemList;
    }
}

