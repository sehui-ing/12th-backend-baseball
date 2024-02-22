package baseball;

import mallang.missionutils.Console;
import mallang.missionutils.Randoms;

public class Application {

    //TODO: 숫자 야구 게임 구현
    public static void main(String[] args) {
        while (true) {
            startGame();
            System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
            String choice = Console.readLine();
            if (choice.equals("2")) {  // 1은 게임 재시작, 2는 게임 종료
                break;
            }
        }
    }

    private static void startGame() {
        int[] answer = generateRandomNumber();
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

    private static int[] generateRandomNumber() {
        int[] number = new int[3];
        boolean[] usedNumber = new boolean[10];
        int tmp;
        for (int i = 0; i < 3; i++) {
            do {
                tmp = Randoms.pickNumberInRange(1, 9);
            } while (usedNumber[tmp]);
            number[i] = tmp;
            usedNumber[tmp] = true;
        }
        return number;
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

    private static String calculateResult(int[] answer, int[] guess) {
        int strike = 0;
        int ball = 0;

        for (int i = 0; i < 3; i++) {
            if (answer[i] == guess[i]) {
                strike++;
            } else if (contains(answer, guess[i])) {
                ball++;
            }
        }

        if (strike == 3) {
            return "3스트라이크";
        }

        String result = "";
        if (strike > 0) {
            result += strike + "스트라이크 ";
        }
        if (ball > 0) {
            result += ball + "볼";
        }
        if (strike == 0 && ball == 0) {
            result = "낫싱";
        }

        return result;
    }

    private static boolean contains(int[] array, int target) {
        for (int value : array) {
            if (value == target) {
                return true;
            }
        }
        return false;
    }
}
