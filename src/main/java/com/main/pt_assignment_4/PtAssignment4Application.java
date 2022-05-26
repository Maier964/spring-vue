package com.main.pt_assignment_4;

import com.main.pt_assignment_4.model.Client;
import com.main.pt_assignment_4.util.Initializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class PtAssignment4Application {

    public static void main(String[] args) {
        /* Demo */

        /* Init */

        Initializer.init();

//        for(Map.Entry<String, Client> iterator: Data.userMap.entrySet()) {
//            System.out.println( iterator.getValue() );
//        }

        SpringApplication.run(PtAssignment4Application.class, args);
    }

    // TODO: Use sanitisers in service -> get design by contract done
    // TODO: Test admin reports
    // TODO: Check composite MenuItem
    // DONE: Figure out way to serialize everything when application closes ( maybe use a button in front-end )
    // TODO: Finish Front-end
    // TODO: Manage good error response between server and vue (Optional if u have time)


}
