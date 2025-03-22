package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Process {
    private int ID;
    private ProcessState state;
    private int arrivalTime;
    private int burstTime;
    private int completionTime;
    private int turnAroundTime;
    private int waitingTime;
    private int remainingTime;

    public Process(int ID, int arrivalTime, int burstTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.state = ProcessState.NEW;
    }

    public static List<Process> generateProcesses(int endTime) {
        List<Process> processes = new ArrayList<>();
        int id = 1;
        int arrivalTime = 0;

        while (arrivalTime < endTime) {
            int numOfProcesses = ThreadLocalRandom.current().nextInt(1, 3);
            for (int i = 0; i < numOfProcesses; i++) {
                int burstTime = ThreadLocalRandom.current().nextInt(100, 501);

                Process p = new Process(id++, arrivalTime, burstTime);
                processes.add(p);
            }

            arrivalTime += ThreadLocalRandom.current().nextInt(200, 501);
        }

        return processes;
    }

    public static void displayProcessTable(List<Process> processes) {
        System.out.println("\nProcess Table: ");

        System.out.println("+-------+---------------+--------------+------------------+------------------+---------------+");
        System.out.println("| ID    | Arrival Time  | Burst Time   | Completion Time  | Turnaround Time  | Waiting Time  |");
        System.out.println("+-------+---------------+--------------+------------------+------------------+---------------+");

        int totalTurnaround = 0;
        int totalWaiting = 0;

        for (Process p : processes) {
            System.out.printf("| %-5d | %-13d | %-12d | %-16d | %-16d | %-13d |\n",
                    p.ID, p.arrivalTime, p.burstTime, p.completionTime, p.turnAroundTime, p.waitingTime);

            totalTurnaround += p.turnAroundTime;
            totalWaiting += p.waitingTime;
        }

        System.out.println("+-------+---------------+--------------+-----------------+-------------------+---------------+");

        double avgTurnaround = (double) totalTurnaround / processes.size();
        double avgWaiting = (double) totalWaiting / processes.size();

        System.out.printf("Average Turnaround Time: %.2f\n", avgTurnaround);
        System.out.printf("Average Waiting Time: %.2f\n", avgWaiting);
    }

    public static List<ExecutionSlot> fcfs(List<Process> processes) {
        ArrayList<Process> sortedProcesses = new ArrayList<>(processes);
        sortedProcesses.sort(Comparator.comparingInt(p -> p.arrivalTime));

        List<ExecutionSlot> timeline = new ArrayList<>();
        int currentTime = 0;

        for (Process process : sortedProcesses) {
            if (currentTime < process.arrivalTime) {
                timeline.add(new ExecutionSlot(-1, currentTime, process.arrivalTime));
                currentTime = process.arrivalTime;
            }

            process.state = ProcessState.RUNNING;
            int startTime = currentTime;
            currentTime += process.burstTime;

            timeline.add(new ExecutionSlot(process.getID(), startTime, currentTime));

            process.setCompletionTime(currentTime);
            process.setRemainingTime(0);
            process.setState(ProcessState.TERMINATED);
            process.calculateTimes();
        }

        return timeline;
    }

    public static List<ExecutionSlot> sjfNonPreemptive(List<Process> processes) {
        List<Process> remainingProcesses = new ArrayList<>(processes);
        List<ExecutionSlot> timeline = new ArrayList<>();
        int currentTime = 0;

        while (!remainingProcesses.isEmpty()) {
            List<Process> availableProcesses = new ArrayList<>();

            for (Process process : remainingProcesses) {
                if (process.arrivalTime <= currentTime) {
                    availableProcesses.add(process);
                }
            }

            if (availableProcesses.isEmpty()) {
                Process nextProcess = Collections.min(remainingProcesses,
                        Comparator.comparingInt(p -> p.arrivalTime)
                );
                timeline.add(
                        new ExecutionSlot(-1, currentTime, nextProcess.arrivalTime)
                );
                currentTime = nextProcess.arrivalTime;
                continue;
            }

            Process shortestJob = Collections.min(
                    availableProcesses, Comparator.comparingInt(p -> p.burstTime)
            );

            shortestJob.setState(ProcessState.RUNNING);
            int startTime = currentTime;
            currentTime += shortestJob.burstTime;

            timeline.add(new ExecutionSlot(shortestJob.getID(), startTime, currentTime));

            shortestJob.setCompletionTime(currentTime);
            shortestJob.setRemainingTime(0);
            shortestJob.setState(ProcessState.TERMINATED);
            shortestJob.calculateTimes();

            remainingProcesses.remove(shortestJob);
        }

        return timeline;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ProcessState getState() {
        return state;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        if (remainingTime < 0) {
            this.remainingTime = 0;
            return;
        }
        this.remainingTime = remainingTime;
    }

    public void calculateTimes() {
        this.turnAroundTime = this.completionTime - this.arrivalTime;
        this.waitingTime = this.turnAroundTime - this.burstTime;
    }

    @Override
    public String toString() {
        return String.format("PID%d", ID);
    }

    public static void displayGanttChart(List<ExecutionSlot> timeLine) {
        System.out.println("\nGantt Chart:");

        final int CELL_WIDTH = 12;

        for (int i = 0; i < timeLine.size(); i++) {
            System.out.print("+");
            for (int j = 0; j < CELL_WIDTH; j++) {
                System.out.print("-");
            }
        }
        System.out.println("+");

        for (ExecutionSlot slot : timeLine) {
            String label = (slot.processID == -1) ? "IDLE" : "P" + slot.processID;

            int cellPadding = (CELL_WIDTH - label.length());

            System.out.print("| ");
            System.out.print(label);

            for (int j = 0; j < cellPadding - 1; j++) {
                System.out.print(" ");
            }
        }

        System.out.println("|");

        for (int i = 0; i < timeLine.size(); i++) {
            System.out.print("+");
            for (int j = 0; j < CELL_WIDTH; j++) {
                System.out.print("-");
            }
        }

        System.out.println("+");

        for (ExecutionSlot slot : timeLine) {
            String timeStr = String.valueOf(slot.startTime);

            System.out.print(timeStr);

            int padding = CELL_WIDTH - timeStr.length() + 1;

            for (int j = 0; j < padding; j++) {
                System.out.print(" ");
            }
        }

        System.out.println(" " + timeLine.getLast().endTime);
    }

}

