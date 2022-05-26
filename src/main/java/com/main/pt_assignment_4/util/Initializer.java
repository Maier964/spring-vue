package com.main.pt_assignment_4.util;

import com.main.pt_assignment_4.Data;
import com.main.pt_assignment_4.model.Client;
import com.main.pt_assignment_4.model.MenuItem;
import com.main.pt_assignment_4.model.Order;
import com.main.pt_assignment_4.service.AdminCRUD;

import java.util.*;

public class Initializer {

    static Boolean isInitialised = false;

    /* Singleton */
    private Initializer(){};

    public static void init(){

        if ( isInitialised ){
            return;
        }

        /* Initialize userMap */

        Data.userMap = new HashMap<>();

        initUsers();

        /* Initialize productMap */

        Data.productMap = new HashMap<>();

        initProducts();

        /* Initialize orderMap */

        Data.orderMap = new HashMap<>();

        initOrders();


        isInitialised = true;

    }

    private static void initUsers(){
        /* Fetch user objects from file */
        ArrayList<Client> clients = Serializer.deserializeAllClients();

        if ( clients == null )
            // Nothing to fetch
            return;

        /* Add users to the user hashmap */
        for( Client client : clients ){
            Data.userMap.put( client.getEmail(), client );
        }
    }

    private static void initProducts(){

        ArrayList<MenuItem> products = Serializer.deserializeAllProducts();

//        ArrayList<MenuItem> products = new ArrayList<>(AdminCRUD.importProducts().subList(0, 2000)) ;

        if (products == null)
                return;

        for( MenuItem product : products ){
            Data.productMap.put( product.getName(), product );
        }
    }

    private static void initOrders(){

        ArrayList<Order> orders = Serializer.deserializeAllOrders();

        if (orders == null)
            return;

        for ( Order order : orders ){
            Data.orderMap.put( order.getOrderID(), order );
        }

    }

}
