package main;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static void printMenu() {
        System.out.println("\nChoose a scheduling algorithm:");
        System.out.println("1. First-Come, First-Served (FCFS)");
        System.out.println("2. Shortest Job First (SJF) - Non-preemptive");
        System.out.println("3. Shortest Job First (SJF) - Preemptive");
        System.out.println("4. Round Robin");

        System.out.print("\nEnter your choice (1-4): ");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Process scheduling simulator");
        System.out.println("-----------------------------");

        System.out.print("Enter upper-bound time (in ms) for process creation: ");
        int endTime = scanner.nextInt();

        List<Process> generatedProcesses = Process.generateProcesses(endTime);
        System.out.println("Created " + generatedProcesses.size() + " processes in timeframe of " + ((float) endTime / 1000) + " seconds");

        printMenu();

        List<ExecutionSlot> timeline = null;
        int choice = scanner.nextInt();
        char finish = 'y';

        while (finish != 'n') {
            switch (choice) {
                case 1:
                    System.out.println("Running First-Come, First-Served (FCFS)...");
                    timeline = Process.fcfs(generatedProcesses);
                    break;
                case 2:
                    System.out.println("Running Shortest Job First (SJF) Non-preemptive...");
                    timeline = Process.sjfNonPreemptive(generatedProcesses);
                    break;
                case 3:
                    System.out.println("Running Shortest Job First (SJF) Preemptive...");
                    timeline = Process.sjfPreemptive(generatedProcesses);
                    break;
                case 4:
                    System.out.print("Enter time quantum (in ms) for process Round Robin: ");
                    int timeQuantum = scanner.nextInt();
                    System.out.println("Running Round Robin...");
                    timeline = Process.roundRobin(generatedProcesses, timeQuantum);
                    break;
                default:
                    System.out.println("Invalid choice.");

            }

            if (timeline != null) {
                Process.displayProcessTable(generatedProcesses);
                Process.displayGanttChart(timeline);
            }

            System.out.print("Do you want to test another algorithm (y/n)?: ");
            finish = scanner.next().toLowerCase().charAt(0);

            if (finish != 'n') {
                printMenu();
                choice = scanner.nextInt();
            }
        }
    }
}
