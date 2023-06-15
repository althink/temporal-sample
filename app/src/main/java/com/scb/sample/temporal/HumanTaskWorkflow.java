package com.scb.sample.temporal;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface HumanTaskWorkflow {

    @WorkflowMethod
    String askForApproval(String assignmentGroup);

    @SignalMethod
    void assign(String userId);

    @SignalMethod
    void humanAction(String action);
}
