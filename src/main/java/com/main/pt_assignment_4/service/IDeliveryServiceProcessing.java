package com.main.pt_assignment_4.service;

import com.main.pt_assignment_4.model.MenuItem;
import com.main.pt_assignment_4.model.Order;
import com.main.pt_assignment_4.model.Product;

import java.util.ArrayList;
import java.util.Map;

public interface IDeliveryServiceProcessing {
    public String importProducts();

    public String addMenuItem(Product product);

    public Boolean generateReports();

    public Boolean computeOrder(ArrayList<MenuItem> choosenItems);



}
