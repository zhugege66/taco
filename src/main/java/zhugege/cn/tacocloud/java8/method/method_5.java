package zhugege.cn.tacocloud.java8.method;

import zhugege.cn.tacocloud.java8.entity.Apple;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class method_5 {

    public static void main(String[] args){

        List<Apple> appleList = Arrays.asList(new Apple("red",123),new Apple("blue",150),
                new Apple("greed",125),new Apple("greed",123));
        System.out.println("before sort--------");
        forEach(appleList,a -> System.out.println(a.toString()));
        appleList.sort(Comparator.comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor));
        System.out.println("after sort--------");
        forEach(appleList,a -> System.out.println(a.toString()));


        System.out.println("filter--------");
        Predicate<Apple> greedApple = a -> a.getColor().equals("greed");
        Predicate<Apple> redOrGreedHeavyApple = greedApple.and(a -> a.getWeight() > 123)
                .or(a -> a.getColor().equals("red"));
        for(Apple a : appleList){
            if(redOrGreedHeavyApple.test(a)){
                System.out.println(a.toString());
            }
        }

        System.out.println("Function test------");
        Function<String,String> addHeader = Latter::addHeader;
        Function<String,String> transformationPipeline = addHeader.andThen(Latter::checkSpelling)
                .andThen(Latter::addFooter);
        String str = transformationPipeline.apply("labda");
        System.out.println(str);
    }

    public static <T> void forEach(List<T> list, Consumer<T> consumer){

        for(T t : list){
            consumer.accept(t);
        }
    }

}

class Latter{

    public static String addHeader(String text){
        return "From zhugege ,xiaofei: " + text;
    }

    public static String addFooter(String text){
        return text + " Kind regards ";
    }

    public static String checkSpelling(String text){
        return text.replace("labda","lambda");
    }
}
