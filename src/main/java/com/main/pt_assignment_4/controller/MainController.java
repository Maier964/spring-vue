package com.main.pt_assignment_4.controller;

import com.main.pt_assignment_4.Data;
import com.main.pt_assignment_4.model.*;
import com.main.pt_assignment_4.sanitisers.ClientSanitiser;
import com.main.pt_assignment_4.service.AdminCRUD;
import com.main.pt_assignment_4.service.DeliveryServiceProcessing;
import com.main.pt_assignment_4.util.JSONCompositeWrapper;
import com.main.pt_assignment_4.util.JSONOrderWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


@CrossOrigin
@RestController
public class MainController{

    DeliveryServiceProcessing service = new DeliveryServiceProcessing();

    @PostMapping("/register")
    public ResponseEntity< String > handleRegister(@RequestBody Client client){

        /* Sanitise - do this in the service class, no sanitisation is required here */
        if (!ClientSanitiser.validate(client)){
            System.out.println("User " + client + " was not registered (Validation)");
            return new ResponseEntity<>("Validation failed", HttpStatus.BAD_REQUEST);
        }

        /* Add to "database" */
        if ( service.addClient( client ) != null ){
            System.out.println("Registered user: " + client);
            return HTTPSuccessWrapper();
        }

        System.out.println("User " + client + " was not registered (Not a validation error)");
        return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/find")
    public ResponseEntity<String> handleFindClient( @RequestParam String clientName ){

        if ( service.findClient( clientName )  != null ){
            return HTTPSuccessWrapper();
        }

        return new ResponseEntity<>("User not found", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> handleFindClient( @RequestBody Client client ){

        if ( service.login( client ) ){
            // Return email to show in dashboard panel
            return new ResponseEntity<>(client.getEmail(), HttpStatus.OK);
        }

        return new ResponseEntity<>("Login failed", HttpStatus.UNAUTHORIZED);
    }


    public ResponseEntity<String> HTTPSuccessWrapper(){
        return new ResponseEntity<>("Success!", HttpStatus.OK);
    }

    @GetMapping("/report1")
    public ResponseEntity<String> handleGenerateReport1( @RequestParam Integer startHour, @RequestParam Integer endHour ){
        if (service.generateReport1(startHour, endHour))
            return HTTPSuccessWrapper();
        else
            return new ResponseEntity<>("Report1 generation failed", HttpStatus.OK);
    }

    @GetMapping("/report2")
    public ResponseEntity<String> handleGenerateReport2( @RequestParam Integer timesOrdered){
        if (service.generateReport2(timesOrdered))
            return HTTPSuccessWrapper();
        else
            return new ResponseEntity<>("Report2 generation failed", HttpStatus.OK);
    }

    @GetMapping("/report3")
    public ResponseEntity<String> handleGenerateReport3( @RequestParam Integer timesOrdered, @RequestParam Integer price){
        if (service.generateReport3(timesOrdered, price))
            return HTTPSuccessWrapper();
        else
            return new ResponseEntity<>("Report3 generation failed", HttpStatus.OK);
    }

    @GetMapping("/report4")
    public ResponseEntity<String> handleGenerateReport4( @RequestParam Integer day){
        System.out.println("Debug haha");
        if (service.generateReport4(day))
            return HTTPSuccessWrapper();
        else
            return new ResponseEntity<>("Report4 generation failed", HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<ArrayList<MenuItem>> getAllItems(@RequestParam String request){

        if ( request.equals("True") ){
            ArrayList<MenuItem> items = new ArrayList<>();
            for(Map.Entry<String, MenuItem> iterator : Data.productMap.entrySet()  ){
                if ( iterator.getValue() instanceof Product ){
                    items.add( iterator.getValue() );
                }
            }
            return new ResponseEntity<>(items, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

    }

    @GetMapping("/compProducts")
    public ResponseEntity<ArrayList<JSONCompositeWrapper>> getAllCompItems(@RequestParam String request){
        ArrayList<JSONCompositeWrapper> dataToSend = new ArrayList<>();

        if ( request.equals("True") ){
            for(Map.Entry<String, MenuItem> iterator : Data.productMap.entrySet()  ){
                if ( iterator.getValue() instanceof CompositeProduct ){
                    JSONCompositeWrapper wrapper = new JSONCompositeWrapper();
                    wrapper.setTitle( iterator.getKey() );
                    int cnt = 0;
                    for( MenuItem items : ((CompositeProduct) iterator.getValue()).getMenuItemList() ){
                        switch (cnt) {
                            case 0 -> wrapper.setDish1(items.getName());
                            case 1 -> wrapper.setDish2(items.getName());
                            case 2 -> wrapper.setDish3(items.getName());
                            case 3 -> wrapper.setDish4(items.getName());
                            default -> { } // Impossible to reach - a maximum of 4 meals are in a composite product
                        }
                        cnt++;
                    }

                    dataToSend.add( wrapper );

                }
            }

            // Create jsonized object
            return new ResponseEntity<>(dataToSend, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @PostMapping("/simpleOrder")
    public ResponseEntity<String> createOrder(@RequestBody String data)
    {
        System.out.println("Debug");
        if (service.createOrder(parseName(data), parseProducts(data))) {
            return HTTPSuccessWrapper();
        } else {
            return new ResponseEntity<>("Failed... Sad", HttpStatus.EXPECTATION_FAILED);
        }
    }


    @PostMapping("/compositeOrder")
    public ResponseEntity<String> createCompOrder(@RequestBody String data)
    {
        System.out.println("Debug");

        if (service.createOrder(parseName(data), parseCompProducts(data))) {
            return HTTPSuccessWrapper();
        } else {
            return new ResponseEntity<>("Failed... Sad", HttpStatus.EXPECTATION_FAILED);
        }
    }



    @PostMapping("/addSimple")
    public ResponseEntity<String> addProduct(@RequestBody Product menuItem)
    {
        System.out.println("Got add request: " + menuItem);
        if (service.addMenuItem(menuItem)) {
            return HTTPSuccessWrapper();
        } else {
            return new ResponseEntity<>("Failed.", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/updateSimple")
    public ResponseEntity<String> updateProduct(@RequestBody Product menuItem)
    {
        // It will be ok to check if product exists.
        System.out.println("Got update request: " + menuItem);
        if (service.updateMenuItem(menuItem)) {
            return HTTPSuccessWrapper();
        } else {
            return new ResponseEntity<>("Failed.", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/delete")
    public ResponseEntity<String> deleteProductByName(@RequestParam String name)
    {
        System.out.println("Got delete request: " + name);
        if (service.removeMenuItem(name)) {
            return HTTPSuccessWrapper();
        } else {
            return new ResponseEntity<>("Failed.", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<ArrayList<JSONOrderWrapper>> getAllOrders(@RequestParam String request){

        if ( request.equals("True") ){
            ArrayList<JSONOrderWrapper> orders = new ArrayList<>();
            for(Map.Entry<Integer, Order> iterator : Data.orderMap.entrySet()  ){
                orders.add( new JSONOrderWrapper(
                        String.valueOf(iterator.getValue().getOrderID()), // id
                        iterator.getValue().getClient().getEmail(), // client
                        iterator.getValue().getItems().toString(), // products
                        iterator.getValue().getOrderDate().toString()
                ));
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

    }

    @PostMapping("/addComposite")
    public ResponseEntity<String> addCProduct(@RequestBody String data)
    {
        System.out.println(data);
        System.out.println("Got add composite request:");

        ArrayList<String> strings = new ArrayList<>();

        String[] parsedData = data.replace("\\", "").split("\"");

        for( int i = 4; i < parsedData.length; i+=2 ){
            if ( i+2 < parsedData.length )
                strings.add( parsedData[ i+2 ] );
        }

        strings.remove( strings.size() - 1 );

        if (service.addComposite(StringUtils.chop( data.split("\"")[4] ), strings)) {
            return HTTPSuccessWrapper();
        } else {
            return new ResponseEntity<>("Failed.", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/updateComposite")
    public ResponseEntity<String> updateCProduct(@RequestBody String data)
    {
//        System.out.println("Got add request: " + menuItem);
        ArrayList<String> strings = new ArrayList<>();

        String[] parsedData = data.replace("\\", "").split("\"");

        for( int i = 4; i < parsedData.length; i+=2 ){
            if ( i+2 < parsedData.length )
                strings.add( parsedData[ i+2 ] );
        }

        strings.remove( strings.size() - 1 );

        if (service.updateComposite(StringUtils.chop(data.split("\"")[4]), strings)) {
            return HTTPSuccessWrapper();
        } else {
            return new ResponseEntity<>("Failed.", HttpStatus.EXPECTATION_FAILED);
        }
    }


    public String parseName( String data ){
        String name = data.split("\"")[4];
        return name.substring(0, name.length()-1);
    }

    public ArrayList<MenuItem> parseProducts(String data ){

        ArrayList<MenuItem> products = new ArrayList<>();

        String[] newData = data.replace("\\", "").replace("\"","").split("name");

        System.out.println(Arrays.toString(newData));

        for( int i = 1; i < newData.length; i++ ){
            System.out.println("Product " + i);
            String title = newData[i].split(",")[0].substring(1);
            Double rating = Double.parseDouble( newData[i].split(",")[1].substring(7) );
            Double price = Double.parseDouble( newData[i].split(",")[2].substring(6) );
            Integer calories = Integer.parseInt( newData[i].split(",")[3].substring(9) );
            Integer protein = Integer.parseInt( newData[i].split(",")[4].substring(8) );
            Integer fat = Integer.parseInt( newData[i].split(",")[5].substring(4) );
            Integer sodium;
            if ( i != newData.length - 1 ){
                sodium = Integer.parseInt(StringUtils.chop(newData[i].split(",")[6].substring(7)) );
            }
            else{
                sodium = Integer.parseInt( StringUtils.chop(
                        StringUtils.chop(
                        StringUtils.chop(newData[i].split(",")[6].substring(7)))) );
            }

            products.add( new Product( title, rating, price, calories, protein, fat, sodium ) );
        }

        return products;
    }

    public ArrayList<MenuItem> parseCompProducts(String data ){
        ArrayList<MenuItem> compositeItems = new ArrayList<>();

        String[] newData = data.replace("\\", "").replace("\"","").split("title");

        // Parse info
        for( int i = 1; i < newData.length; i++ ){
            ArrayList<MenuItem> products = new ArrayList<>();
            String title = newData[i].split(",")[0].substring(1);
            String dish1 = newData[i].split(",")[1].substring(6);
            String dish2 = newData[i].split(",")[2].substring(6);
            String dish3 = newData[i].split(",")[3].substring(6);
            String dish4;
            if ( i == newData.length - 1 ){
                dish4 = StringUtils.chop( StringUtils.chop( StringUtils.chop( newData[i].split(",")[4].substring(6) ) ) );
            }
            else{
                dish4 = StringUtils.chop( newData[i].split(",")[4].substring(6) );
            }

            // Check if dishes actually exist
            if ( productExists(dish1) != null ){
                products.add( productExists(dish1) );
            }
            if ( !dish2.equals("null") && productExists(dish2) != null ){
                products.add( productExists(dish2) );
            }
            if ( !dish3.equals("null") && productExists(dish3) != null ){
                products.add( productExists(dish3) );
            }
            if ( !dish4.equals("null") && productExists(dish4) != null ){
                products.add( productExists(dish4) );
            }

            compositeItems.add( new CompositeProduct( title, products ) );
        }



        return compositeItems;
    }

    public MenuItem productExists(String product){
        assert product != null;
        for( Map.Entry<String, MenuItem> iterator : Data.productMap.entrySet() ){
            if ( iterator.getValue().getName().equals(product) ){
                return iterator.getValue();
            }
        }
        return null;
    }

}


