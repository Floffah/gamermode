package dev.floffah.gamermode.events;

import dev.floffah.gamermode.server.Server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventEmitter {
    Server server;

    Map<Class<? extends Event>, List<Executor>> lnrs = new HashMap<>();

    public EventEmitter(Server server) {
        this.server = server;
    }

    public void execute(Event e) {
        if (lnrs.containsKey(e.getClass())) {
            List<Executor> ex = lnrs.get(e.getClass());
            for (Executor exe : ex) {
                exe.execute(e);
            }
        }
    }

    public void registerListeners(Listener l) {
        Method[] methods = l.getClass().getMethods();
        for (Method m : methods) {
            EventListener evn = m.getAnnotation(EventListener.class);
            if (evn == null) continue;
            Class<?> eused;
            if (m.getParameterTypes().length != 1 || !Event.class.isAssignableFrom(eused = m.getParameterTypes()[0])) {
                server.logger.err(String.format("Method %s() in class %s has incorrect parameters", m.getName(), l.getClass().getName()));
                continue;
            }
            Executor e = new Executor() {
                @Override
                public void execute(Event event) {
                    try {
                        m.invoke(l, event);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        server.logger.printStackTrace(e);
                    }
                }
            };
            List<Executor> lnr = lnrs.get(eused);
            if (lnr == null) {
                lnr = new ArrayList<>();
            }
            lnr.add(e);
            lnrs.put((Class<? extends Event>) eused, lnr);
        }
    }
}
