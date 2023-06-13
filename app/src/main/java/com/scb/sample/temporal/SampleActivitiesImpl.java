package com.scb.sample.temporal;

public class SampleActivitiesImpl implements SampleActivities {

    @Override
    public String payment(String accountId) {
        System.out.println("Payment Activity start");
        System.out.println("Payment Activity end");
        return "ok";
    }

    @Override
    public void notification(String email) {
        System.out.println("Notification Activity start");
        System.out.println("Notification Activity end");
    }
    
}
