package main;

import java.util.List;

public class Main {
    private static final int END_TIME = 5000;
    public static void main(String[] args) {
        List<Process> generatedProcesses = Process.generateProcesses(END_TIME);
        Process.displayProcessTable(generatedProcesses);
    }
}
