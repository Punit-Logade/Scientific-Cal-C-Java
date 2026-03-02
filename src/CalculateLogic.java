package Scientific_Calculator;

import java.util.*;

public class CalculateLogic {

    private double lastResult;
    private boolean hasResult;
    
    public double calculate(double a, double b, char op) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0)
                    throw new ArithmeticException("Cannot divide by zero");
                return a / b;

            case '%':
                if (b == 0)
                    throw new ArithmeticException("Cannot use zero in modulus");
                return a % b;

            case '^': return Math.pow(a, b);

            default: throw new IllegalArgumentException("Invalid Operator");
        }
    }

    public void setLastResult(double result) {
        lastResult = result;
        hasResult = true;
    }

    public double getLastResult() {
        return lastResult;
    }

    public boolean hasResult() {
        return hasResult;
    }

    public double evaluateExpression(String exp) {

    exp = exp.replace("(", " ( ")
             .replace(")", " ) ")
             .replace("+", " + ")
             .replace("-", " - ")
             .replace("*", " * ")
             .replace("/", " / ")
             .replace("%", " % ")
             .replace("^", " ^ ")
             .replace("√", " √ ")
             .replace("sin", " sin ")
             .replace("cos", " cos ")
             .replace("tan", " tan ")
             .replace("log", " log ")
             .replace("ln", " ln ")
             .replace("!", " ! ");

    exp = exp.replaceAll("(sin|cos|tan)(\\d)", "$1($2");

    exp = exp.replaceAll("\\s+", " ").trim();

    List<String> postfix = infixToPostfix(exp);
    return evaluatePostfix(postfix);
}

    private int precedence(String c) {
        switch (c) {
            case "+":
            case "-": return 1;
            case "*": 
            case "/": 
            case "%": return 2;
            case "^": return 3;
            case "√":
            case "sin":
            case "cos":
            case "tan": return 4;
            case "log":
            case "ln": return 4;
            case "!": return 5;
        }
        return -1;
    }

    private List<String> infixToPostfix(String exp) {

    List<String> output = new ArrayList<>();
    Stack<String> stack = new Stack<>();
    String[] tokens = exp.split(" ");

    for (String token : tokens) {

        // Number
        if (token.matches("-?\\d+(\\.\\d+)?")) {
            output.add(token);
        }

        // Opening bracket
        else if (token.equals("(")) {
            stack.push(token);
        }

        // Closing bracket
        else if (token.equals(")")) {

            while (!stack.isEmpty() && !stack.peek().equals("(")) {
                output.add(stack.pop());
            }

            if (!stack.isEmpty()) stack.pop(); // remove '('
            if (!stack.isEmpty() && (stack.peek().equals("sin") || stack.peek().equals("cos") || stack.peek().equals("tan"))) {
                output.add(stack.pop());
            }
        }

        else if (token.equals("!")) {
            output.add(token);
        }
        // Operator
        else {

            while (!stack.isEmpty() &&
                  (precedence(stack.peek()) > precedence(token) ||
                  (precedence(stack.peek()) == precedence(token)
                          && !token.equals("^")))) {

                output.add(stack.pop());
            }

            stack.push(token);
        }
    }

    while (!stack.isEmpty()) {
        output.add(stack.pop());
    }

    return output;
}

    private double evaluatePostfix(List<String> postfix) {

        Stack<Double> stack = new Stack<>();

        for (String token : postfix) {

            if (token.matches("-?\\d+(\\.\\d+)?")) {
                stack.push(Double.parseDouble(token));
            }
        
            else if (token.equals("√")) {
                stack.push(Math.sqrt(stack.pop()));
            }
        
            else if (token.equals("sin")) {
                stack.push(Math.sin(Math.toRadians(stack.pop())));
            }
        
            else if (token.equals("cos")) {
                stack.push(Math.cos(Math.toRadians(stack.pop())));
            }
        
            else if (token.equals("tan")) {
                stack.push(Math.tan(Math.toRadians(stack.pop())));
            }

            else if (token.equals("log")) {
                stack.push(Math.log10(stack.pop()));
            }

            else if (token.equals("ln")) {
                stack.push(Math.log(stack.pop()));
            }

            else if (token.equals("!")) {
                double val = stack.pop();

                if (val < 0 || val != Math.floor(val)) throw new ArithmeticException("Invalid factorial");
                double fact = 1;
                for (int i = 1; i <= (int) val; i++)
                    fact *= i;
                stack.push(fact);
            }
        
            else {
                double b = stack.pop();
                double a = stack.pop();
                stack.push(calculate(a, b, token.charAt(0)));
            }
        }


        return stack.pop();
    }

    public boolean isValidExpression(String exp) {
        int balance = 0;

        for (char c : exp.toCharArray()) {
            if (c == '(') balance++;
            if (c == ')') balance--;
            if (balance < 0 ) return false;
        }

        return balance == 0;
    }

}
