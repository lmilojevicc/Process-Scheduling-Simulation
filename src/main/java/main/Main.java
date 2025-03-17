package main;

import java.util.List;

public class Main {
    private static final int END_TIME = 5000;
    public static void main(String[] args) {
        List<Process> generatedProcesses = Process.generateProcesses(END_TIME);
        List<ExecutionSlot> timeline = Process.fcfs(generatedProcesses);
        Process.displayProcessTable(generatedProcesses);
        Process.displayGanttChart(timeline);
    }
}
