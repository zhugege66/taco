package zhugege.cn.tacocloud.java8.method;

import zhugege.cn.tacocloud.java8.entity.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;

public class method_4 {

    public static  void main(String[] args){

        Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
        List<String> stringListSource = Arrays.asList(new String("abcf"),new String(""),
                new String("123"));
        forEach(stringListSource,(String s) -> System.out.println(s));

        List<String> stringList = filter(stringListSource,nonEmptyStringPredicate);

        forEach(stringList,(String s)-> System.out.println(s));

        /**
         * 函数式接口测试Function
         */
        List<Integer> integerList = stringToInt(stringList,(String s) -> s.length());
        forEach(integerList,(Integer i) -> System.out.println(i));
        

        Supplier<String> s = () -> new String("zhugege");
        System.out.println(s.get());

        final int number;
        number = 9;
        Runnable r = () -> System.out.println(number);
        r.run();

        List<String> list = Arrays.asList("a","S","A","s");
        list.sort(String::compareToIgnoreCase);
        forEach(list,ss -> System.out.print(ss + " "));

        Supplier<Apple> supplier = Apple::new;
        Apple a1 = supplier.get();
        System.out.println(a1.toString());

        BiFunction<String,Integer,Apple> biFunction = Apple::new;
        Apple a2 = biFunction.apply("blue",120);
        System.out.println(a2.toString());
    }

    public static <T> List<T> filter(List<T> list,Predicate<T> predicate){
        List<T> result = new ArrayList<>();
        for(T s : list){
            if(predicate.test(s)){
                result.add(s);
            }
        }
        return result;
    }

    public static <T> void forEach(List<T> list, Consumer<T> consumer){
        for(T t : list){
            consumer.accept(t);
        }
    }

    public static <T,R> List<R> stringToInt(List<T> list, Function<T,R> function){
        List<R> result = new ArrayList<>();
        for(T t : list){
            result.add(function.apply(t));
        }
        return result;
    }

}
