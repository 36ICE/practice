package com.example.statemachine;

import org.squirrelframework.foundation.exception.TransitionException;
import org.squirrelframework.foundation.fsm.*;
import org.squirrelframework.foundation.fsm.annotation.*;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;

public class QuickStartSample1 {

    enum MyState{
        A,B,C,D
    }
    enum MyEvent{
        ToA,ToB,ToC,ToD
    }
    class MyContext{
        public String name;
    }
    class MyEventConverter<MyEvent> implements Converter{

        @Override
        public String convertToString(Object obj) {
            return null;
        }

        @Override
        public Object convertFromString(String name) {
            return null;
        }
    }

    //转换器
    // ConverterProvider.INSTANCE.register(MyEvent.class, new MyEventConverter());
    // ConverterProvider.INSTANCE.register(MyState.class, new MyStateConverter());
    //注意：如果只使用fluent API定义状态机，则无需实现相应的转换器。而且，如果 Event 或 State 类
    // 是 String 或 Enumeration 类型，在大多数情况下，您不需要显式实现或注册转换器。
    class MyStateConverter<MyState> implements Converter{

        @Override
        public String convertToString(Object obj) {
            return null;
        }

        @Override
        public Object convertFromString(String name) {
            return null;
        }
    }

    @States({
            @State(name="A", entryCallMethod="entryStateA", exitCallMethod="exitStateA"),
            @State(name="B", entryCallMethod="entryStateB", exitCallMethod="exitStateB")
    })
    @Transitions({
            @Transit(from="A", to="B", on="GoToB", callMethod="stateAToStateBOnGotoB"),
            @Transit(from="A", to="A", on="WithinA", callMethod="stateAToStateAOnWithinA", type= TransitionType.INTERNAL)
    })
    interface MyStateMachine extends StateMachine<MyStateMachine, MyState, MyEvent, MyContext> {
        void entryStateA(MyState from, MyState to, MyEvent event, MyContext context);
        void stateAToStateBOnGotoB(MyState from, MyState to, MyEvent event, MyContext context);
        void stateAToStateAOnWithinA(MyState from, MyState to, MyEvent event, MyContext context);
        void exitStateA(MyState from, MyState to, MyEvent event, MyContext context);

    }

    //新状态机实例 MyStateMachine stateMachine = builder.newStateMachine(MyState.Initial);

    //触发转换 stateMachine.fire(MyEvent.Prepare, new MyContext("Testing"));

    //无类型状态机

    //上下文不敏感状态机 ，用户只需要在状态机实现类上添加注解@ContextInsensitive

    //转换异常处理 被包装到 TransitionException（未经检查的异常）中。目前，默认的异常处理策略简单粗暴，
    // 只是继续抛出异常，参见 AbstractStateMachine.afterTransitionCausedException 方法。
    abstract class MyABCDStateMachine extends AbstractStateMachine{
        @Override
        protected void afterTransitionCausedException(Object fromState, Object toState, Object event, Object context) {
            Throwable targeException = getLastException().getTargetException();
            // recover from IllegalArgumentException thrown out from state 'A' to 'B' caused by event 'ToB'
            if(targeException instanceof IllegalArgumentException &&
                    fromState.equals("A") && toState.equals("B") && event.equals("ToB")) {
                // do some error clean up job here
                // ...
                // after recovered from this exception, reset the state machine status back to normal
                setStatus(StateMachineStatus.IDLE);
            }
//            else if(...) {
//                // recover from other exception ...
//            }
            else {
                super.afterTransitionCausedException(fromState, toState, event, context);
            }
        }
    }


    //定义层次状态 层次状态可能包含嵌套状态。子状态本身可能有嵌套的子状态，并且嵌套可以进行到任何深度。
    // 当分层状态处于活动状态时，它的一个且只有一个子状态处于活动状态。层次状态可以通过 API 或注解来定义。
    //API  void defineSequentialStatesOn(S parentStateId, S... childStateIds);
    //注解 @States({
    //      @State(name="A", entryMethodCall="entryA", exitMethodCall="exitA"),
    //      @State(parent="A", name="BinA", entryMethodCall="entryBinA", exitMethodCall="exitBinA", initialState=true),
    //      @State(parent="A", name="CinA", entryMethodCall="entryCinA", exitMethodCall="exitCinA")
    //})



    //定义并行状态,并行状态封装了一组子状态，当父元素处于活动状态时这些状态同时处于活动状态。并行状态可以通过 API 或注解来定义。例如
    //// defines two region states "RegionState1" and "RegionState2" under parent parallel state "Root"
    //  builder.defineParallelStatesOn(MyState.Root, MyState.RegionState1, MyState.RegionState2);
    //
    //  builder.defineSequentialStatesOn(MyState.RegionState1, MyState.State11, MyState.State12);
    //  builder.externalTransition().from(MyState.State11).to(MyState.State12).on(MyEvent.Event1);
    //
    //  builder.defineSequentialStatesOn(MyState.RegionState2, MyState.State21, MyState.State22);
    //  builder.externalTransition().from(MyState.State21).to(MyState.State22).on(MyEvent.Event2);

