package com.ftn.osa.projekat_osa.utillity;

import com.ftn.osa.projekat_osa.exceptions.InvalidConditionException;
import com.ftn.osa.projekat_osa.exceptions.InvalidOperationException;
import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.model.Message;
import com.ftn.osa.projekat_osa.model.Rule;

public interface RuleHelper {
    static boolean executeRule(Rule rule, Message message, RuleActionInterface ruleActions) throws InvalidConditionException, InvalidOperationException {
        boolean passesCondition = false;
        switch (rule.getCondition()){
            case FROM:{
                if(message.getFrom().toLowerCase().contains(rule.getValue().toLowerCase())) passesCondition = true;
                break;
            }
            case TO:{
                if(message.getTo().toLowerCase().contains(rule.getValue().toLowerCase())) passesCondition = true;
                break;
            }
            case CC:{
                if(message.getCc().toLowerCase().contains(rule.getValue().toLowerCase())) passesCondition = true;
                break;
            }
            case SUBJECT:{
                if(message.getSubject().toLowerCase().contains(rule.getValue().toLowerCase())) passesCondition = true;
                break;
            }
            default: throw new InvalidConditionException("Provided rule condition is invalid");
        }
        if(passesCondition){
            switch (rule.getOperation()){
                case MOVE:{
                    if(rule.getDestinationFolder() == null) throw new NullPointerException("destinationFolder cannot be null for move or copy operation");
                    ruleActions.moveAction(message, rule.getDestinationFolder());
                    break;
                }
                case COPY:{
                    if(rule.getDestinationFolder() == null) throw new NullPointerException("destinationFolder cannot be null for move or copy operation");
                    ruleActions.copyAction(message, rule.getDestinationFolder());
                    break;
                }
                case DELETE:{
                    ruleActions.deleteAction(message);
                    break;
                }
                default: throw new InvalidOperationException("Provided rule operation is invalid");
            }
        }
        return passesCondition;
    }

    public interface RuleActionInterface{
        void moveAction(Message message, Folder destinationFolder);
        void copyAction(Message message, Folder destinationFolder);
        void deleteAction(Message message);
    }


}
