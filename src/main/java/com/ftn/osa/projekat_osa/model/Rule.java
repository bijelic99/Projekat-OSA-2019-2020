package com.ftn.osa.projekat_osa.model;

public class Rule extends Identifiable {
    private  Condition condition;
    private String value;
    private Operation operation;
    private Folder destinationFolder;

    public Rule(Long id, Condition condition, String value, Operation operation, Folder destinationFolder) {
        super(id);
        this.condition = condition;
        this.value = value;
        this.operation = operation;
        this.destinationFolder = destinationFolder;
    }

    public Rule() {
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Folder getDestinationFolder() {
        return destinationFolder;
    }

    public void setDestinationFolder(Folder destinationFolder) {
        this.destinationFolder = destinationFolder;
    }
}
