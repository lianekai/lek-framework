package com.lianekai.util.bean;

import com.lianekai.util.common.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类的操作工具类
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/03/19 22:52
 */
@Slf4j
public class ClassUtils {
    private ClassUtils() {
    }

    public static List<Class<?>> getClassList(String packageName, Class<? extends Annotation> annotationClass) {
        List<Class<?>> classList = getClasses(packageName);
        Iterator<Class<?>> iterator = classList.iterator();
        while (iterator.hasNext()) {
            Class<?> next = iterator.next();
            if (!next.isAnnotationPresent(annotationClass)) {
                iterator.remove();
            }
        }
        return classList;
    }

    public static List<Class<?>> getClasses(String packageName) {
        // 第一个class类的集合
        List<Class<?>> classes = new ArrayList<>();
        // 是否循环迭代
        boolean recursive = true;
        // 获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader()
                    .getResources(packageDirName);
            // 循环迭代下去
            while (dirs.hasMoreElements()) {
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                log.info("getClasses protocol:{}", protocol);
                // 如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    addClass(classes, filePath, packageName);
                } else if ("jar".equals(protocol)) {
                    // 如果是jar包文件
                    // 定义一个JarFile
                    JarFile jar;
                    try {
                        // 获取jar
                        jar = ((JarURLConnection) url.openConnection())
                                .getJarFile();
                        // 从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        // 同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // 如果是以/开头的
                            if (name.charAt(0) == '/') {
                                // 获取后面的字符串
                                name = name.substring(1);
                            }
                            // 如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                // 如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    // 获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx)
                                            .replace(Constant.LEFT_SLASH_CHAR, Constant.POINT_EN_CHAR);
                                }
                                // 如果可以迭代下去 并且是一个包
                                if (((idx != -1) || recursive) && (name.endsWith(".class") && !entry.isDirectory())) {
                                    // 如果是一个.class文件 而且不是目录
                                    // 去掉后面的".class" 获取真正的类名
                                    String className = name.substring(
                                            packageName.length() + 1,
                                            name.length() - 6);
                                    try {
                                        // 添加到classes
                                        classes.add(Class
                                                .forName(packageName + Constant.POINT_EN_CHAR
                                                        + className));
                                    } catch (ClassNotFoundException e) {
                                        log.error("", e);
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        log.error(Constant.EMPTY_STRING, e);
                    }
                }
            }
        } catch (IOException e) {
            log.error("", e);
        }
        return classes;
    }

    public static List<Class<?>> getClassList(String packageName) {
        List<Class<?>> classList = new LinkedList<>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(Constant.POINT_EN, Constant.LEFT_SLASH));
            while (urls != null && urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String packagePath = url.getPath().replace("%20", " ");
                    addClass(classList, packagePath, packageName);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return classList;
    }

    private static void addClass(List<Class<?>> classList, String packagePath, String packageName) {
        try {

            File[] files = new File(packagePath).listFiles(file -> (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory()
            );

            if (files != null) {
                for (File file : files) {
                    String fileName = file.getName();
                    if (file.isFile()) {
                        String className = fileName.substring(0, fileName.lastIndexOf('.'));
                        if (StringUtils.isNotEmpty(packageName)) {
                            className = packageName + "." + className;
                        }
                        doAddClass(classList, className);
                    } else {
                        String subPackagePath = fileName;
                        if (StringUtils.isNotEmpty(packagePath)) {
                            subPackagePath = packagePath + Constant.LEFT_SLASH + subPackagePath;
                        }
                        String subPackageName = fileName;
                        if (StringUtils.isNotEmpty(packageName)) {
                            subPackageName = packageName + Constant.POINT_EN + subPackageName;
                        }
                        addClass(classList, subPackagePath, subPackageName);
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void doAddClass(List<Class<?>> classList, String className) {
        Class<?> cls = loadClass(className, false);
        classList.add(cls);
    }

    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return cls;
    }

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            String msg = String.format(("实例化对象时出现错误,请尝试给 %s 添加无参的构造方法"), clazz.getName());
            throw new RuntimeException(msg, e);
        }
    }

}
