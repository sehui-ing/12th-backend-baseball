package baseball;

import mallang.missionutils.Console;
import mallang.missionutils.Randoms;

import java.util.ArrayList;
import java.util.List;

public class Application {

    public static final String END_GAME = "2";

    //TODO: 숫자 야구 게임 구현
    public static void main(String[] args) {
        while (true) {
            startGame();
            System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
            String choice = Console.readLine();
            if (choice.equals(END_GAME)) {
                break;
            }
        }
    }

    private static void startGame() {
        List<Integer> answer = generateRandomNumber();
        boolean isGameOver = false;

        while (!isGameOver) {
            int[] guess = getUserInput();
            String result = calculateResult(answer, guess);

            System.out.println(result);

            if (result.equals("3스트라이크")) {
                System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
                isGameOver = true;
            }
        }
    }

    private static List<Integer> generateRandomNumber() {
        List<Integer> numbers = new ArrayList<>();
        boolean[] usedNumber = new boolean[10];
        int tmp;
        for (int i = 0; i < 3; i++) {
            do {
                tmp = Randoms.pickNumberInRange(1, 9);
            } while (usedNumber[tmp]);
            numbers.add(tmp);
            usedNumber[tmp] = true;
        }
        return numbers;
    }

    private static int[] getUserInput() {
        System.out.println("숫자를 입력해주세요 : ");
        String input = Console.readLine();
        validateInput(input);
        return convertInputToArray(input);
    }

    private static void validateInput(String input) {
        if (!input.matches("\\d{3}")) {
            throw new IllegalArgumentException("3자리의 숫자를 입력하세요.");
        }
    }

    private static int[] convertInputToArray(String input) {
        int[] result = new int[3];
        for (int i = 0; i < 3; i++) {
            result[i] = input.charAt(i) - '0';
        }
        return result;
    }

    private static String calculateResult(List<Integer> answer, int[] guess) {
        int strike = countStrike(answer, guess);
        int ball = countBall(answer, guess);

        if (strike == 3) {
            return "3스트라이크";
        }

        StringBuilder result = new StringBuilder();
        appendStrikeCount(result, strike);
        appendBallCount(result, strike);
        appendNothing(result, strike, ball);

        return result.toString();
    }

    private static int countStrike(List<Integer> answer, int[] guess) {
        int strikeCount = 0;
        for (int i = 0; i < 3; i++) {
            if (answer.get(i).equals(guess[i])) {
                strikeCount++;
            }
        }
        return strikeCount;
    }

    private static int countBall(List<Integer> answer, int[] guess) {
        int ballCount = 0;
        for (int i = 0; i < 3; i++) {
            if (!answer.get(i).equals(guess[i]) && answer.contains(guess[i]))
                ballCount++;
        }
        return ballCount;
    }

    private static void appendStrikeCount(StringBuilder result, int strike) {
        if (strike > 0) {
            result.append(strike).append("스트라이크 ");
        }
    }

    private static void appendBallCount(StringBuilder result, int ball) {
        if (ball > 0) {
            result.append(ball).append("스트라이크 ");
        }
    }

    private static void appendNothing(StringBuilder result, int strike, int ball) {
        if (strike == 0 && ball == 0) {
            result.append("낫싱 ");
        }
    }
}
