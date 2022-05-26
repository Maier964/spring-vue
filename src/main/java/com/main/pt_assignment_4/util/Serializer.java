package com.main.pt_assignment_4.util;

import com.main.pt_assignment_4.Data;
import com.main.pt_assignment_4.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Serializer implements Serializable {

    public static Boolean serializeAll(){
        /* This method will attempt to serialize all the data containing the users, products and orders. */

        /* If things still don't work -> 1) change .csv to txt... 2) change client class until it works
        3) change the way you close file streams (at exception, they will not close)  */

        /* Serialize clients */
        try {
            FileOutputStream clientFile = new FileOutputStream("clients.csv");
            ObjectOutputStream objectClientStream = new ObjectOutputStream( clientFile );

            for(Map.Entry<String, Client> iterator: Data.userMap.entrySet()) {
                objectClientStream.writeObject( iterator.getValue() );
            }

            objectClientStream.close();
            clientFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


        /* Serialize orders */
        try {
            FileOutputStream orderFile = new FileOutputStream("orders.csv");
            ObjectOutputStream objectOrderStream = new ObjectOutputStream( orderFile );

            for(Map.Entry<Integer, Order> iterator: Data.orderMap.entrySet()) {
                objectOrderStream.writeObject( iterator.getValue() );
            }

            objectOrderStream.close();
            orderFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        /* Serialize products */
        try {
            FileOutputStream productFile = new FileOutputStream("serializedProducts.csv");
            ObjectOutputStream objectProductStream = new ObjectOutputStream( productFile );

            for(Map.Entry<String, MenuItem> iterator: Data.productMap.entrySet()) {
                objectProductStream.writeObject( iterator.getValue() );
            }

            objectProductStream.close();
            productFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static ArrayList<Client> deserializeAllClients(){

        ArrayList<Client> clients = new ArrayList<>();

        /* Deserialize clients */

        try{
            FileInputStream clientFile = new FileInputStream("clients.csv");
            ObjectInputStream clientObject = new ObjectInputStream(clientFile);

            Client client = null;

            while( true){
                try{
                    /* Get object from file */
                    client = (Client) clientObject.readObject();

                    /* Add it to arraylist */
                    clients.add( client );
                }
                catch (EOFException e){
                    break;
                }

            }

            clientObject.close();
            clientFile.close();

        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }

        return clients;
    }


    public static ArrayList<Order> deserializeAllOrders(){

        ArrayList<Order> orders = new ArrayList<>();

        /* Deserialize orders */

        try{
            FileInputStream orderFile = new FileInputStream("orders.csv");
            ObjectInputStream orderObject = new ObjectInputStream(orderFile);

            Order order = null;

            while( true ) {
                try{
                    /* Get object from file */
                    order = (Order) orderObject.readObject();

                    /* Add it to arraylist */
                    orders.add( order );
                }
                catch (EOFException e){
                    break;
                }

            }

            orderObject.close();
            orderFile.close();

        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }

        return orders;
    }


    public static ArrayList<MenuItem> deserializeAllProducts(){

        ArrayList<MenuItem> products = new ArrayList<>();

        /* Deserialize products */

        try{
            FileInputStream productFile = new FileInputStream("serializedProducts.csv");
            ObjectInputStream productObject = new ObjectInputStream(productFile);

            MenuItem product = null;

            while( true ) {
                try{
                    /* Get object from file */
                    product = (MenuItem) productObject.readObject();

                    /* Add it to arraylist */
                    products.add( product );
                }
                catch (EOFException e){
                    break;
                }

            }

            productObject.close();
            productFile.close();

        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }

        return products;
    }
}