    //@States({
    //      @State(name="Root", entryCallMethod="enterRoot", exitCallMethod="exitRoot", compositeType=StateCompositeType.PARALLEL),
    //      @State(parent="Root", name="RegionState1", entryCallMethod="enterRegionState1", exitCallMethod="exitRegionState1"),
    //      @State(parent="Root", name="RegionState2", entryCallMethod="enterRegionState2", exitCallMethod="exitRegionState2")
    //})

    //获取并行状态的当前子状态
    //stateMachine.getSubStatesOn(MyState.Root); // return list of current sub states of parallel state

    //定义上下文事件 ,启动/终止事件;Finish 事件
    // @ContextEvent(finishEvent="Finish")
    //  static class ParallelStateMachine extends AbstractStateMachine<...> {
    //  }

    //StateMachineBuilder<...> builder = StateMachineBuilderFactory.create(...);
    //  ...
    //  builder.defineFinishEvent(HEvent.Start);
    //  builder.defineTerminateEvent(HEvent.Terminate);
    //  builder.defineStartEvent(HEvent.Finish);

    //使用历史状态保存和恢复当前状态
    // // defined history type of state "A" as "deep"
    //  builder.defineSequentialStatesOn(MyState.A, HistoryType.DEEP, MyState.A1, MyState.A2)

    //@State(parent="A", name="A1", entryCallMethod="enterA1", exitCallMethod="exitA1", historyType= HistoryType.DEEP)


    //转换类型 根据 UML 规范，转换可能是以下三种类型之一：
    //内部转换 意味着如果触发转换，则在不退出或进入源状态的情况下发生转换（即，它不会导致状态更改）。这意味着不会调用源状态的进入或退出条件。即使 StateMachine 位于嵌套在关联状态中的一个或多个区域中，也可以进行内部转换。
    //本地转换
    //意味着转换，如果被触发，将不会退出复合（源）状态，但它将退出并重新进入当前状态配置中的复合状态内的任何状态。
    //外部转换
    //意味着转换，如果被触发，将退出复合（源）状态

    //builder.externalTransition().from(MyState.A).to(MyState.B).on(MyEvent.A2B);
    //  builder.internalTransition().within(MyState.A).on(MyEvent.innerA);
    //  builder.localTransition().from(MyState.A).to(MyState.CinA).on(MyEvent.intoC)

    //@Transitions({
    //      @Transition(from="A", to="B", on="A2B"), //default value of transition type is EXTERNAL
    //      @Transition(from="A", on="innerA", type=TransitionType.INTERNAL),
    //      @Transition(from="A", to="CinA", on="intoC", type=TransitionType.LOCAL),
    //  })


    //多态事件调度  在状态机的生命周期中，会触发各种事件，例如
    //State Machine Lifecycle Events
    //|--StateMachineEvent 						/* Base event of all state machine event */
    //     |--StartEvent							/* Fired when state machine started      */
    //     |--TerminateEvent						/* Fired when state machine terminated   */
    //     |--TransitionEvent						/* Base event of all transition event    */
    //     		|--TransitionBeginEvent				/* Fired when transition began           */
    //          |--TransitionCompleteEvent			/* Fired when transition completed       */
    //          |--TransitionExceptionEvent			/* Fired when transition threw exception */
    //          |--TransitionDeclinedEvent			/* Fired when transition declined        */
    //          |--TransitionEndEvent				/* Fired when transition end no matter declined or complete */
    //用户可以添加一个监听器来监听 StateMachineEvent，这意味着在状态机生命周期中触发的所有事件都将被这个监听器捕获，例如，
    //  stateMachine.addStateMachineListener(new StateMachineListener<...>() {
    //          @Override
    //          public void stateMachineEvent(StateMachineEvent<...> event) {
    //              // ...
    //          }
    //  });


    //声明式事件侦听器 ,为了简化状态机的使用，更重要的是提供非侵入式集成，squirrel-foundation 提供了一种通过以下注解添加事件监听器的声明方式，例如
    static class ExternalModule {
        @OnTransitionEnd
        @ListenerOrder(10) // Since 0.3.1 ListenerOrder can be used to insure listener invoked orderly
        public void transitionEnd() {
            // method annotated with TransitionEnd will be invoked when transition end...
            // the method must be public and return nothing
        }

