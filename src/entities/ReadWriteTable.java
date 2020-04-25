package entities;

import java.util.ArrayList;
import java.util.List;

public class ReadWriteTable {

    private List<ReadWriteTableEntry> entries;
    private Boolean inList=false;


    public ReadWriteTable() {
        entries = new ArrayList<>();
        entries.add(new ReadWriteTableEntry());
    }


    public void updateCount(int p, int r, int w) {
        ReadWriteTableEntry entry = new ReadWriteTableEntry(p, r, w);
        int index;
        entries.clear();

/*        for (int i = 0; i < entries.size(); i++) {
            ReadWriteTableEntry e = entries.get(i);

            if(e.getProcessID()==entry.getProcessID()){
                e.updateValues(entry);
                inList=true;
            }
        }
        if(!inList){*/
            entries.add(entry);
/*        }*/
    }

    public int getGetTotalEntries() {
        return entries.size();
    }
    public List<ReadWriteTableEntry> getEntries(){
        return this.entries;
    }
    public ReadWriteTableEntry getEntry(int i){
        return this.entries.get(i);
    }


}
