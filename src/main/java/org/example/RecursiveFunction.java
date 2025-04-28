package org.example;

import java.math.BigInteger;

public class RecursiveFunction {
    public static BigInteger factorial(int n) {
        // Base case: factorial of 0 or 1 is 1
        if (n == 0 || n == 1) {
            return BigInteger.ONE;
        }
        // Recursive case: n! = n * (n-1)!
        return BigInteger.valueOf(n).multiply(factorial(n - 1));
    }

    public static void main(String[] args) {
        int number = 500;
        BigInteger result = factorial(number);

        System.out.println(number + "! = " + result);
    }
}