        @OnTransitionBegin
        public void transitionBegin(MyEvent event) {
            // method annotated with TransitionBegin will be invoked when transition begin...
        }

        // 'event'(E), 'from'(S), 'to'(S), 'context'(C) and 'stateMachine'(T) can be used in MVEL scripts
        @OnTransitionBegin(when="event.name().equals(\"toB\")")
        public void transitionBeginConditional() {
            // method will be invoked when transition begin while transition caused by event "toB"
        }

        @OnTransitionComplete
        public void transitionComplete(String from, String to, MyEvent event, Integer context) {
            // method annotated with TransitionComplete will be invoked when transition complete...
        }

        @OnTransitionDecline
        public void transitionDeclined(String from, MyEvent event, Integer context) {
            // method annotated with TransitionDecline will be invoked when transition declined...
        }

        @OnBeforeActionExecuted
        public void onBeforeActionExecuted(Object sourceState, Object targetState,
                                           Object event, Object context, int[] mOfN, Action<?, ?, ?,?> action) {
            // method annotated with OnAfterActionExecuted will be invoked before action invoked
        }

        @OnAfterActionExecuted
        public void onAfterActionExecuted(Object sourceState, Object targetState,
                                          Object event, Object context, int[] mOfN, Action<?, ?, ?,?> action) {
            // method annotated with OnAfterActionExecuted will be invoked after action invoked
        }

        @OnActionExecException
        public void onActionExecException(Action<?, ?, ?,?> action, TransitionException e) {
            // method annotated with OnActionExecException will be invoked when action thrown exception
        }
    }
//    ExternalModule externalModule = new ExternalModule();
//  fsm.addDeclarativeListener(externalModule);
//  ...
//          fsm.removeDeclarativeListener(externalModule);



    //转换扩展方法 每个转换事件在 AbstractStateMachine 类上也有相应的扩展方法，允许在客户状态机实现类中扩展。
    // protected void afterTransitionCausedException(Exception e, S fromState, S toState, E event, C context) {
    //  }
    //
    //  protected void beforeTransitionBegin(S fromState, E event, C context) {
    //  }
    //
    //  protected void afterTransitionCompleted(S fromState, S toState, E event, C context) {
    //  }
    //
    //  protected void afterTransitionEnd(S fromState, S toState, E event, C context) {
    //  }
    //
    //  protected void afterTransitionDeclined(S fromState, E event, C context) {
    //  }
    //
    //  protected void beforeActionInvoked(S fromState, S toState, E event, C context) {
    //  }



    //加权动作 用户可以定义动作权重来调整动作执行顺序。.动作权重默认为 0。用户有两种方法来设置动作权重。
    //一种是将权重编号附加到方法名称并用“：”分隔。
    //// define state entry action 'goEntryD' weight -150
    //@State(name="D", entryCallMethod="goEntryD:-150")
    //// define transition action 'goAToC1' weight +150
    //@Transit(from="A", to="C", on="ToC", callMethod="goAToC1:+150")

    //另一种方法是覆盖 Action 类的权重方法，例如
    //
    //  Action<...> newAction = new Action<...>() {
    //      ...
    //      @Override
    //      public int weight() {
    //          return 100;
    //      }
    //}


    //异步执行 @AsyncExecute注解可以用在方法调用动作和声明式事件监听器上，表示该动作或事件监听器将异步执行，例如
    //@ContextInsensitive
    //  @StateMachineParameters(stateType=String.class, eventType=String.class, contextType=Void.class)
    //  public class ConcurrentSimpleStateMachine extends AbstractUntypedStateMachine {
    //      // No need to specify constructor anymore since 0.2.9
    //  	// protected ConcurrentSimpleStateMachine(ImmutableUntypedState initialState,
    //      //    Map<Object, ImmutableUntypedState> states) {
    //      //	super(initialState, states);
    //  	// }
    //
    //  	@AsyncExecute
    //  	protected void fromAToB(String from, String to, String event) {
    //      	// this action method will be invoked asynchronously
    //  	}
    //  }

    // public class DeclarativeListener {
    //      @OnTransitionBegin
    //      @AsyncExecute
    //      public void onTransitionBegin(...) {
    //          // transition begin event will be dispatched asynchronously to this listener method
    //      }
    //  }


