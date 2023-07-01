package dev.pk7r.interactive.jda.utils;

import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;

@UtilityClass
public class AnnotationUtils {

    public <A extends Annotation> A get(Class<?> clazz, Class<A> annotation) {
        return clazz.getAnnotation(annotation);
    }

    public boolean hasAnnotation(Class<? extends Annotation> annotation, Class<?> clazz) {
        return clazz.isAnnotationPresent(annotation);
    }
}