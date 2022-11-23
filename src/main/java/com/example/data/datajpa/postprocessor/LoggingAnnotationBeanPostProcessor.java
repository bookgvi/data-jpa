package com.example.data.datajpa.postprocessor;

import com.example.data.datajpa.annotation.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class LoggingAnnotationBeanPostProcessor implements BeanPostProcessor {
    private final Logger logger = LoggerFactory.getLogger(LoggingAnnotationBeanPostProcessor.class);
    private final Map<String, Class<?>> annotatedBeans = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        boolean isAnnotationPresent = Arrays.stream(bean.getClass().getMethods()).anyMatch(method -> method.isAnnotationPresent(Logging.class));
        if (isAnnotationPresent) {
            annotatedBeans.put(beanName, bean.getClass());
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class annotatedClass = annotatedBeans.get(beanName);
        if (annotatedClass != null) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(annotatedClass);
            enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
                Optional<Method> methodOptional = Arrays.stream(annotatedClass.getMethods()).filter(method::equals).findFirst();

                if (methodOptional.isPresent()) {
                    Logging loggingAnnotation = methodOptional.get().getAnnotation(Logging.class);
                    if (loggingAnnotation != null) {
                        if (loggingAnnotation.isRequest()) {
                            logger.error("{}#{}: {}", annotatedClass.getSimpleName(), method.getName(), Arrays.asList(objects));
                        }
                        Object invoke = methodProxy.invoke(bean, objects);
                        if (loggingAnnotation.isResponse()) {
                            logger.error("{}#{}: {}", annotatedClass.getSimpleName(), method.getName(), invoke != null ? String.valueOf(invoke) : "void");
                        }
                        return invoke;
                    }
                }
                return method.invoke(bean, objects);
            });
            return enhancer.create();
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
