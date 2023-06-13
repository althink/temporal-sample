package com.scb.sample.temporal;

import io.temporal.client.WorkflowOptions;
import io.temporal.testing.TestWorkflowRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PaymentWorkflowTest {

    @Rule
    public TestWorkflowRule testWorkflowRule =
            TestWorkflowRule.newBuilder()
                    .setWorkflowTypes(PaymentWorkflowImpl.class)
                    .setDoNotStart(true)
                    .build();

    @Test
    public void testIntegrationGetGreeting() {
        testWorkflowRule.getWorker().registerActivitiesImplementations(new SampleActivitiesImpl());
        testWorkflowRule.getTestEnvironment().start();

        PaymentWorkflow workflow =
                testWorkflowRule
                        .getWorkflowClient()
                        .newWorkflowStub(
                            PaymentWorkflow.class,
                                WorkflowOptions.newBuilder().setTaskQueue(testWorkflowRule.getTaskQueue()).build());
        String result = workflow.doPayment("12345678");
        assertEquals("ok", result);
        testWorkflowRule.getTestEnvironment().shutdown();
    }

    @Test
    public void testMockedGetGreeting() {
        SampleActivities formatActivities = mock(SampleActivities.class, withSettings().withoutAnnotations());
        when(formatActivities.payment(anyString())).thenReturn("ok");
        testWorkflowRule.getWorker().registerActivitiesImplementations(formatActivities);
        testWorkflowRule.getTestEnvironment().start();

        PaymentWorkflow workflow =
                testWorkflowRule
                        .getWorkflowClient()
                        .newWorkflowStub(
                            PaymentWorkflow.class,
                                WorkflowOptions.newBuilder().setTaskQueue(testWorkflowRule.getTaskQueue()).build());
        String result = workflow.doPayment("12345678");
        assertEquals("ok", result);
        testWorkflowRule.getTestEnvironment().shutdown();
    }
}