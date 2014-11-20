package be.rubus.web.valerie.storage;

import be.rubus.web.valerie.utils.ProxyUtils;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 */
@ApplicationScoped
// FIXME Use the MethodHandles of Java 7
public class PropertyStorage {
    @Inject
    protected transient Logger logger;

    private Map<String, Map<String, Field>> fieldMap = new ConcurrentHashMap<>();
    private Map<String, Map<String, Method>> methodMap = new ConcurrentHashMap<>();
    private Map<String, List<String>> fieldNotAvailableMap = new ConcurrentHashMap<>();
    private Map<String, List<String>> methodNotAvailableMap = new ConcurrentHashMap<>();

    public void storeField(Class targetClass, String property, Field field) {
        if (field != null) {
            getFieldMapForClass(targetClass).put(property, field);
        } else {
            getNotAvailableFieldListForClass(targetClass).add(property);
        }
    }

    public void storeMethod(Class targetClass, String property, Method method) {
        if (method != null) {
            getMethodMapForClass(targetClass).put(property, method);
        } else {
            getNotAvailableMethodListForClass(targetClass).add(property);
        }
    }

    public Field getField(Class targetClass, String property) {
        Map<String, Field> fieldMap = getFieldMapForClass(targetClass);
        return fieldMap.get(property);
    }

    public Method getMethod(Class targetClass, String property) {
        Map<String, Method> methodMap = getMethodMapForClass(targetClass);
        return methodMap.get(property);
    }

    public boolean containsField(Class targetClass, String property) {
        boolean result = getFieldMapForClass(targetClass).containsKey(property);

        if (!result) {
            result = getNotAvailableFieldListForClass(targetClass).contains(property);
        }
        return result;
    }

    public boolean containsMethod(Class targetClass, String property) {
        boolean result = getMethodMapForClass(targetClass).containsKey(property);

        if (!result) {
            result = getNotAvailableMethodListForClass(targetClass).contains(property);
        }
        return result;
    }

    private Map<String, Field> getFieldMapForClass(Class target) {
        String key = ProxyUtils.getClassName(target);
        if (!this.fieldMap.containsKey(key)) {
            this.fieldMap.put(key, new ConcurrentHashMap<String, Field>());
        }
        return this.fieldMap.get(key);
    }

    private List<String> getNotAvailableFieldListForClass(Class target) {
        String key = ProxyUtils.getClassName(target);
        if (!this.fieldNotAvailableMap.containsKey(key)) {
            this.fieldNotAvailableMap.put(key, new CopyOnWriteArrayList<String>());
        }
        return this.fieldNotAvailableMap.get(key);
    }

    private Map<String, Method> getMethodMapForClass(Class target) {
        String key = ProxyUtils.getClassName(target);
        if (!this.methodMap.containsKey(key)) {
            this.methodMap.put(key, new ConcurrentHashMap<String, Method>());
        }
        return this.methodMap.get(key);
    }

    private List<String> getNotAvailableMethodListForClass(Class target) {
        String key = ProxyUtils.getClassName(target);
        if (!this.methodNotAvailableMap.containsKey(key)) {
            this.methodNotAvailableMap.put(key, new CopyOnWriteArrayList<String>());
        }
        return this.methodNotAvailableMap.get(key);
    }
}
