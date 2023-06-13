package com.scb.sample.temporal;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface SampleActivities {

    String payment(String accountId);

    void notification(String email);
    
}