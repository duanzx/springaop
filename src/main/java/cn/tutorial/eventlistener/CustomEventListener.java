package cn.tutorial.eventlistener;

import org.springframework.context.ApplicationListener;

public class CustomEventListener implements ApplicationListener<CustomEvent> {
    @Override
    public void onApplicationEvent(CustomEvent event) {
        event.printMsg("just for test msg");
    }
}
