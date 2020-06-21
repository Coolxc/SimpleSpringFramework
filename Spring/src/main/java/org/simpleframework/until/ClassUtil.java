package org.simpleframework.until;


import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ClassUtil {

    public static final String PROTOCOL_FILE = "file";

    /**
     * Get the collection of all the class under the specify package
     *
     * @param packageName
     * @return
     */
    public static Set<Class<?>> extractPackageClass(String packageName) {
        //1.get the class loader
        ClassLoader classLoader = getClassLoader();
        //2.get the loader resource throw class loader
        URL url = classLoader.getResource(packageName.replace(".", "/"));
        if (url == null) {
            log.warn("The URL is not exist");
            return null;
        }
        //3.Resources are acquired in different ways according to different resource
        Set<Class<?>> classSet = null;
        if (url.getProtocol().equalsIgnoreCase(PROTOCOL_FILE)) {
            classSet = new HashSet<>();
            File packageDirectory = new File(url.getPath());
            extractClassFile(classSet, packageDirectory, packageName);
        }
        return classSet;
    }

    /**
     * @param emptyClassSet :Use a set to collect all class instances
     * @param fileSource
     * @param packageName
     */
    public static void extractClassFile(Set<Class<?>> emptyClassSet, File fileSource, String packageName) {
        if (!fileSource.isDirectory()) {
            return;
        }
        //use a array to collect all directory and add class instance to set
        File[] files = fileSource.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                } else {
                    String absolutePath = file.getAbsolutePath();
                    if (absolutePath.endsWith(".class")) {
                        addToEmptySet(absolutePath);
                    }
                }
                return false;
            }

            //adds a class instance to the set
            private void addToEmptySet(String absolutePath) {
                absolutePath = absolutePath.replace(File.separatorChar, '.');
                String path = absolutePath.substring(absolutePath.indexOf(packageName));
                path = path.substring(0, path.indexOf(".class"));

                Class clazz = null;
                try {
                    clazz = Class.forName(path);
                } catch (ClassNotFoundException e) {
                    log.warn("class not found");
                    e.printStackTrace();
                }
                emptyClassSet.add(clazz);
            }
        });

        if (files != null) { //if the array is not null,recurse to the directory in the array
            for (File file : files) {
                extractClassFile(emptyClassSet, file, packageName);
            }
        }
    }

    /**
     * Get an instance of classLoader from the current thread context
     *
     * @return classLoader
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     *
     * @param clazz
     * @param accessible Allow calls to private constructors
     * @param <T> The generated instance type
     * @return A new instance from specified class object
     */
    public static <T> T newInstance(Class<T> clazz, boolean accessible){
        try {
            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(accessible);
            return (T)constructor.newInstance();
        } catch (Exception e) {
            log.warn("new instance error");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //override newInstance method, implements the way of default parameter
    public static <T> T newInstance(Class<T> clazz){return newInstance(clazz, false);}

    //Set the Field value of the specified object
    public static void setField(Field field, Object fieldValue, Object object, boolean accessible){
        try {
            field.setAccessible(accessible);
            field.set(object, fieldValue);
        } catch (IllegalAccessException e) {
            log.warn("Not have access to autowired this field: " + field);
            e.printStackTrace();
        }
    }
}
