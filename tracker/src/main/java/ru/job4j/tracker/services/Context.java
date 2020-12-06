package ru.job4j.tracker.services;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class Context {
    private final Map<String, Object> els = new HashMap<>();

    public <T> void reg(Class<T> cl) {
        Constructor<?>[] constructors = cl.getDeclaredConstructors();
        if (constructors.length > 1) {
            throw new IllegalStateException("Class has multiple constructors : " + cl.getCanonicalName());
        }
        Constructor<?> con = constructors[0];
        List<Object> args = new ArrayList<>();
        for (Class<?> arg : con.getParameterTypes()) {
            if (!els.containsKey(arg.getCanonicalName())) {
                throw new IllegalStateException("Object doesn't found in context : " + arg.getCanonicalName());
            }
            args.add(els.get(arg.getCanonicalName()));
        }
        try {
            final Object o = con.newInstance(args.toArray());
            els.put(cl.getCanonicalName(), o);
            final Class<?>[] interfaces = cl.getInterfaces();
            for (Class<?> in : interfaces) {
                els.put(in.getCanonicalName(), o);
            }
        } catch (Exception e) {
            throw new IllegalStateException("Couldn't create an instance of : " + cl.getCanonicalName(), e);
        }
    }

    public <T> T get(Class<T> inst) {
        return (T) els.get(inst.getCanonicalName());
    }
}