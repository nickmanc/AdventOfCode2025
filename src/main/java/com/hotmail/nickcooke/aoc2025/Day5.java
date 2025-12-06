package com.hotmail.nickcooke.aoc2025;

public class Day4 extends AoCSolution {
    public static void main(String[] args) {
        Day4 day4 = new Day4();
        day4.getInput();
        day4.part1Timed();
        day4.part2Timed();
    }

    @Override
    void part1() {
        Grid grid = initialiseGrid();
        System.out.println("Part 1: " + grid.countAccessiblePositions());
        if (grid.countAccessiblePositions() != 1551) throw new AoCWrongAnswerException();
    }

    @Override
    void part2() {
        Grid grid = initialiseGrid();
        int totalRemovedRolls = 0;
        int removedRolls;
        do {
            removedRolls = grid.removeAccessiblePositions();
            totalRemovedRolls += removedRolls;
        } while (removedRolls > 0);
        System.out.println("Part 2: " + totalRemovedRolls);
        if (totalRemovedRolls != 9784) throw new AoCWrongAnswerException();
    }

    private Grid initialiseGrid() {
        //assumes grid is square, which isn't in the spec but example and my input are
        int dimensions = inputLines.get(0).length();
        Grid grid = new Grid(dimensions);
        int y = 0;
        for (String line : inputLines) {
            char[] characters = line.toCharArray();
            for (int x = 0; x < dimensions; x++) {
                Position position = new Position(x, y, characters[x]);
                grid.addPosition(position);
            }
            y++;
        }
        return grid;
    }


    static class Position {
        private final int x;
        private final int y;
        private char value;

        public Position(int x, int y, char value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public char getValue() {
            return value;
        }

        public void setValue(char value) {
            this.value = value;
        }

        public boolean hasRoll() {
            return value == '@';
        }
    }

    static class Grid {
        Position[][] grid;
        int dimensions;

        public Grid(int dimensions) {
            grid = new Position[dimensions][dimensions];
            this.dimensions = dimensions;
        }

        void addPosition(Position position) {
            grid[position.getX()][position.getY()] = position;
        }

        void printGrid() {
            for (int y = 0; y < grid.length; y++) {
                for (int x = 0; x < grid[y].length; x++) {
                    char printableChar = grid[x][y].hasRoll() ? getAdjacentRollCount(x, y) < 4 ? 'A' : '@' : '.';
                    System.out.print(printableChar);
                }
                System.out.println();
            }
        }

        int countAccessiblePositions() {
            int accessiblePositionsCount = 0;
            for (int y = 0; y < grid.length; y++) {
                for (int x = 0; x < grid[y].length; x++) {
                    if (grid[x][y].hasRoll()) {
                        if (getAdjacentRollCount(x, y) < 4) {
                            accessiblePositionsCount++;
                        }
                    }
                }
            }
            return accessiblePositionsCount;
        }

        int removeAccessiblePositions() {
            int removedCount = 0;
            for (int y = 0; y < grid.length; y++) {
                for (int x = 0; x < grid[y].length; x++) {
                    if (grid[x][y].hasRoll()) {
                        if (getAdjacentRollCount(x, y) < 4) {
                            grid[x][y].setValue('.');
                            removedCount++;
                        }
                    }
                }
            }
            return removedCount;
        }

        int getAdjacentRollCount(int x, int y) {
            int rollCount = 0;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if ((x + i) >= 0 && (y + j) >= 0 && (x + i) < dimensions && (y + j) < dimensions && !(i == 0 && j == 0) && grid[x + i][y + j].hasRoll()) {
                        rollCount++;
                    }
                }

            }
            return rollCount;
        }
    }
}
