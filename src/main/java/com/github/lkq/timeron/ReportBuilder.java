package com.github.lkq.timeron;

import com.github.lkq.timeron.measure.TimeRecorder;

import java.lang.reflect.Method;
import java.util.Map;

public class ReportBuilder {
    public String buildJSON(Map<Method, TimeRecorder> timers) {
        String comma = "";
        StringBuilder json = new StringBuilder();
        json.append("[");

        for (Method method : timers.keySet()) {
            TimeRecorder timer = timers.get(method);
            json.append(comma)
                    .append("{")
                    .append("\"").append(method.getDeclaringClass().getName()).append(".").append(method.getName()).append("\"").append(":")
                    .append("{")
                    .append("\"total\":")
                    .append(timer.total())
                    .append(",")
                    .append("\"count\":")
                    .append(timer.count())
                    .append(",")
                    .append("\"avg\":")
                    .append(timer.avg())
                    .append("}}");
            comma = ",";
        }
        json.append("]");
        return json.toString();
    }
}
