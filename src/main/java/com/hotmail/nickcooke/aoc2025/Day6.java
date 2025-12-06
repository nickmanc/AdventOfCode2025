package com.hotmail.nickcooke.aoc2025;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Day5 extends AoCSolution {
    public static void main(String[] args) {
        Day5 day5 = new Day5();
        day5.getInput();
        day5.part1Timed();
        day5.part2Timed();
    }

    @Override
    void part1() {
        boolean inRanges = true;
        List<Range> freshIngredientRanges = new ArrayList<>();
        List<Long> ingredients = new ArrayList<>();
        for (String line : inputLines) {
            if (line.length() == 0) {
                inRanges = false;
                continue;
            }
            if (inRanges) {
                Range range = new Range(Long.parseLong(line.split("-")[0]), Long.parseLong(line.split("-")[1]));
                freshIngredientRanges.add(range);
            } else {
                ingredients.add(Long.parseLong(line));
            }
        }
        int freshIngredientCount = 0;
        ingredientloop:
        for (Long ingredient : ingredients) {
            for (Range range : freshIngredientRanges) {
                if (range.inRange(ingredient)) {
                    freshIngredientCount++;
                    continue ingredientloop;
                }
            }
        }
        System.out.println("Part 1: " + freshIngredientCount);
        if (freshIngredientCount != 735) throw new AoCWrongAnswerException();
    }

    @Override
    void part2() {
        List<Range> freshIngredientRanges = new ArrayList<>();
        for (String line : inputLines) {
            if (line.length() == 0) {
                break;
            }
            Range range = new Range(Long.parseLong(line.split("-")[0]), Long.parseLong(line.split("-")[1]));
            freshIngredientRanges.add(range);
        }
        long freshIngredientCount = 0;
        Collections.sort(freshIngredientRanges);
        freshIngredientCount = 0;
        for (Range range : freshIngredientRanges) {
            for (Range innerRange : freshIngredientRanges) {

                if (!innerRange.isDeleted() && !range.isDeleted()) {
//                    System.out.println("Comparing " + range + " with " + innerRange);
                    if (range.equals(innerRange)) {
//                        System.out.println("same object, skipping");
                    } else if (range.encloses(innerRange)) {
//                        System.out.println(range + " encloses " + innerRange + ", removing " + innerRange);
                        innerRange.delete();
                    } else if (range.overlaps(innerRange)) {
//                        System.out.println(range + " overlaps " + innerRange);
                        range.combineWith(innerRange);
//                        System.out.println("Range is now " + range + ", removing " + innerRange);
                        innerRange.delete();
                    }
                }

            }
        }

        for (Range range : freshIngredientRanges) {
            if (!range.isDeleted()) {
                freshIngredientCount += range.getSize();
            }

        }
        System.out.println("Part 2: " + freshIngredientCount);
        if (freshIngredientCount != 344306344403172L) throw new AoCWrongAnswerException();
    }

    static class Range implements Cloneable, Comparable<Range> {
        long from;
        long to;
        boolean deleted = false;

        public Range(long from, long to) {
            this.from = from;
            this.to = to;
        }

        public void delete() {
            deleted = true;
        }

        public long getFrom() {
            return from;
        }

        public void setFrom(long from) {
            this.from = from;
        }

        @Override
        public String toString() {
            if (deleted) {
                return "Range{}";
            }
            return "Range{" + "from=" + from + ", to=" + to + '}';
        }

        public long getTo() {
            return to;
        }

        public void setTo(long to) {
            this.to = to;
        }

        public boolean inRange(Long ingredient) {
            return ingredient >= from && ingredient <= to;
        }

        public boolean overlaps(Range other) {
            return inRange(other.from) || inRange(other.to);
        }

        public boolean encloses(Range other) {
            return from <= other.from && to >= other.to;
        }

        public Long getSize() {
            return deleted ? 0 : to - from + 1;
        }

        @Override
        public Range clone() {
            try {
                Range clone = (Range) super.clone();
                clone.from = from;
                clone.to = to;
                return clone;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void combineWith(Range innerRange) {
            from = Math.min(from, innerRange.getFrom());
            to = Math.max(to, innerRange.getTo());
        }

        @Override
        public int compareTo(Range o) {
            return Long.compare(from, o.from);
        }
    }
}
