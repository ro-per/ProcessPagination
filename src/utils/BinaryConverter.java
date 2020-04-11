package utils;

public final class BinaryConverter {

    public static String convertToBinary(int address, int length) {
        String binaryAddress = Integer.toBinaryString(address);
        int zeroes = length - binaryAddress.length();
        StringBuilder binaryBuilder = new StringBuilder();
        for (int i = 0; i < zeroes; i++) {
            binaryBuilder.append("0");
        }
        binaryBuilder.append(binaryAddress);
        return binaryBuilder.toString();
    }
}
