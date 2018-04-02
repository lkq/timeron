package com.github.lkq.timeron;

import com.github.lkq.timeron.measure.InvocationTimer;

import java.lang.reflect.Method;
import java.util.Map;

public class ReportBuilder {
    public String buildJSON(Map<Method, InvocationTimer> timers) {
        String comma = "";
        StringBuilder json = new StringBuilder();
        json.append("[");

        for (Method method : timers.keySet()) {
            InvocationTimer timer = timers.get(method);
            json.append(comma)
                    .append("{")
                    .append("\"").append(method.getDeclaringClass().getName()).append(".").append(method.getName()).append("\"").append(":")
                    .append("{")
                    .append("\"avg\":")
                    .append(timer.avg())
                    .append("}}");
            comma = ",";
        }

        json.append("]");
        return json.toString();
    }
}
