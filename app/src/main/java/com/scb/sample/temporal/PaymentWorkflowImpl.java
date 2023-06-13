package com.scb.sample.temporal;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class PaymentWorkflowImpl implements PaymentWorkflow {

    /* 
     * At least one of the following options needs to be defined:
     * - setStartToCloseTimeout
     * - setScheduleToCloseTimeout
     */
    ActivityOptions options = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(60))
            .build();

    /*
     * Define the SampleActivities stub. Activity stubs are proxies for activity invocations that
     * are executed outside of the workflow thread on the activity worker, that can be on a
     * different host. Temporal is going to dispatch the activity results back to the workflow and
     * unblock the stub as soon as activity is completed on the activity worker.
     * 
     * The activity options that were defined above are passed in as a parameter.
     */
    private final SampleActivities activity = Workflow.newActivityStub(SampleActivities.class, options);

    private String approvalAction;

    // This is the entry point to the Workflow.
    @Override
    public String doPayment(String accountId) {

        System.out.println("Wait for approval action");

        Workflow.await(() -> approvalAction != null);

        System.out.println("Approval action: " + approvalAction);

        if (!"approve".equals(approvalAction)) {
            return "rejected";
        }

        String result = activity.payment(accountId);
        activity.notification("someEmail");
        return result;
    }

    @Override
    public void approvalAction(String action) {
        approvalAction = action;
    }
}