package com.yang.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotationDemo {
    public static void main(String[] args) throws Exception {
        parseTypeAnnotation();
        parseFieldAnnotation();
        parseMethodAnnotation();
    }

    public static void parseTypeAnnotation() throws Exception {
        System.out.println("===============Parsing an annotation on a specify class===============");
        Class clazz = Class.forName("com.yang.annotation.MyCourse");
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations){
            CourseInfoAnnotation annotation1 = (CourseInfoAnnotation)annotation;
            System.out.println("Course name is " + annotation1.name() + "\n" + "Course profile is " + annotation1.profile() + "\n");
        }
    }

    public static void parseFieldAnnotation() throws Exception {
        System.out.println("===============Parsing an annotation on a specify field===============");
        Class clazz = Class.forName("com.yang.annotation.MyCourse");
        Field person = clazz.getDeclaredField("person");
        PersonInfoAnnotation annotation = person.getAnnotation(PersonInfoAnnotation.class);
        System.out.println("name: " + annotation.name() + "\n"
                + "age: " + annotation.age() + "\n"
                + "language: ");
        for (String language : annotation.language()){
            System.out.println("  " + language);
        }
    }

    public static void parseMethodAnnotation() throws Exception {
        System.out.println("===============Parsing an annotation on a specify method==============");
        Class clazz = Class.forName("com.yang.annotation.MyCourse");
        Method method = clazz.getDeclaredMethod("getCourse");
        CourseInfoAnnotation annotation = method.getAnnotation(CourseInfoAnnotation.class);
        System.out.println("Course name is " + annotation.name() + "\n"
                + "Course profile is " + annotation.profile() + "\n"
                + "Course index is " + annotation.index());
    }
}
