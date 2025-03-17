package main;

import java.util.ArrayList;
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
            int burstTime = ThreadLocalRandom.current().nextInt(100, 501);

            Process p = new Process(id++, arrivalTime, burstTime);
            processes.add(p);

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
            System.out.printf("| %-5d | %-13d | %-12d | %-15d | %-17d | %-13d |\n",
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
}

