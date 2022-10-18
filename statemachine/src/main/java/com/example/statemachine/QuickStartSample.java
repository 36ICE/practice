package com.example.statemachine;

import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;
import org.squirrelframework.foundation.fsm.annotation.StateMachineParameters;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

public class QuickStartSample {

    enum FSMEvent{
        ToA,ToB,ToC,ToD
    }
    @StateMachineParameters(stateType = String.class,eventType = FSMEvent.class,contextType = Integer.class)
    static class StateMachineSample extends AbstractUntypedStateMachine{
        protected void fromAToB(String from, String to, FSMEvent event, Integer context){
            System.out.println("Transition from '"+ from +"'to '"+ to +"' on event '"+event+"' with context '"+ context +"'.");

        }

        protected void ontoB(String from ,String to, FSMEvent event,Integer context){
            System.out.println("Entry State '" +to +"'.");
        }
    }

    public static void main(String[] args) {
        //build state transitions
        UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(StateMachineSample.class);
        builder.externalTransition().from("A").to("B").on(FSMEvent.ToB).callMethod("fromAToB");
        builder.onEntry("B").callMethod("ontoB");

        //use satte machine
        UntypedStateMachine fsm =builder.newStateMachine("A");
        fsm.fire(FSMEvent.ToB,10);

        System.out.println("Current State is "+ fsm.getCurrentState());




    }

}
