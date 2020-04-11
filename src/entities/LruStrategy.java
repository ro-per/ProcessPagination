package entities;

import java.util.List;

public class LruStrategy implements ReplacementStrategy {

    private int minimumAccessTime;

    @Override
    public Process getProcess(List<Process> processes) {
        minimumAccessTime = Integer.MAX_VALUE;
        Process lruProcess = null;
        for (Process process : processes) {
            if (process.getProcessLastAccessTime() < minimumAccessTime) {
                minimumAccessTime = process.getProcessLastAccessTime();
                lruProcess = process;
            }
        }
        return lruProcess;
    }

    @Override
    public Page getPage(Process process) {
        minimumAccessTime = Integer.MAX_VALUE;
        Page lruPage = null;
        PageTable pageTable = process.getPageTable();
        for (Entry entry : pageTable.getEntries()) {
            if (entry.isPresent() && entry.getLastAccessTime() < minimumAccessTime) {
                minimumAccessTime = entry.getLastAccessTime();
                lruPage = entry.getPage();
            }
        }
        return lruPage;
    }
}
