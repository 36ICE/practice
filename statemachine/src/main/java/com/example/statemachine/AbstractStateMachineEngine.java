package com.example.statemachine;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.GenericTypeResolver;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;

public abstract class AbstractStateMachineEngine<T extends UntypedStateMachine> implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private UntypedStateMachineBuilder stateMachineBuilder;

    public AbstractStateMachineEngine(){
        Class<T> genericType=(Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(),AbstractStateMachineEngine.class);

        stateMachineBuilder = StateMachineBuilderFactory.create(genericType,ApplicationContext.class);
    }


    public void setApplicationContext(ApplicationContext applicationContext)throws BeansException{
        this.applicationContext=applicationContext;
    }


    //触发
    public void fire(){

        //StateMachine 不是由spring创建的，无法通过注解的方式来开启事务，要手动开启注解。
//        DataSourceTransactionManager transactionManager =applicationContext.getBean("transactionManager");
        DefaultTransactionDefinition def =new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

//        TransactionStatus status= transactionManager.getTransaction(def);

//        try {
//            stateMachine.fire(event,context);
//
//            transactionManager.commit();
//            return ;
//        }catch (Exception e){
//            transactionManager.rollback();
//        }
    }


    //还需要一个类集成AbstractStateMachineEngine，用于调用fire()方法。该类需要添加@Service注解，以便spring注入


}
