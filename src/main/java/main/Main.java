package main;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Process scheduling simulator");
        System.out.println("-------------------------------------");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter upper-bound time (in ms) for process creation: ");
        int endTime = scanner.nextInt();
        List<Process> generatedProcesses = Process.generateProcesses(endTime);
        System.out.println("Created " + generatedProcesses.size() + " processes in timeframe of " + endTime / 1000 + " seconds");

        List<ExecutionSlot> timeline = Process.sjfNonPreemptive(generatedProcesses);
        Process.displayProcessTable(generatedProcesses);
        Process.displayGanttChart(timeline);
    }
}
