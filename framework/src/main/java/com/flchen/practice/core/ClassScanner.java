package com.flchen.practice.core;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * author fl.chen
 * Date 2019-06-14
 * Time 20:48
 **/
public class ClassScanner {

    public static List<Class<?>> scannerClass(String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace('.', '/');

        ClassLoader defaultClassLoader = Thread.currentThread().getContextClassLoader();

        Enumeration<URL> resources = defaultClassLoader.getResources(path);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if(resource.getProtocol().contains("jar")) {
                JarURLConnection jarURLConnection = (JarURLConnection) resource.openConnection();
                String jarFilePath = jarURLConnection.getJarFile().getName();

                classes.addAll(getClassFromJar(jarFilePath, path));
            } else {

                //TODO
            }
        }

        return classes;
    }

    private static List<Class<?>> getClassFromJar(String jarFilePath, String path) throws IOException, ClassNotFoundException {

        List<Class<?>> classes = new ArrayList<>();
        JarFile jarFile = new JarFile(jarFilePath);
        Enumeration<JarEntry> jarEntries = jarFile.entries();

        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            String entryName = jarEntry.getName(); // jarEntry举例com/flchen/practice/Application.class
            if (entryName.startsWith(path) && entryName.endsWith(".class")) {
                String classFullName = entryName.replace('/', '.');
                classes.add(Class.forName(classFullName));
            }
        }
        return classes;
    }
}
