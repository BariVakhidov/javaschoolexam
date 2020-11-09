package com.tsystems.javaschool.tasks.calculator;

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
        if (statement == null || statement.contains(",") || statement.equals("")
                || statement.contains("..") || statement.contains("**") || statement.contains("//")
                || statement.contains("++") || statement.contains("--"))
            return null;

        statement = statement.replaceAll(" ", "");
        char[] chars = statement.toCharArray();

        List<String> tokens = new ArrayList<>();
        String num;

        //парсинг входной строки
        for (int i = 0; i < chars.length; i++) {
            if (Character.isDigit(chars[i]) && i < chars.length - 1 && (Character.isDigit(chars[i + 1]) ^chars[i+1]=='.')) {
                num = Character.toString(chars[i]);
                while (chars[i]=='.' || Character.isDigit(chars[i]) && i < chars.length - 1 && (Character.isDigit(chars[i + 1]) ^ chars[i+1]=='.')) {
                    num = num.concat(Character.toString(chars[i + 1]));
                    i++;
                }
                tokens.add(num);
                continue;
            }
            tokens.add(Character.toString(chars[i]));

        }
        System.out.println(tokens);

        //проверка всех скобок
        int openBr = 0;
        int closeBr = 0;
        Pattern pattern = Pattern.compile("\\(");
        Matcher matcher = pattern.matcher(statement);
        while (matcher.find())
        {
            ++openBr;
        }
        pattern = Pattern.compile("\\)");
        matcher = pattern.matcher(statement);
        while (matcher.find())
        {
            ++closeBr;
        }

        if(openBr!=closeBr)
            return null;
        Deque<String> operatorStack = new ArrayDeque<>();
        LinkedList<String> queueExit = new LinkedList<>();

        // приведение входного листа в обратную польскую запись по алгоритму сортировочной станции
        for (String token : tokens){
            if (isNumeric(token)) {
                queueExit.add(token);
            }
            if (token.equals("/")  || token.equals("*") || token.equals("+")|| token.equals("-")) {
                while (!operatorStack.isEmpty() && operatorStack.peekLast().equals("*") ^ operatorStack.peekLast().equals("/") ^ operatorStack.peekLast().equals("-")) {
                    queueExit.add(operatorStack.pollLast());
                }
                operatorStack.addLast(token);
            }
            if (token.equals("(")) {
                operatorStack.addLast(token);
            }
            if (token.equals(")") ) {
                while (!operatorStack.isEmpty() && !operatorStack.peekLast().equals("(")) {
                    queueExit.add(operatorStack.pollLast());
                }
                if (operatorStack.isEmpty()) {
                    throw new IllegalArgumentException("Unmatched brackets");
                }
                operatorStack.pollLast();
            }
        }
        if (!operatorStack.isEmpty()) {
            while (!operatorStack.isEmpty()) {
                queueExit.add(operatorStack.pollLast());
            }
        }
        System.out.println(queueExit);

        //вычисление выражения
        for (int i = 0; i < queueExit.size(); i ++) {
            if (queueExit.get(i).equals("-")) {
                Double x = Double.parseDouble(queueExit.get(i-1));
                Double y = Double.parseDouble(queueExit.get(i-2));
                queueExit.add(i+1, Double.toString(y-x));
                queueExit.remove(i);queueExit.remove(i-1);queueExit.remove(i-2);
                i = 0;
            }
            if (queueExit.get(i).equals("+")) {
                Double x = Double.parseDouble(queueExit.get(i-1));
                Double y = Double.parseDouble(queueExit.get(i-2));
                queueExit.add(i+1, Double.toString(y+x));
                queueExit.remove(i);queueExit.remove(i-1);queueExit.remove(i-2);
                i =0;
            }
            if (queueExit.get(i).equals("*")) {
                Double x = Double.parseDouble(queueExit.get(i-1));
                Double y = Double.parseDouble(queueExit.get(i-2));
                queueExit.add(i+1, Double.toString(y*x));
                queueExit.remove(i);queueExit.remove(i-1);queueExit.remove(i-2);
                i = 0;
            }
            if (queueExit.get(i).equals("/")) {
                Double x = Double.parseDouble(queueExit.get(i-1));
                Double y = Double.parseDouble(queueExit.get(i-2));
                queueExit.add(i+1, Double.toString(y/x));
                queueExit.remove(i);queueExit.remove(i-1);queueExit.remove(i-2);
                i=0;
            }
        }

        System.out.println(queueExit);
        Double result =Double.parseDouble(queueExit.getLast());

        if (result.isInfinite()) {
            return null;
        }

        if(result%1==0)
            return String.format("%.0f",result);
        String strResult = String.format("%.4f",result);
        strResult = strResult.replace(',','.').replaceFirst("0+$","");
        System.out.println(strResult);
        return strResult;
    }

    public static boolean isNumeric(String strNum) {
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

}
