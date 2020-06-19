package com.ftn.osa.projekat_osa.android_dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.ftn.osa.projekat_osa.model.Condition;
import com.ftn.osa.projekat_osa.model.Operation;
import com.ftn.osa.projekat_osa.model.Rule;

public class RuleDTO extends DtoObject<Rule> {
    private Long id;
    private String condition;
    private String operation;
    private FolderMetadataDTO destination;
    private String value;

    public RuleDTO() {

    }

    public RuleDTO(Long id, String condition, String operation, FolderMetadataDTO destination, String value) {
        this.id = id;
        this.condition = condition;
        this.operation = operation;
        this.destination = destination;
        this.value = value;
    }

    public RuleDTO(Rule entity) {
        this.id = entity.getId();
        this.condition = entity.getCondition().name();
        this.operation = entity.getOperation().name();
        this.destination = entity.getDestinationFolder() != null ? new FolderMetadataDTO(entity.getDestinationFolder()) : null;
        this.value = entity.getValue();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public FolderMetadataDTO getDestination() {
        return destination;
    }

    public void setDestination(FolderMetadataDTO destination) {
        this.destination = destination;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Rule getJpaEntity() {
        return new Rule(this.getId(), Condition.valueOf(this.getCondition()), this.getValue(), Operation.valueOf(this.getOperation()), this.getDestination().getJpaEntity());
    }
}
