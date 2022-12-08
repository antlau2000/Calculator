import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static String calc(String input) throws Exception {
        String[] array = input.split(" ");
        if (array.length != 3) {
            throw new Exception("Incorrect input format. Expected 2 numbers and 1 arithmetic sign");
        }

        String arithmeticSign = array[1];
        String number1 = array[0];
        String number2 = array[2];

        if (!isArithmeticSign(arithmeticSign)) {
            throw new Exception("Incorrect arithmetic sign");
        } else if (isArabicNumeral(number1) && isArabicNumeral(number2)) {
            int arabicNumber1 = Integer.parseInt(number1);
            int arabicNumber2 = Integer.parseInt(number2);
            return "" + arithmeticOperation(arabicNumber1, arabicNumber2, arithmeticSign);
        } else if (isRomanNumeral(number1) && isRomanNumeral(number2)) {
            int romanNumber1 = convertRomanToInt(number1);
            int romanNumber2 = convertRomanToInt(number2);
            int result = arithmeticOperation(romanNumber1, romanNumber2, arithmeticSign);
            if (result < 1) {
                throw new Exception("Roman numerals can only be positive");
            }
            return convertIntToRoman(result);
        } else if (isArabicNumeral(number1) && isRomanNumeral(number2) ||
                isArabicNumeral(number2) && isRomanNumeral(number1)) {
            throw new Exception("Roman and arabic numerals are incompatible");
        } else {
            throw new Exception("Incorrect input format");
        }
    }

    private static boolean isArithmeticSign(String sign) {
        if (sign.length() != 1) {
            return false;
        }
        char c = sign.charAt(0);
        return c == '*' || c == '/' || c == '+' || c == '-';
    }
    private static boolean isRomanNumeral(String number) {
        String regex = "^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";
        Pattern p = Pattern.compile(regex);
        if (number == null) {
            return false;
        }
        Matcher m = p.matcher(number);
        return m.matches();
    }
    private static boolean isArabicNumeral(String number) {
        if (number.length() < 1 || number.length() > 2) {
            return false;
        }
        for (char c : number.toCharArray()) {
            if (c < '1' || c > '9') {
                return false;
            }
        }
        return true;
    }
    private static int arithmeticOperation(int number1, int number2, String arithmeticSign) throws Exception {
        if (number1 > 0 && number1 <= 10 && number2 > 0 && number2 <= 10) {
            switch (arithmeticSign) {
                case "+":
                    return number1 + number2;
                case "-":
                    return number1 - number2;
                case "*":
                    return number1 * number2;
                case "/":
                    return number1 / number2;
                default:
                    throw new Exception("Incorrect arithmetic sign");
            }
        } else {
            throw new Exception("Invalid number(s)");
        }
    }
    private static String convertIntToRoman(int num) {
        String[] m = { "", "M", "MM", "MMM" };
        String[] c = { "",  "C",  "CC",  "CCC",  "CD",
                "D", "DC", "DCC", "DCCC", "CM" };
        String[] x = { "",  "X",  "XX",  "XXX",  "XL",
                "L", "LX", "LXX", "LXXX", "XC" };
        String[] i = { "",  "I",  "II",  "III",  "IV",
                "V", "VI", "VII", "VIII", "IX" };

        String thousands = m[num / 1000];
        String hundreds = c[(num % 1000) / 100];
        String tens = x[(num % 100) / 10];
        String ones = i[num % 10];

        return thousands + hundreds + tens + ones;
    }
    private static int convertRomanToInt(String s) {
        int total = 0;
        for (int i = 0; i < s.length(); i++)
        {
            int s1 = value(s.charAt(i));
            if (i + 1 < s.length()) {
                int s2 = value(s.charAt(i + 1));
                if (s1 >= s2) {
                    total += s1;
                } else {
                    total -= s1;
                }
            } else {
                total += s1;
            }
        }
        return total;
    }
    private static int value(char c) {
        if (c == 'I')
            return 1;
        if (c == 'V')
            return 5;
        if (c == 'X')
            return 10;
        if (c == 'L')
            return 50;
        if (c == 'C')
            return 100;
        if (c == 'D')
            return 500;
        if (c == 'M')
            return 1000;
        return -1;
    }
}
