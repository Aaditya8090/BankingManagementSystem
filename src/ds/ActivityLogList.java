package ds;

import java.util.LinkedList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ActivityLogList {
    private static ActivityLogList instance;
    private LinkedList<String> logs;

    private ActivityLogList() {
        logs = new LinkedList<>();
    }

    public static ActivityLogList getInstance() {
        if (instance == null) {
            instance = new ActivityLogList();
        }
        return instance;
    }


    public void addLog(String log) {
        String timestamp = java.time.LocalDateTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        logs.addFirst("[" + timestamp + "] " + log);    //adds to the front of ll
        // logs.add("[" + timestamp + "] " + message);    //adds the log at the end of ll
    }


    public String getAllLogs() {
        if (logs.isEmpty()) return "No activity logs yet.";
        StringBuilder sb = new StringBuilder();
        for (String log : logs) {
            sb.append("- ").append(log).append("\n");
        }
        return sb.toString();
    }
}
