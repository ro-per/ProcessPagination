package test;

import org.junit.Test;
import utils.BinaryConverter;

import static org.junit.Assert.assertEquals;

public class BinaryConverterTest {

    @Test
    public void convertToBinary() {
        String binaryAddress = BinaryConverter.convertToBinary(1925, 16);
        assertEquals("0000011110000101", binaryAddress);
    }
}