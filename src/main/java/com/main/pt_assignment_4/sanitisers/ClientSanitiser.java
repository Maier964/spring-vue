package com.main.pt_assignment_4.sanitisers;

import com.main.pt_assignment_4.model.Client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientSanitiser {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PASSWORD_MINIMUM_RESTRICTION_REGEX =
            Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$"); // 8 len - lowercase/uppercase/number

    private ClientSanitiser(){};

    public static boolean validate( Client client ){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher( client.getEmail() );
        if (!matcher.find())
            return false;

        matcher = VALID_PASSWORD_MINIMUM_RESTRICTION_REGEX.matcher( client.getPassword() );
        return matcher.find();
    }
}
