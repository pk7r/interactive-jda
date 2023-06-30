package dev.pk7r.interactive.jda.utils;

import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;
import java.util.Arrays;

@UtilityClass
public class AnnotationUtils {

    public <A extends Annotation> A get(Class<?> clazz, Class<A> annotation) {
        return clazz.getAnnotation(annotation);
    }

    public boolean hasAnnotation(Class<? extends Annotation> annotation, Class<?> clazz) {
        return Arrays.stream(clazz.getAnnotations()).map(Annotation::getClass).anyMatch(a -> a.equals(annotation));
    }
}