package com.example.galactic;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GalacticUnitConvertWithExamples {

    private static final Map<String, String> wordToRomanMap = new HashMap<>();
    private static final Map<String, Double> itemValues = new HashMap<>();

    public static void main(String[] args) {
        // Example input handling
        String[] inputs = {
                "glob is I",
                "prok is V",
                "pish is X",
                "tegj is L",
                "glob glob Silver is 34 Credits",
                "glob prok Gold is 57800 Credits",
                "pish pish Iron is 3910 Credits",
                "how much is pish tegj glob glob ?",
                "how many Credits is glob prok Silver ?",
                "how many Credits is glob prok Gold ?",
                "how many Credits is glob prok Iron ?",
                "how much wood could a woodchuck chuck if a woodchuck could chuck wood?"
        };

        for (String input : inputs) {
            processInput(input);
        }
    }

    // Method to process each line of input
    private static void processInput(String input) {
        if (input.matches("(.*) is [IVXLCDM]")) {
            // Handle mapping of words to Roman numerals (e.g., "glob is I")
            addWordToRomanMapping(input);
        } else if (input.matches("(.*) is (\\d+) Credits")) {
            // Handle defining the value of items (e.g., "glob glob Silver is 34 Credits")
            addItemValue(input);
        } else if (input.matches("how much is (.*) \\?")) {
            // Handle queries asking for the value of Roman numerals (e.g., "how much is pish tegj glob glob ?")
            handleHowMuchQuery(input);
        } else if (input.matches("how many Credits is (.*) \\?")) {
            // Handle queries asking for the number of credits (e.g., "how many Credits is glob prok Silver ?")
            handleHowManyCreditsQuery(input);
        } else {
            // Handle invalid input
            System.out.println("I have no idea what you are talking about");
        }
    }

    // Method to add word to Roman numeral mapping
    private static void addWordToRomanMapping(String input) {
        String[] parts = input.split(" is ");
        wordToRomanMap.put(parts[0].trim(), parts[1].trim());
    }

    // Method to add item value based on input statement
    private static void addItemValue(String input) {
        String[] parts = input.split(" is ");
        String[] words = parts[0].trim().split(" ");
        String item = words[words.length - 1];
        String romanValue = "";

        for (int i = 0; i < words.length - 1; i++) {
            romanValue += wordToRomanMap.get(words[i]);
        }

        int numeralValue = romanToInt(romanValue);
        double credits = Double.parseDouble(parts[1].split(" ")[0]);
        double valuePerUnit = credits / numeralValue;
        itemValues.put(item, valuePerUnit);
    }

    // Method to handle "how much" queries
    private static void handleHowMuchQuery(String input) {
        Matcher matcher = Pattern.compile("how much is (.*) \\?").matcher(input);
        if (matcher.find()) {
            String[] words = matcher.group(1).trim().split(" ");
            String romanValue = "";

            for (String word : words) {
                if (!wordToRomanMap.containsKey(word)) {
                    System.out.println("I have no idea what you are talking about");
                    return;
                }
                romanValue += wordToRomanMap.get(word);
            }

            int value = romanToInt(romanValue);
            System.out.println(matcher.group(1).trim() + " is " + value);
        }
    }

    // Method to handle "how many Credits" queries
    private static void handleHowManyCreditsQuery(String input) {
        Matcher matcher = Pattern.compile("how many Credits is (.*) \\?").matcher(input);
        if (matcher.find()) {
            String[] words = matcher.group(1).trim().split(" ");
            String item = words[words.length - 1];
            String romanValue = "";

            for (int i = 0; i < words.length - 1; i++) {
                if (!wordToRomanMap.containsKey(words[i])) {
                    System.out.println("I have no idea what you are talking about");
                    return;
                }
                romanValue += wordToRomanMap.get(words[i]);
            }

            if (!itemValues.containsKey(item)) {
                System.out.println("I have no idea what you are talking about");
                return;
            }

            int numeralValue = romanToInt(romanValue);
            double credits = numeralValue * itemValues.get(item);
            System.out.println(matcher.group(1).trim() + " is " + (int) credits + " Credits");
        }
    }

    // Convert a Roman numeral string to an integer
    private static int romanToInt(String s) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        int total = 0;
        int prevValue = 0;

        for (int i = s.length() - 1; i >= 0; i--) {
            int currentValue = romanMap.get(s.charAt(i));

            if (currentValue < prevValue) {
                total -= currentValue;
            } else {
                total += currentValue;
            }

            prevValue = currentValue;
        }

        return total;
    }
}

