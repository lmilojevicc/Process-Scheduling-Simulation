package main;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Process> generatedProcesses = Process.generateProcesses(5000);
        for (Process p : generatedProcesses) {
            System.out.println(p);
        }
    }
}