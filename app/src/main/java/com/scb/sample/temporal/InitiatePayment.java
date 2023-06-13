package com.scb.sample.temporal;

import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;

public class InitiatePayment {

    public static void main(String[] args) throws Exception {

        // This gRPC stubs wrapper talks to the local docker instance of the Temporal service.
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

        // WorkflowClient can be used to start, signal, query, cancel, and terminate Workflows.
        WorkflowClient client = WorkflowClient.newInstance(service);

        // Define our workflow unique id
        final String WORKFLOW_ID = "PaymentWorkflowID";

        /*
         * Set Workflow options such as WorkflowId and Task Queue so the worker knows where to list and which workflows to execute.
         */
        WorkflowOptions options = WorkflowOptions.newBuilder()
                    .setWorkflowId(WORKFLOW_ID)
                    .setTaskQueue(Shared.PAYMENT_TASK_QUEUE)
                    .build();

        // Create the workflow client stub. It is used to start our workflow execution.
        PaymentWorkflow workflow = client.newWorkflowStub(PaymentWorkflow.class, options);

        /*
         * Execute our workflow and wait for it to complete. The call to our getGreeting method is
         * synchronous.
         * 
         * Replace the parameter "World" in the call to getGreeting() with your name.
         */
        // String result = workflow.doPayment("123456");
        WorkflowClient.start(workflow::doPayment, "123456");

        // do user action
        workflow.approvalAction("approve");

        

        // String workflowId = WorkflowStub.fromTyped(workflow).getExecution().getWorkflowId();
        // // Display workflow execution results
        // System.out.println(workflowId + " " + result);
        // System.exit(0);
    }
}
