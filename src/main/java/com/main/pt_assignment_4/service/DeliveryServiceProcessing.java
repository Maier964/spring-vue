package com.main.pt_assignment_4.service;

import com.main.pt_assignment_4.Data;
import com.main.pt_assignment_4.model.Client;
import com.main.pt_assignment_4.model.MenuItem;
import com.main.pt_assignment_4.model.Order;
import com.main.pt_assignment_4.util.OrderCreator;

import java.util.*;

/*
@invariant wellFormed
 */
public class DeliveryServiceProcessing {

    /* Admin operations */

    public DeliveryServiceProcessing(){};

    public ArrayList<MenuItem> importProducts(){
        return AdminCRUD.importProducts();
    }

    public Boolean addMenuItem(MenuItem product){
        assert product != null: "The product is null!";
        return AdminCRUD.addMenuItem(product);
    }

    public Boolean updateMenuItem( MenuItem product ){
        assert  product != null;
        assert productExists( product.getName() );
        return AdminCRUD.updateMenuItem(product);
    }

    public Boolean removeMenuItem( String product ){
        assert product != null;
        assert productExists( product );
        return AdminCRUD.removeMenuItem(product);
    }

    public Boolean addComposite( String name, ArrayList<String> strings ){
        assert name != null;
        assert strings != null;
        return AdminCRUD.addComposite(name, strings);
    }

    public Boolean updateComposite( String name, ArrayList<String> strings ){
        assert name != null;
        assert strings != null;
        assert productExists( name );
        return AdminCRUD.addComposite(name, strings);
    }

    public Boolean login( Client client ){
        System.out.println( client );
        return ( Data.userMap.get( client.getEmail() ).getPassword().equals( client.getPassword() ) );
    }

    public Boolean generateReport1( int startHour, int endHour ){

        StringBuilder stringBuilder = new StringBuilder("----------REPORT----------\n Orders between hour " + startHour
        + " and "+ endHour + ":\n");

        Data.orderMap.values().stream()
                .filter(order -> order.getOrderDate().getHour() >= startHour &&
                        order.getOrderDate().getHour() <= endHour)
                    .forEach(order -> stringBuilder.append(order).append("\n") );

        String finalReport = stringBuilder.toString();

        System.out.println( finalReport );

        /* Maybe send it to vue, if you have time */

        return true;
    }

    public Boolean generateReport2( int timesOrdered ){

        /* Additional data structure needed */
        Map<MenuItem, Integer > frequencyMap = new HashMap<>();

        StringBuilder stringBuilder = new StringBuilder("----------REPORT----------\n Products ordered more than " + timesOrdered
                + " times:\n");

        // Get all bought products in this map with 0 count value
        Data.orderMap.entrySet().stream()
                .map( iterator -> iterator.getValue().getItems() )
                    .forEach( array -> array.stream()
                        .forEach( arrayElement -> frequencyMap.put( arrayElement, 0 ) ) );

        // Calculate times ordered for each product
        Data.orderMap.entrySet().stream()
                .map( iterator -> iterator.getValue().getItems() )
                    .forEach( array -> array
                        .forEach( arrayElement -> frequencyMap.put( arrayElement,  frequencyMap.get( arrayElement ) + 1  ) ) );

        // Lastly, iterate through the newly built map and get report objects

        frequencyMap.entrySet().stream()
                .filter( entry -> entry.getValue() > timesOrdered )
                    .forEach( secondaryEntry -> stringBuilder.append( secondaryEntry.getKey() ).append("\n") );

        String finalReport = stringBuilder.toString();

        System.out.println( finalReport );

        /* Maybe send it to vue, if you have time */

        return true;
    }

