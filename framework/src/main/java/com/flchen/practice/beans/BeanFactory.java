package com.flchen.practice.beans;

import com.flchen.practice.web.mvc.Controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {

    private static Map<Class<?>, Object> beans = new ConcurrentHashMap<>();


    public static Object getBean(Class<?> cls) {
        return beans.get(cls);
    }

    public static void initBeans(List<Class<?>> clsList) throws Exception {

        List<Class<?>> toCreate = new ArrayList<>(clsList);

        System.out.println("[start] -- initialize beans");
        while (0 != toCreate.size()) {
            int remainSize = toCreate.size();
            for (int i = 0; i < toCreate.size(); i++) {
                if(createSuccess(toCreate.get(i))) {
//                    System.out.println("[create] -- create success : " + toCreate.get(i).getSimpleName());
                    toCreate.remove(i);
                }
            }
            if (remainSize == toCreate.size()) {
                throw new Exception("Cycle dependency exception.");
            }
        }
        System.out.println("[end] -- initialize beans");
    }

    private static boolean createSuccess(Class<?> cls) throws IllegalAccessException, InstantiationException {
        if (!(cls.isAnnotationPresent(Bean.class) || cls.isAnnotationPresent(Controller.class))) {
//            System.out.println("[ignore] -- class : " + cls.getSimpleName());
            return true;
        }

        Object bean = cls.newInstance();
        List<Field> fields = Arrays.asList(cls.getFields());
        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                Class<?> fieldType = field.getType();
                Object reliantBean = beans.get(fieldType);
                if (null == reliantBean) {
                    System.out.println("[dependency] -- dependency not exist : " + reliantBean.getClass().getSimpleName());
                    // dependency absent
                    return false;
                }
                field.setAccessible(true);
                field.set(bean, reliantBean);
            }
        }
        beans.put(cls, bean);
        return true;
    }

    public static Map<Class<?>, Object> getBeans() {
        return beans;
    }
}
