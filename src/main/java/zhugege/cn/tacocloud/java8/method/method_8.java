package zhugege.cn.tacocloud.java8.method;

import zhugege.cn.tacocloud.java8.entity.Dish;

import java.nio.file.DirectoryStream;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class method_8 {

    public static List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH) );

    public static void main(String[] args){

        Optional<Dish> maxCaloriesDish = menu.stream()
                .collect(Collectors.maxBy(Comparator.comparing(Dish::getCalories)));
        System.out.println("maxCaloriesDish " + maxCaloriesDish.get());

        String menuName = menu.stream()
//                .collect(Collectors.reducing("",Dish::getName,(a,b) -> a + b));
                .map(Dish::getName)
                .collect(Collectors.joining(", "));
        System.out.println("menuName " + menuName);

        IntSummaryStatistics intSummaryStatistics = menu.stream()
                .collect(Collectors.summarizingInt(Dish::getCalories));
        System.out.println("intSummaryStatistics " + intSummaryStatistics);

        int totalCalories = menu.stream()
                .collect(Collectors.reducing(0,Dish::getCalories,(a,b)->a+b));
        System.out.println("totalCalories " + totalCalories);

        Map<Dish.Type,List<Dish>> dishByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType,Collectors.toList()));
        System.out.println("dishByType " + dishByType);

        Map<Dish.Type,Map<CaloricLevel,List<Dish>>> dishByTypeAndCaloricLevel = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.groupingBy(dish -> {
                            if(dish.getCalories() <= 400)
                                return CaloricLevel.DIET;
                            else if(dish.getCalories() <= 700)
                                return CaloricLevel.NORMAL;
                            else
                                return CaloricLevel.FAT;
                })));
        System.out.println("dishByTypeAndCaloricLevel " + dishByTypeAndCaloricLevel);


        Optional<Dish> maxCaloricDish = menu.stream()
                .filter(d -> d.isVegetarian())
                .collect(Collectors.maxBy(Comparator.comparing(Dish::getCalories)));
        System.out.println("maxCaloricDish " + maxCaloricDish.get());

        Supplier<String> stringSupplier = String::new;
        String str = stringSupplier.get();
        str += "zhugege";
        System.out.println(str);

        Map<Boolean,List<Integer>> partionPrimes = IntStream.rangeClosed(2,100)
                .boxed()
                .collect(Collectors.groupingBy(i -> isPrime(i)));
        System.out.println("partionPrimes " + partionPrimes);
    }

    public static boolean isPrime(int number){

        int numberRoot = (int)Math.sqrt(number);
        return IntStream.rangeClosed(2,numberRoot)
                .noneMatch(i -> number % i == 0);
    }

    public enum CaloricLevel{DIET,NORMAL,FAT}
}
