package srez.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class ContextTools {
    private ConfigurableApplicationContext applicationContext;
    private ApplicationContext parentContext;
    private final Map<String, Object> beans = new HashMap<>();

    private ContextTools() {
    }

    public static ContextTools create() {
        return new ContextTools();
    }

    public ContextTools start() {
        if (parentContext != null) {
            applicationContext.setParent(parentContext);
        }
        applicationContext.addBeanFactoryPostProcessor(f -> beans.forEach(f::registerSingleton));
        applicationContext.registerShutdownHook();
        applicationContext.refresh();
        applicationContext.start();
        return this;
    }

    public ContextTools stop() {
        applicationContext.stop();
        return this;
    }

    public ContextTools setXmlPath(String xmlPath) {
        ClassPathXmlApplicationContext context;
        applicationContext = context = new ClassPathXmlApplicationContext();
        context.setConfigLocation(xmlPath);
        return this;
    }

    public ContextTools setConfigClass(Class<?> clazz) {
        AnnotationConfigApplicationContext context;
        applicationContext = context = new AnnotationConfigApplicationContext();
        context.register(clazz);
        return this;
    }

    public ContextTools setParent(ApplicationContext parentContext) {
        this.parentContext = parentContext;
        return this;
    }

    public ContextTools addBean(String beanName, Object bean) {
        beans.put(beanName, bean);
        return this;
    }
}