    //状态机后处理器 ,用户可以为特定类型的状态机注册后处理器，以便在状态机实例化后添加后处理逻辑
    //// 1 User defined a state machine interface
    //  interface MyStateMachine extends StateMachine<MyStateMachine, MyState, MyEvent, MyContext> {
    //  . . .
    //  }
    //
    //  // 2 Both MyStateMachineImpl and MyStateMachineImplEx are implemented MyStateMachine
    //  class MyStateMachineImpl implements MyStateMachine {
    //      . . .
    //  }
    //  class MyStateMachineImplEx implements MyStateMachine {
    //      . . .
    //  }
    //
    //  // 3 User define a state machine post processor
    //  MyStateMachinePostProcessor implements SquirrelPostProcessor<MyStateMachine> {
    //      void postProcess(MyStateMachine component) {
    //          . . .
    //      }
    //  }
    //
    //  // 4 User register state machine post process
    //  SquirrelPostProcessorProvider.getInstance().register(MyStateMachine.class, MyStateMachinePostProcessor.class);


    //状态机导出
    //SCXMLVisitor可用于在SCXML文档中导出状态机定义。
    //  SCXMLVisitor visitor = SquirrelProvider.getInstance().newInstance(SCXMLVisitor.class);
    //  stateMachine.accept(visitor);
    //  visitor.convertSCXMLFile("MyStateMachine", true);

    //状态机导入
    //UntypedStateMachineImporter可用于导入由 SCXMLVisitor 导出的状态机类似 SCXML 的定义或手写定义。UntypedStateMachineImporter 将根据定义构建一个 UntypedStateMachineBuilder，以后可以使用它来创建状态机实例。
    //  UntypedStateMachineBuilder builder = new UntypedStateMachineImporter().importDefinition(scxmlDef);
    //  ATMStateMachine stateMachine = builder.newAnyStateMachine(ATMState.Idle);


    //保存/加载状态机数据
    //当状态机处于空闲状态时，用户可以保存状态机的数据。
    //StateMachineData.Reader<MyStateMachine, MyState, MyEvent, MyContext>
    //      savedData = stateMachine.dumpSavedData();
    //用户还可以将以上savedData加载到另一个状态机中，该状态机的状态已终止或刚刚初始化。
    //
    //newStateMachineInstance.loadSavedData(savedData);


    //状态机配置
    //在创建新的状态机实例时，用户可以通过 StateMachineConfiguration配置其行为，例如
    //  UntypedStateMachine fsm = builder.newUntypedStateMachine("a",
    //  	 StateMachineConfiguration.create().enableAutoStart(false)
    //       		.setIdProvider(IdProvider.UUIDProvider.getInstance()),
    //       new Object[0]); // since 0.3.0
    //  fsm.fire(TestEvent.toA);


    //状态机诊断
    //StateMachineLogger用于观察状态机的内部状态，如执行性能、动作调用顺序、转换进度等，例如
    //  StateMachine<?,?,?,?> stateMachine = builder.newStateMachine(HState.A);
    //  StateMachineLogger fsmLogger = new StateMachineLogger(stateMachine);
    //  fsmLogger.startLogging();
    //  ...
    //  stateMachine.fire(HEvent.B2A, 1);
    //  ...
    //  fsmLogger.terminateLogging();
    //  -------------------------------------------------------------------------------------------
    //  Console Log:
    //  HierachicalStateMachine: Transition from "B2a" on "B2A" with context "1" begin.
    //  Before execute method call action "leftB2a" (1 of 6).
    //  Before execute method call action "exitB2" (2 of 6).
    //  ...
    //  Before execute method call action "entryA1" (6 of 6).
    //  HierachicalStateMachine: Transition from "B2a" to "A1" on "B2A" complete which took 2ms.
    //  ...


    //定时状态定时状态是在进入状态后可以延迟或周期性触发指定事件的状态
    //。定时任务将提交给ScheduledExecutorService。用户可以通过SquirrelSingletonProvider注册您的 ScheduledExecutorService 实现实例，例如
    //  ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    //  SquirrelSingletonProvider.getInstance().register(ScheduledExecutorService.class, scheduler);



    //链接状态（所谓
    //的子机状态）链接状态指定子机状态机规范的插入。包含链接状态的状态机称为包含状态机。在单个包含状态机的上下文中，同一状态机可能不止一次地成为子机。
//链接状态在语义上等同于复合状态。子机状态机的区域是复合状态的区域。进入、退出和行为动作和内部转换被定义为状态的一部分。子机器状态是一种分解机制，允许分解常见行为及其重用。
//可以通过以下示例代码定义链接状态。
//
//  builderOfTestStateMachine.definedLinkedState(LState.A, builderOfLinkedStateMachine, LState.A1);



    //JMX 支持
    //从 0.3.3 开始，用户可以在运行时远程监控状态机实例（例如当前状态、名称）和修改配置（例如切换日志记录/切换性能监视器/远程触发事件）。所有状态机实例信息都将在“org.squirrelframework”域下。以下示例代码显示了如何启用 JMX 支持。
    //  UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(...);
    //  builder.setStateMachineConfiguration(StateMachineConfiguration.create().enableRemoteMonitor(true));









}
