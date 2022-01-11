package com.tsystems.javaschool.tasks.calculator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */


    public String evaluate(String statement) {
        if (statement == null || statement.isEmpty()) return null; // returns null if null or empty expression
        String result = statement;
        //Logics to handle expression with parentheses
        if (result.contains(")") && result.contains("(")) {
            while (result.contains(")")) {
                //Checks if '(' located before ')'
                if ((result.contains(")") && result.contains("(")) &&
                        (result.lastIndexOf("(") < result.indexOf(")"))) {
                    //Substring to handle expressions inside parentheses
                    String substr = result.substring(result.lastIndexOf("(") + 1, result.indexOf(")"));
                    substr = checkExpression(substr); //checks if expression is valid
                    substr = evaluateInsidePars(substr); //calculating expression
                    if (substr == null) return null; //if expression isn't valid or calculation returns null, result is null

                    //String builder for replacing parentheses expression with calculated substring expression
                    StringBuilder stringBuilder = new StringBuilder(result);
                    stringBuilder.replace(result.lastIndexOf("("), result.indexOf(")") + 1, substr);
                    result = stringBuilder.toString();

                } else {

                    return null;
                }
            }
        }
        //If expression doesn't contain parentheses, check and calculate expression
        if (!(result.contains(")") && result.contains("("))) {
            result = checkExpression(result);
            if (result.equals("null")) return null;
            result = evaluateInsidePars(result);
        }

        if (result == null) return null;
        //Formatting expression to return final result
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("0.####", symbols);
        return (df.format(Double.parseDouble(result)));
    }

    //Function for evaluating expressions inside parentheses or if it isn't parentheses
    //Firstly, we prepare LinkedList with tokens, which consists of numbers and Math symbols
    //Secondly, we evaluate expressions, one per operation, until it is only one token with Number left inside LinkedList
    private static String evaluateInsidePars(String statement) {
        LinkedList<String> tokens = getTokens(statement);
        // A mathematical expression can start with unary "-" or "+", e.g. "-3*7+4"
        // Next if-statement had been written to correctly handle this case
            assert tokens != null;
            if (tokens.get(0).matches("[-+]")) {
            tokens.set(1, tokens.get(0) + tokens.get(1));
            tokens.remove(0);
        }
        // For-loop statement to correct tokens if it is negative number after * or / operation
        // (For ex. to handle this type of expression: 5+2/-2)
        for (int i = 0; i < tokens.size() - 1; i++) {
            if (tokens.get(i + 1).matches("[-]") && tokens.get(i).matches("[*/]")) {
                tokens.set(i + 1, tokens.get(i + 1) + tokens.get(i + 2));
                tokens.remove(i + 2);
            }
        }

        if (tokens.size() % 2 == 0) return null;  // Since now all operators in the token list are binary
        // there must be odd number of tokens in the list.
        double res;
        if (tokens.size() == 1) return tokens.get(0);
        // This for-loop used for calculation of multiplication and division operations according to operator precedence.
        for (int i = 0; i < tokens.size() - 1; ) {
            switch (tokens.get(i + 1)) {
                case "*":
                    res = Double.parseDouble(tokens.get(i)) * Double.parseDouble(tokens.get(i + 2));
                    break;
                case "/":
                    double divisor = Double.parseDouble(tokens.get(i + 2));
                    if (divisor == 0) return null;
                    res = Double.parseDouble(tokens.get(i)) / Double.parseDouble(tokens.get(i + 2));
                    break;
                default:
                    i += 2;
                    continue;
            }
            tokens.set(i, String.valueOf(res));
            tokens.subList(i + 1, i + 3).clear();
        }
        if (tokens.size() == 1) return tokens.get(0);
        // This for-loop used for calculation of addition and subtraction operations according to operator precedence.
        for (int i = 0; i < tokens.size() - 1; ) {
            switch (tokens.get(i + 1)) {
                case "+":
                    res = Double.parseDouble(tokens.get(i)) + Double.parseDouble(tokens.get(i + 2));
                    break;
                case "-":
                    res = Double.parseDouble(tokens.get(i)) - Double.parseDouble(tokens.get(i + 2));
                    break;
                default:
                    return null;
            }
            tokens.set(i, String.valueOf(res));
            tokens.subList(i + 1, i + 3).clear();
        }
        return tokens.get(0);
    }

    //Function to split String into numbers and Mathematical symbols that would be stored inside LinkedList as Tokens
    private static LinkedList<String> getTokens(String statement) {
        LinkedList<String> tokens = new LinkedList<>();
        Pattern p = Pattern.compile("(\\d+(\\.\\d+)?|[-+*\\/])");
        Matcher m = p.matcher(statement);
        int pos = 0;
        while (m.find()) {
            if (m.start() != pos) return null;    // Invalid format of the token.
            tokens.add(m.group());
            pos = m.end();
        }
        if (pos != statement.length()) return null;   // Invalid format of the token.
        return tokens;
    }

    //Function for checking for valid expressions
    private static String checkExpression(String statement) {
        Pattern pattern = Pattern.compile("^([-+]?\\d+[.]?\\d*[\\+\\-\\*\\/]{1})+\\d+([.]?\\d+)?$");
        Matcher matcher = pattern.matcher(statement);
        if (matcher.matches()) {
            return statement;
        } else {
            return "null";
        }
    }
}
