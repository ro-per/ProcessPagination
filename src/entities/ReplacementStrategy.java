package entities;

import java.util.List;

public interface ReplacementStrategy {

    Process getProcess(List<Process> processes);

    Page getPage(Process process);
}
