package zhugege.cn.tacocloud.java8.method;

import zhugege.cn.tacocloud.java8.entity.Dish;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class method_6 {

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

        List<String> threedHighCaloricDishNames = menu.stream()
                .filter(c -> {
                    System.out.println("filter "+c.getName());
                    return c.getCalories() > 300;})
                .sorted(Comparator.comparing(Dish::getCalories).reversed())
                .map(c -> {
                    System.out.println("map " + c.getName());
                    return c.getName();
                })
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("threedHighCaloricDishNames---\n" + threedHighCaloricDishNames);

        System.out.println("number-------");
        List<Integer> number = Arrays.asList(1,2,3,4,5,6,2,4);
        number.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);

        System.out.println("mapTest---------");
        String[] arrayOfWords = {"Hello","World"};
        Stream<String> stringStream = Arrays.stream(arrayOfWords);
        stringStream.map(word -> word.split(""))
                .map(Arrays::stream)
                .distinct()
                .forEach(System.out::println);

        List<String> resutList = Arrays.stream(arrayOfWords)
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(resutList);

        List<Integer> number1 = Arrays.asList(1,2,3);
        List<Integer> number2 = Arrays.asList(3,4);
        List<Integer> squaresNumber1 = number1.stream()
                .map(i -> i * i)
                .collect(Collectors.toList());
        System.out.println(squaresNumber1);
        List<int []> pairs = number1.stream()
                .flatMap(i -> number2.stream()
                        .filter(j -> (i+j) % 3 == 0)
                        .map(j -> new int[] {i,j}))
                .collect(Collectors.toList());
        forEach(pairs,s -> {
            for(int i : s){
                System.out.print(i + " ");
            }
            System.out.println();
        });

        Optional<Dish> dish = menu.stream()
                .filter(Dish::isVegetarian)
                .findAny();
        dish.ifPresent(d -> System.out.println(d.getName()));

        List<Integer> numberList = Arrays.asList(1,2,3,4);
        int total = numberList.stream()
                .reduce(0,(a,b)-> a+b);
        System.out.println("total " + total);

        Optional<Integer> optionalInteger = numberList.stream().reduce(Integer::sum);
        optionalInteger.ifPresent(n -> System.out.println("total " + n));
    }

    public static <T> void forEach(List<T> list, Consumer<T> consumer){

        for(T t : list){
            consumer.accept(t);
        }
    }
}
