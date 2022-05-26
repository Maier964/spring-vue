package com.main.pt_assignment_4.service;

import com.main.pt_assignment_4.Data;
import com.main.pt_assignment_4.model.CompositeProduct;
import com.main.pt_assignment_4.model.MenuItem;
import com.main.pt_assignment_4.model.Product;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdminCRUD {

    /*  Works without the composite part */
    public static ArrayList<MenuItem> importProducts() {
        FileInputStream stream = null;
        ArrayList<MenuItem> products = new ArrayList<>();
        try {
            Stream<String> lines = Files.lines(Path.of("products.csv"));
            // Get only single products first ( and skip first line )
            lines.filter(line -> line.split(",").length == 7 && !(line.split(",")[0].equals("Title"))).
                    forEach(line -> products.add(new Product(
                            line.split(",")[0], // name
                            Double.parseDouble(line.split(",")[1]), // rating
                            Double.parseDouble(line.split(",")[2]), // price
                            Integer.parseInt(line.split(",")[3]), // calories
                            Integer.parseInt(line.split(",")[4]), // protein
                            Integer.parseInt(line.split(",")[5]), // fat
                            Integer.parseInt(line.split(",")[6]) // sodium
                    )));

            /* IDK :c */
//            ArrayList<MenuItem> arrayBuffer = new ArrayList<>();
//            lines.filter( line -> line.split(",").length > 7 ).
//                    forEach( line -> Arrays.stream(line.split("#\\{\\{(.*?)\\}\\}#")).
//                            forEach( product -> arrayBuffer.add( new Product(
//                                    product.split(",")[0], // name
//                                    Double.parseDouble(product.split(",")[1]), // rating
//                                    Double.parseDouble(product.split(",")[2]), // price
//                                    Integer.parseInt( product.split(",")[3] ), // calories
//                                    Integer.parseInt(product.split(",")[4]), // protein
//                                    Integer.parseInt(product.split(",")[5]), // fat
//                                    Integer.parseInt(product.split(",")[6]) // sodium
//                            ) ) ) )

            return products.stream().filter(distinctByKey(MenuItem::getName)).collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static Boolean addMenuItem(MenuItem product){
        Data.productMap.put( product.getName(), product );
        return true;
    }

    public static Boolean removeMenuItem( String productName ){
        Data.productMap.put( productName, null );
        return true;
    }

    public static Boolean updateMenuItem( MenuItem product ){
        Data.productMap.put(product.getName(), product);
        return true;
    }

    public static Boolean addComposite( String name, ArrayList<String> meals ){
        // Find products of each string meal
        ArrayList<MenuItem> itemMeals = new ArrayList<>();

        for( String stringMeal : meals ){
            stringMeal = stringMeal + " ";
            for(Map.Entry<String, MenuItem> it : Data.productMap.entrySet() ){
                if ( it.getKey().equals(stringMeal) ){
                    itemMeals.add( it.getValue() );
                }
            }
        }
        CompositeProduct compositeProduct = new CompositeProduct(name, itemMeals);

        Data.productMap.put( name, compositeProduct );
        return true;
    }

    // Update is not necessary, we can use the same add function and just overwrite the map value at key name..

}
