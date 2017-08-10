package ru.hd.olaf.main;

import java.util.Stack;
import java.util.Scanner;
import java.util.regex.Pattern;

public class KauffmanW7_LargeProgram05_EvalBoolean
{

    public static void main(String[ ] args)
    {
        Scanner stdin = new Scanner(System.in);
        String expression;
        boolean answer;

        // Testing Expressions.
        expression = "(5>1)";
        answer = evaluate(expression);
        System.out.print("Test01: " + expression + " evaluated as ");
        if (answer == true) {
            System.out.println("true, which is correct.");
        }
        else {
            System.out.println("false, which is WRONG.");
        }

        expression = "(5>=1)";
        answer = evaluate(expression);
        System.out.print("Test02: " + expression + " evaluated as ");
        if (answer == true) {
            System.out.println("true, which is correct.");
        }
        else {
            System.out.println("false, which is WRONG.");
        }

        expression = "(5<1)";
        answer = evaluate(expression);
        System.out.print("Test03: " + expression + " evaluated as ");
        if (answer == true) {
            System.out.println("true, which is WRONG.");
        }
        else {
            System.out.println("false, which is correct.");
        }

        expression = "(5<=1)";
        answer = evaluate(expression);
        System.out.print("Test04: " + expression + " evaluated as ");
        if (answer == true) {
            System.out.println("true, which is WRONG.");
        }
        else {
            System.out.println("false, which is correct.");
        }

        expression = "(5==1)";
        answer = evaluate(expression);
        System.out.print("Test05: " + expression + " evaluated as ");
        if (answer == true) {
            System.out.println("true, which is WRONG.");
        }
        else {
            System.out.println("false, which is correct.");
        }

        expression = "(5!=1)";
        answer = evaluate(expression);
        System.out.print("Test06: " + expression + " evaluated as ");
        if (answer == true) {
            System.out.println("true, which is correct.");
        }
        else {
            System.out.println("false, which is WRONG.");
        }

        expression = "(10 > 5)";
        answer = evaluate(expression);
        System.out.print("Test07: " + expression + " evaluated as ");
        if (answer == true) {
            System.out.println("true, which is correct.");
        }
        else {
            System.out.println("false, which is WRONG.");
        }

        expression = "(10 >= 5)";
        answer = evaluate(expression);
        System.out.print("Test08: " + expression + " evaluated as ");
        if (answer == true) {
            System.out.println("true, which is correct.");
        }
        else {
            System.out.println("false, which is WRONG.");
        }

        expression = "(10 < 5)";
        answer = evaluate(expression);
        System.out.print("Test09: " + expression + " evaluated as ");
        if (answer == true) {
            System.out.println("true, which is WRONG.");
        }
        else {
            System.out.println("false, which is correct.");
        }

        expression = "(10 <= 5)";
        answer = evaluate(expression);
        System.out.print("Test10: " + expression + " evaluated as ");
        if (answer == true) {
            System.out.println("true, which is WRONG.");
        }
        else {
            System.out.println("false, which is correct.");
        }

        expression = "(10 == 5)";
        answer = evaluate(expression);
        System.out.print("Test11: " + expression + " evaluated as ");
        if (answer == true) {
            System.out.println("true, which is WRONG.");
        }
        else {
            System.out.println("false, which is correct.");
        }

        expression = "(10 != 5)";
        answer = evaluate(expression);
        System.out.print("Test12: " + expression + " evaluated as ");
        if (answer == true) {
            System.out.println("true, which is correct.");
        }
        else {
            System.out.println("false, which is WRONG.");
        }

        expression = "((10 > 5) && (10 > 15))";
        answer = evaluate(expression);
        System.out.print("Test13: " + expression + " evaluated as ");
        if (answer == true) {
            System.out.println("true, which is WRONG.");
        }
        else {
            System.out.println("false, which is correct.");
        }

        expression = "((10 > 5) || (10 > 15))";
        answer = evaluate(expression);
        System.out.print("Test14: " + expression + " evaluated as ");
        if (answer == true) {
            System.out.println("true, which is correct.");
        }
        else {
            System.out.println("false, which is WRONG.");
        }

        expression = "(((10 > 5) && (10 > 15)) || (20 > 15))";
        answer = evaluate(expression);
        System.out.print("Test15: " + expression + " evaluated as ");
        if (answer == true) {
            System.out.println("true, which is correct.");
        }
        else {
            System.out.println("false, which is WRONG.");
        }

        expression = "(((10 > 5) || (10 > 15)) && (15 >= 20))";
        answer = evaluate(expression);
        System.out.print("Test16: " + expression + " evaluated as ");
        if (answer == true) {
            System.out.println("true, which is WRONG.");
        }
        else {
            System.out.println("false, which is correct.");
        }

        expression = "(!(5<1))";
        answer = evaluate(expression);
        System.out.print("Test17: " + expression + " evaluated as ");
        if (answer == true) {
            System.out.println("true, which is correct.");
        }
        else {
            System.out.println("false, which is WRONG.");
        }

        expression = "(!(5>1))";
        answer = evaluate(expression);
        System.out.print("Test18: " + expression + " evaluated as ");
        if (answer == true) {
            System.out.println("true, which is WRONG.");
        }
        else {
            System.out.println("false, which is correct.");
        }



        answer = query(stdin, "\nWould you like to try some expressions of your own?");
        if (!answer) System.exit(0);



        System.out.println("\nPlease type a Boolean expression made from");
        System.out.println("comparison between unsigned numbers, and the");
        System.out.println("operations && (AND) and || (OR).  The ");
        System.out.println("expression must be fully parenthesized.");

        do
        {
            System.out.print("Your expression: ");
            expression = stdin.nextLine( );
            try
            {
                answer = evaluate(expression);
                System.out.println("The value is " + answer);
            }
            catch (Exception e)
            {
                System.out.println("Error." + e.toString( ));
            }
        }
        while (query(stdin, "Another string?"));

        System.out.println("All numbers are interesting.");
    }