    public boolean generateReport3( int timesOrdered, int price ){
        /* Additional data structures needed */
        Map<Client, Integer > frequencyMap = new HashMap<>();
        Map<Client, Double> priceMap = new HashMap<>();

        StringBuilder stringBuilder = new StringBuilder("----------REPORT----------\n Clients that ordered more than " + timesOrdered
                + " times and the total value of the orders is bigger than " + price + ":\n");

        // Get all clients in this map with 0 count value
        Data.orderMap.entrySet().stream()
                .map( iterator -> iterator.getValue().getClient() )
                        .forEach( client -> frequencyMap.put( client, 0 ) );

        // Calculate times ordered for each client
        Data.orderMap.entrySet().stream()
                .map( iterator -> iterator.getValue().getClient())
                        .forEach( client -> frequencyMap.put( client, frequencyMap.get( client ) + 1 ) );


        // Check if times ordered is big enough, in order to start step 2 of validation
        frequencyMap.entrySet().stream()
                .filter( entry -> entry.getValue() > timesOrdered )
                    .forEach( secondaryEntry -> priceMap.put( secondaryEntry.getKey(), (double) 0) );

        // Compute price for each of the entries in priceMap

        Data.orderMap.entrySet().stream()
                .filter( mapIterator -> priceMap.containsKey( mapIterator.getValue().getClient() ) )
                .forEach( secondIterator -> priceMap.put(
                        secondIterator.getValue().getClient(), // Client
                        priceMap.get( secondIterator.getValue().getClient() ) +
                                secondIterator.getValue().getTotalPrice())
                );

        // Finally, check if price and order count respect the conditions

        Data.orderMap.entrySet().stream()
                .filter( mapIterator -> priceMap.containsKey( mapIterator.getValue().getClient() ) )
                    .filter( secondMapIterator -> priceMap.get( secondMapIterator.getValue().getClient() ) > price )
                        .forEach( finalIterator -> stringBuilder.append( finalIterator.getValue().getClient() ).append("\n") );

        String finalReport = stringBuilder.toString();

        System.out.println( finalReport );

        return true;
    }


    public boolean generateReport4( int day ){

        /* Sanitise input - considering we are talking about the day of the week */
        if ( day < 1 || day > 7 ){
            return false;
        }

        /* Additional data structures needed */
        Map<MenuItem, Integer > frequencyMap = new HashMap<>();

        StringBuilder stringBuilder = new StringBuilder("----------REPORT----------\n Products ordered in day "+ day
        + " :\n");

        /* Initialise frequency map */

        Data.orderMap.entrySet().stream()
                .filter( orderEntry -> orderEntry.getValue().getOrderDate().getDayOfWeek().getValue() == day  )
                    .map( correctEntry -> correctEntry.getValue().getItems() )
                        .forEach( array -> array.forEach(item -> frequencyMap.put( item, 0 ) ) );

        /* Populate frequency map */

        Data.orderMap.entrySet().stream()
                .filter( orderEntry -> orderEntry.getValue().getOrderDate().getDayOfWeek().getValue() == day  )
                .map( correctEntry -> correctEntry.getValue().getItems() )
                .forEach( array -> array.forEach(item -> frequencyMap.put( item, frequencyMap.get( item ) + 1 ) ) );

        /* Finalize report */

        frequencyMap.entrySet().forEach(entry -> stringBuilder.append( entry.getKey() )
                .append(" with ").append( entry.getValue() ).append(" orders.").append("\n") );

        String finalReport = stringBuilder.toString();

        System.out.println( finalReport );

        return true;
    }

    /* Client operations */

    public MenuItem searchForProductByName( String name ){
        /* Using stream processing */
        Optional<Map.Entry<String, MenuItem>> optionalHashEntry =
                Data.productMap.entrySet().stream().filter(productIterator -> productIterator.getKey().equals( name ) )
                        .findFirst();

        return optionalHashEntry.map(Map.Entry::getValue).orElse(null);

    }

    public Client addClient( Client client ){
        assert !emailExists(client.getEmail()):"Client already exists!";

        Data.userMap.put(client.getEmail(), client);

//        assert Data.userMap.get( client.getEmail() ) == null:"Client was not registered!";
        return Data.userMap.get( client.getEmail() );
    }

    public Boolean emailExists(String email){
        assert email != null;
        for( Map.Entry<String, Client> iterator : Data.userMap.entrySet() ){
            if ( iterator.getValue().getEmail().equals(email) ){
                return true;
            }
        }
        return false;
    }


    public Boolean productExists(String product){
        assert product != null;
        for( Map.Entry<String, MenuItem> iterator : Data.productMap.entrySet() ){
            if ( iterator.getValue().getName().equals(product) ){
                return true;
            }
        }
        return false;
    }


    public Client findClient( String name ){
        return Data.userMap.get( name );
    }

    public Boolean createOrder( String clientName, ArrayList<MenuItem> products ){
        assert products != null;
        assert emailExists(clientName);

        return OrderCreator.createOrder( clientName, products );
    }

    public Boolean wellFormed(){
        for( Map.Entry<String, MenuItem> iterator : Data.productMap.entrySet() ){
            if ( iterator.getValue().getName() == null )
                return false;
        }

        for( Map.Entry<String, Client> iterator : Data.userMap.entrySet() ){
            if ( iterator.getValue().getEmail() == null ||
                 iterator.getValue().getPassword() == null ){
                return false;
            }
        }

        for( Map.Entry<Integer, Order> iterator : Data.orderMap.entrySet() ){
            if (    iterator.getValue().getItems() == null ||
                    iterator.getValue().getOrderDate() == null ||
                    iterator.getValue().getClient() == null){
                return false;
            }
        }

        return true;
    }

}
