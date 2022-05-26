package com.main.pt_assignment_4.util;

import com.main.pt_assignment_4.Data;
import com.main.pt_assignment_4.model.MenuItem;
import com.main.pt_assignment_4.model.Order;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

public class OrderCreator {

    public static boolean createOrder(String nameOfClient, ArrayList<MenuItem> products){
        // TODO: Create order, generate random named file ( base64 of nameOfClient + nameOfFirstProduct ),
        // TODO: add it to order map.
        Order order = new Order((int) (Math.random() * (9999999)) + 1, Data.userMap.get(nameOfClient) , products);
        Data.orderMap.put( order.getOrderID(), order );
        String filename = nameOfClient + products.get(0).getName();
        Base64.Encoder encoder = Base64.getUrlEncoder();
        File myFile = new File( encoder.encodeToString(filename.getBytes() ) + ".order");
        try {
            if ( myFile.createNewFile() ){
                System.out.println("File created! " + myFile.getName());
            }
            else{
                System.out.println("File " + myFile.getName() +" already exists.");
                return false;
            }
            BufferedWriter writer = new BufferedWriter( new FileWriter(encoder.encodeToString(filename.getBytes() ) + ".order"));
            writer.write( order.toString() );
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed...");
            return false;
        }

        return true;
    }
}
