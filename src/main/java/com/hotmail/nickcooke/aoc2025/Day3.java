package com.hotmail.nickcooke.aoc2025;

public class Day2 extends AoCSolution {
    public static void main(String[] args) {
        Day2 day2 = new Day2();
        day2.getInput();
        day2.part1Timed();
        day2.part2Timed();
    }

    @Override
    void part1() {
        String[] productIds = inputLines.get(0).split(",");
        long invalidCount=0;
        for (String productId : productIds) {
            Range range = new Range(productId);

            System.out.println(range);
            invalidCount+=range.getCountOfInvalidProductIds();
        }
        System.out.println("Part 1: " + invalidCount);
    }

    @Override
    void part2() {
        System.out.println("Part 2: ");
    }

    static class Range {
        private ProductId start;
        private ProductId end;
        public Range(String input) {
            start = new ProductId(input.split("-")[0]);
            end = new ProductId(input.split("-")[1]);
        }

        @Override
        public String toString() {
            return start.getId() + "---" + end.getId() + " : " + getCountOfInvalidProductIds();
        }
        public long getCountOfInvalidProductIds(){
            long invalidCount=0L;
            for (long i = start.getIdAsLong(); i<=end.getIdAsLong(); i++){
                ProductId productId = new ProductId((i+" ") .trim());
                if (!productId.isValid()){
                    invalidCount += productId.getIdAsLong();
                }
            }
            return invalidCount;
        }
    }

    static class ProductId {
        private String id;

        ProductId(String productId) {
            id = productId;
        }
        boolean isValid(){
            if ((id).length()%2!=0){
                return true;
            }
            else {
                String idString = id;
                int halfLength = (idString).length()/2;
                String first = idString.substring(0,halfLength);
                String second = idString.substring(halfLength);
                return !first.equals(second);
            }
        }

//        boolean isValid() {
//            portionLoop:
//            for (int i = 1; i <= id.length() / 2; i++) {
//                if (id.length() % i != 0) {
//                    continue portionLoop;
//                } else {
//                    String idString = id;
//                    String firstPortion = idString.substring(0, i);
//                    for (int startOfNextPortion = i; startOfNextPortion < idString.length(); startOfNextPortion += i) {
//                        String nextPortion = idString.substring(startOfNextPortion, startOfNextPortion + i);
//                        if (!nextPortion.equals(firstPortion)) {
//                            continue portionLoop;
//                        }
//                    }
//                    return false;
//                }
//            }
//            return true;
//        }
        String getId() {return id;}
        long getIdAsLong(){return Long.parseLong(id);}
    }
}
