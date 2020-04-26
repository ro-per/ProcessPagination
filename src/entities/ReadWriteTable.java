package entities;

import java.util.ArrayList;
import java.util.List;

public class ReadWriteTable {
    private List<ReadWriteTableEntry> entries;

    public ReadWriteTable() {
        entries = new ArrayList<>();
        entries.add(new ReadWriteTableEntry());
    }

    public void updateCount(int p, int r, int w) {
        ReadWriteTableEntry entry = new ReadWriteTableEntry(p, r, w);
        int index;
        entries.clear();
        entries.add(entry);
    }
}