    public static boolean query(Scanner input, String prompt)  {
        String answer;

        System.out.print(prompt + " [Y or N]: ");
        answer = input.nextLine( ).toUpperCase( );
        while (!answer.startsWith("Y") && !answer.startsWith("N"))
        {
            System.out.print("Invalid response. Please type Y or N: ");
            answer = input.nextLine( ).toUpperCase( );
        }

        return answer.startsWith("Y");
    }


    public static boolean evaluate(String s)    {
        // Precondition: The string is a fully parenthesized Boolean expression
        // formed from non-negative numbers, parentheses, comparisons, and the three
        // logical operations: !(NOT, unary), && (AND, binary), and || (OR, binary).
        // Postcondition: The string has been evaluated and the value returned.
        // Exceptions: Can throw an NumberFormatException if the expression contains
        // characters other than digits, operations, parentheses and whitespace.
        // Can throw IllegalArgumentException if the input line is an
        // illegal expression, such as unbalanced parentheses or an unknown symbol.

        //  KEY
        //****************
        //  >= G         *
        //  <= L         *
        //  != N         *
        //  || O         *
        //  && A         *
        //  == E         *
        //****************

        s = s.replace(">=","G");
        s = s.replace("<=","L");
        s = s.replace("!=","N");
        s = s.replace("||","O");
        s = s.replace("&&","A");
        s = s.replace("==","E");
        //         System.out.println(s);
        Scanner input = new Scanner(s);
        Stack<Integer> numbers = new Stack<Integer>( );
        Stack<Character> operations = new Stack<Character>( );
        Stack<Boolean> booleans = new Stack<Boolean>( );
        String next;
        char first;

        while (input.hasNext( )) {
            if (input.hasNext(UNSIGNED_INT)) {
                next = input.findInLine(UNSIGNED_INT);
                numbers.push(new Integer(next));
            }
            else {
                next = input.findInLine(CHARACTER);
                first = next.charAt(0);

                switch (first) {
                    case '>': // Greater Than
                    case 'G': // >= Greater Than or Equal to.
                    case '<': // Less Than
                    case 'L': // Less Than or Equal to.
                    case 'E': // == Equal
                    case 'N': // != Not Equal
                    case '!': // Not
                    case 'A': // && And
                    case 'O': // || Or
                        operations.push(first);
                        break;
                    case ')': // Right parenthesis
                        evaluateStackTops(numbers, operations,booleans);
                        break;
                    case '(': // Left parenthesis
                        break;
                    default : // Illegal character
                        throw new IllegalArgumentException("Illegal character");
                }
            }
        }
        if (booleans.size( ) != 1)
            throw new IllegalArgumentException("Illegal input expression" );

        return booleans.pop( );
    }


    public static boolean evalBoolean(int oper1, int oper2, char bool) {
        switch (bool) {
            case '>': // Greater Than
                return  (oper1 > oper2);
            case 'G': // >= Greater Than or Equal to.
                return (oper1 >= oper2);
            case '<': // Less Than
                return (oper1 < oper2);
            case 'L': // Less Than or Equal to.
                return (oper1 <= oper2);
            case 'E': // == Equal
                return (oper1 == oper2);
            case 'N': // != Not Equal
                return (oper1 != oper2);
            default :  // This statement should be unreachable since only the aforementioned values get sent to the method...
                throw new IllegalArgumentException("Illegal operation");
        }
    }

    public static void evaluateStackTops(Stack<Integer> numbers, Stack<Character> operations,Stack<Boolean> booleans) {
        int operand1, operand2;
        boolean bool1,bool2;
        if (operations.size( ) < 1)
            throw new IllegalArgumentException("Illegal expression (Operations Stack Underflow)");
        char oper = operations.pop();

        switch (oper) {
            case '>': // Greater Than
            case 'G': // >= Greater Than or Equal to.
            case '<': // Less Than
            case 'L': // Less Than or Equal to.
            case 'E': // == Equal
            case 'N': // != Not Equal
                if (numbers.size( ) < 2)
                    throw new IllegalArgumentException("Illegal expression (Numbers Stack Underflow)");
                operand2 = numbers.pop( );
                operand1 = numbers.pop( );
                booleans.push(evalBoolean(operand1,operand2,oper));
                break;

            case '!':
                if (booleans.size( ) < 1)
                    throw new IllegalArgumentException("Illegal expression (Booleans Stack Underflow)");
                booleans.push (!(booleans.pop( )));
                break;

            case 'A':
            case 'O':
                if (booleans.size( ) < 2)
                    throw new IllegalArgumentException("Illegal expression (Booleans Stack Underflow)");
                bool2 = booleans.pop( );
                bool1 = booleans.pop( );
                if (oper == 'A')
                    booleans.push ( (bool1 && bool2));
                else
                    booleans.push ( (bool1 || bool2));
                break;
            default :
                throw new IllegalArgumentException("Illegal operation (Unknown Character)");
        }
    }

    // These patterns are from Appendix B of Data Structures and Other Objects.
    // They may be used in hasNext and findInLine to read certain patterns
    // from a Scanner.
    public static final Pattern CHARACTER = Pattern.compile("\\S.*?");
    public static final Pattern UNSIGNED_INT = Pattern.compile("\\d+.*?");
}