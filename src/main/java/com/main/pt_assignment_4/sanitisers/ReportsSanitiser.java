package com.main.pt_assignment_4.sanitisers;

public class ReportsSanitiser {

    private ReportsSanitiser(){};

    public static boolean validateReport1(int startHour, int endHour){
        return ( isValid(startHour) && isValid(endHour) );
    }

    private static boolean isValid(int hour){
        return ( (hour > 0) && (hour < 25) );
    }

    public static boolean validateReport2( int timesOrdered ){
        return (timesOrdered > 0);
    }

    public static boolean validateReport3( int timesOrdered, int price ){
        return ( (timesOrdered > 0) && (price > 0) );
    }

    public static boolean validateReport4( int day ){
        return ( ( day > 0 ) && ( day < 8 ) );
    }
}
