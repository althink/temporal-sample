package com.scb.sample.temporal;

import java.util.List;

import io.temporal.workflow.Workflow;

public class HumanTaskWorkflowImpl implements HumanTaskWorkflow {

    private String assignmentGroup;
    private String assignee;
    private String approvalAction;

    @Override
    public String askForApproval(String assignmentGroup) {
        this.assignmentGroup = assignmentGroup;
        
        System.out.println("Wait for approval action");

        Workflow.await(() -> assignee != null);

        System.out.println("Human task assigned");

        Workflow.await(() -> approvalAction != null);

        System.out.println("Approval action: " + approvalAction);

        return approvalAction;
    }

    @Override
    public void assign(String userId) {
        System.out.println("Assign human task to: " + userId);

        assignee = userId;
    }

    @Override
    public void humanAction(String action) {
        System.out.println("Human action signal called: " + action);

        approvalAction = action;
    }
    
}
