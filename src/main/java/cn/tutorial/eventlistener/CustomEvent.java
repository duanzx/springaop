package cn.tutorial.eventlistener;

import org.springframework.context.ApplicationEvent;

public class CustomEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public CustomEvent(Object source) {
        super(source);
    }

    public void printMsg(String msg){
        System.out.println("监听到事件："+CustomEvent.class.getSimpleName()+",打印"+msg);
    }
}
