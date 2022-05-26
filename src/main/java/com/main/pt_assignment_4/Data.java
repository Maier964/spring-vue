package com.main.pt_assignment_4;

import com.main.pt_assignment_4.model.Client;
import com.main.pt_assignment_4.model.MenuItem;
import com.main.pt_assignment_4.model.Order;
import com.main.pt_assignment_4.model.Product;

import java.util.ArrayList;
import java.util.Map;


public class Data {

    /* Warning: These variables will be deleted when the class is unloaded from the system, i.e. when there are no
    more references to this particular class. This shouldn't happen for an app of this kind, but it is worth noting
    that for production quality, this is not the recommended approach. I don't know what the recommended approach would
    be.
     */

    public static Map< String, Client> userMap;
    public static Map< String, MenuItem> productMap;
    public static Map< Integer, Order> orderMap;


}
