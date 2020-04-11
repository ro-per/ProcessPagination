package test;

import entities.Instruction;
import entities.InstructionReader;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InstructionReaderTest {

    @Test
    public void readInstructions() throws IOException, SAXException, ParserConfigurationException {
        int processID = 0;
        int virtualAddress = 0;
        String operation = "Start";
        List<Instruction> list = InstructionReader.getInstance().readInstructions(InstructionReader.INSTR_30_4);
        Instruction instruction = list.get(0);
        assertEquals(instruction.getProcessID(), processID);
        assertEquals(instruction.getOperation(), operation);
        assertEquals(instruction.getVirtualAddress(), virtualAddress);
    }
}