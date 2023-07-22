/*
в требованиях размыто описано как обрабатывать исключения, так что сделал не как в примерах
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) { //не понимаю, надо ли освобождать его или можно забить
            for (; ; ) { //можно dowhile выход сделать
                System.out.print("Input: \n");
                String input = in.nextLine();

                System.out.printf("Output: \n%s\n", calc(input));
            }
        }

    }

    static public String calc(String input){

        String[] operands = input.split(" "); //можно было бы обработать строку лучше
        if (operands.length != 3) throw new RuntimeException("Некорректный формат выражения");
        int x, y;                                   //но буду исходить из минимума
        try
        {
            x = Integer.parseInt(operands[0]);
            y = Integer.parseInt(operands[2]); //смотрим операнды
        } catch (NumberFormatException ex)
        {
            x = romanToInt(operands[0]);
            y = romanToInt(operands[2]);
            if (x != 0 & y != 0){ //мне кажется это плохо, но в голову не приходит, как сделать лучше
                return intToRoman(operate(x, y, operands[1]));
            }
            else {
                throw new RuntimeException("Некорректный формат чисел"); //мне кажется, что это плохая практика
            }                                                            //но иначе долго делать однородный фидбэк
        }
        if (x > 0 & x <= 10 & y > 0 & y <= 10) {
            return Integer.toString(operate(x, y, operands[1]));
        }
        else{
            throw new RuntimeException("Вводимые числа in range (1,10)");
        }

    }

    static public int operate(int x, int y, String operator) {
        return switch (operator) {
            case "+" -> x + y; //свитч из 12 джавы крутой, лямбда в деле, но есть вопросы по работе
            case "-" -> x - y;
            case "/" -> x / y; //остаток отбрасывается сам
            case "*" -> x * y;
            default -> throw new RuntimeException("Некорректный оператор");
        };
    }

    static public int romanToInt(String s){ //можно и масштабировать алгоритмом,
        return switch (s) {                 //но не вижу необходимости тратить время для 10 чисел
            case "X" -> 10;
            case "IX" -> 9;
            case "VIII" -> 8;
            case "VII" -> 7;
            case "VI" -> 6;
            case "V" -> 5;
            case "IV" -> 4;
            case "III" -> 3;
            case "II" -> 2;
            case "I" -> 1;
            default -> 0;
        };
    }
    static public String intToRoman(int s){
        String[] ones = {"","I","II","III","IV","V","VI","VII","VIII","IX"};
        String[] tens = {"","X","XX","XXX","XL","L","LX","LXX","LXXX","XC"};
        String[] hundreds = {"","C"}; //возможно получится как-то загнать в десятки,
                                      //это надо проверять
        return  hundreds[(s%1000)/100] + tens[(s%100)/10] + ones[s%10];
    }
}
