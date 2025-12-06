package com.hotmail.nickcooke.aoc2025;

public class Day1 extends AoCSolution {
    public static void main(String[] args) {
        Day1 day1 = new Day1();
        day1.getInput();
        day1.part1Timed();
        day1.part2Timed();
    }

    @Override
    void part1() {
        int currentNumber = 50;
        int hitZero = 0;
        for (String line : inputLines) {
            int direction = line.charAt(0) == 'L' ? -1 : 1;
            int clicks = Integer.parseInt(line.substring(1));
            currentNumber += (clicks * direction);
            if (currentNumber % 100 == 0) {
                hitZero++;
            }
        }
        System.out.println("Hit Zero: " + hitZero);
    }

    @Override
    void part2() {
        Dial dial = new Dial(50);
        for (String line : inputLines) {
            int clicks = Integer.parseInt(line.substring(1));
            if (line.charAt(0) == 'L') {
                dial.turnAntiClockwise(clicks);
            }
            else
            {
                dial.turnClockwise(clicks);
            }
        }
        System.out.println("Hit Zero: " + dial.getHitZero());
    }

    private static class Dial {
        private int currentValue;
        private int hitZero = 0;

        Dial(int currentValue) {
            this.currentValue = currentValue;
        }

        void turnClockwise(int clicks) {
            for (int i = 0; i < clicks; i++) {
                currentValue = currentValue + 1;
                if (currentValue == 100) {
                    currentValue = 0;
                    hitZero++;
                }
            }
        }

        void turnAntiClockwise(int clicks) {
            for (int i = 0; i < clicks; i++) {
                currentValue = currentValue - 1;
                if (currentValue == -1) {
                    currentValue = 99;
                } else if (currentValue == 0) {
                    hitZero++;
                }
            }
        }

        int getCurrentValue() {
            return currentValue;
        }

        int getHitZero() {
            return hitZero;
        }
    }
}
