package test;

import entities.Instruction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InstructionTest {

    @Test
    public void getOffset() {
        Instruction instruction = new Instruction();
        instruction.setVirtualAddress(21639);
        int expected = 1159;
        assertEquals(expected, instruction.getOffset());
    }

    @Test
    public void getPageNumber() {
        Instruction instruction = new Instruction();
        instruction.setVirtualAddress(21639);
        int expected = 5;
        assertEquals(expected, instruction.getPageNumber());
    }


}