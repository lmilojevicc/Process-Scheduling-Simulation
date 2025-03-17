package main;

public class ExecutionSlot {
    int processID;
    int startTime;
    int endTime;

    public ExecutionSlot(int processID, int startTime, int endTime) {
        this.processID = processID;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}