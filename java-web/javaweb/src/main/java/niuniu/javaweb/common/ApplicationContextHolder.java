package niuniu.javaweb.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.stereotype.Component;

/**
 * @author NiuNiu666
 * @package niuniu.javaweb.common
 * @date 2023/2/10 17:52
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static Object getBean(String name) {
        return context != null ? context.getBean(name) : null;
    }

    public static <T> T getBean(Class<T> clz) {
        return context != null ? context.getBean(clz) : null;
    }

    public static <T> T getBean(String name, Class<T> clz) {
        return context != null ? context.getBean(name, clz) : null;
    }

    public static void addApplicationListenerBean(String listenerBeanName) {
        if (context != null) {
            ApplicationEventMulticaster applicationEventMulticaster = (ApplicationEventMulticaster)context.getBean(ApplicationEventMulticaster.class);
            applicationEventMulticaster.addApplicationListenerBean(listenerBeanName);
        }
    }

}
