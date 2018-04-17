package com.github.lkq.timeron;

import com.github.lkq.timeron.measure.TimeRecorder;

import java.util.Map;

public class ReportBuilder {
    public String buildJSON(Map<String, TimeRecorder> timers) {
        String comma = "";
        StringBuilder json = new StringBuilder();
        json.append("[");

        for (String signature : timers.keySet()) {
            TimeRecorder timer = timers.get(signature);
            json.append(comma)
                    .append("{")
                    .append("\"").append(signature).append("\"").append(":")
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
