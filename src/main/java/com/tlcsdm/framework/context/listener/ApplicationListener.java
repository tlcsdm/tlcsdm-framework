package com.tlcsdm.framework.context.listener;

import com.tlcsdm.framework.context.listener.impl.DefaultEventMulticaster;
import com.tlcsdm.framework.core.util.ReflectUtils;

import java.lang.reflect.Type;
import java.util.EventListener;

@FunctionalInterface
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
    void onApplicationEvent(E event);

    default String[] getMulticasterNames() {
        return new String[]{DefaultEventMulticaster.DEFAULT_EVENT_MULTICASTER_NAME};
    }

    default Class supportsEventType() {
        try {
            for (Type genericInterface : this.getClass().getGenericInterfaces()) {
                if (genericInterface.getTypeName().startsWith(ApplicationListener.class.getName())) {
                    return ReflectUtils.getActualTypeArgument(genericInterface);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ApplicationEvent.class;
        }
        return null;
    }
}
