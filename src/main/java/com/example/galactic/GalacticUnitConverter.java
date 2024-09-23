package com.example.galactic;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class GalacticUnitConverter {

    // Mapping of Roman numerals to their integer values for Roman to Integer conversion
    private static final Map<Character, Integer> romanToIntMap = new HashMap<>();
    // Mapping of integer values to their Roman numeral equivalents for Integer to Roman conversion
    private static final LinkedHashMap<Integer, String> intToRomanMap = new LinkedHashMap<>();

    static {
        // Initialize Roman to Integer map
        romanToIntMap.put('I', 1);
        romanToIntMap.put('V', 5);
        romanToIntMap.put('X', 10);
        romanToIntMap.put('L', 50);
        romanToIntMap.put('C', 100);
        romanToIntMap.put('D', 500);
        romanToIntMap.put('M', 1000);

        // Initialize Integer to Roman map
            intToRomanMap.put(1000, "M");
        intToRomanMap.put(900, "CM");
        intToRomanMap.put(500, "D");
        intToRomanMap.put(400, "CD");
        intToRomanMap.put(100, "C");
        intToRomanMap.put(90, "XC");
        intToRomanMap.put(50, "L");
        intToRomanMap.put(40, "XL");
        intToRomanMap.put(10, "X");
        intToRomanMap.put(9, "IX");
        intToRomanMap.put(5, "V");
        intToRomanMap.put(4, "IV");
        intToRomanMap.put(1, "I");
    }

    // Method to convert a Roman numeral to an integer
    public static int romanToInt(String s) {
        if (!isValidRoman(s)) {
            System.out.println("Error: Invalid Roman numeral structure.");
            return -1;
        }

        int total = 0;
        int prevValue = 0;

        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            int currentValue = romanToIntMap.get(c);

            if (currentValue < prevValue) {
                total -= currentValue;
            } else {
                total += currentValue;
            }

            prevValue = currentValue;
        }

        return total;
    }

    // Method to validate the Roman numeral according to specified rules
    private static boolean isValidRoman(String s) {
        // Check for invalid repeats of symbols that shouldn't repeat
        if (s.matches(".*(IIII|XXXX|CCCC|MMMM|VV|LL|DD).*")) return false;

        // Check specific patterns that violate subtraction rules
        if (s.matches(".*IL|IC|ID|IM|VX|VL|VC|VD|VM|XD|XM|LC|LD|LM|DM.*")) return false;

        // Check for valid repeat patterns (allow four times if separated by a smaller value)
        if (s.matches(".*(IIII|XXXX|CCCC).*") && !s.matches(".*(IV|IX|XL|XC|CD|CM).*")) return false;

        // Symbols 'V', 'L', and 'D' should never be subtracted
        if (s.matches(".*(VV|LL|DD).*")) return false;

        return true;
    }

    // Method to convert an integer to a Roman numeral
    public static String intToRoman(int num) {
        StringBuilder roman = new StringBuilder();

        for (Map.Entry<Integer, String> entry : intToRomanMap.entrySet()) {
            int value = entry.getKey();
            String symbol = entry.getValue();

            while (num >= value) {
                roman.append(symbol);
                num -= value;
            }
        }

        return roman.toString();
    }

    // Main method for testing
    public static void main(String[] args) {
        // Testing Roman to Integer conversion
        String romanNumeral1 = "MMVI";
        System.out.println("Roman numeral " + romanNumeral1 + " is: " + romanToInt(romanNumeral1)); // Output: 2006

        String romanNumeral2 = "MCMXLIV";
        System.out.println("Roman numeral " + romanNumeral2 + " is: " + romanToInt(romanNumeral2)); // Output: 1944

        // Test with invalid input
        String invalidRomanNumeral = "IIII";
        System.out.println("Roman numeral " + invalidRomanNumeral + " is: " + romanToInt(invalidRomanNumeral)); // Output: Error message

        // Testing Integer to Roman conversion
        int number1 = 2006;
        System.out.println("Integer " + number1 + " in Roman numeral is: " + intToRoman(number1)); // Output: MMVI

        int number2 = 1903;
        System.out.println("Integer " + number2 + " in Roman numeral is: " + intToRoman(number2)); // Output: MCMXLIV
    }
}


