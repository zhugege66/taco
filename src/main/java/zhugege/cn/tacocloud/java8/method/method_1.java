package zhugege.cn.tacocloud.java8.method;

import zhugege.cn.tacocloud.java8.AppleFormatter;
import zhugege.cn.tacocloud.java8.ApplePredicate;
import zhugege.cn.tacocloud.java8.entity.Apple;
import zhugege.cn.tacocloud.java8.impl.AppleFancyFormatter;
import zhugege.cn.tacocloud.java8.impl.ApplePredicateByColor;
import zhugege.cn.tacocloud.java8.impl.ApplePredicateByWeight;

import java.util.*;
import java.util.function.Predicate;

public class method_1 {

    public static void main(String[] args){
        Apple apple1 = new Apple("red",160);
        Apple apple2 = new Apple("greed",125);
        Apple apple3 = new Apple("red",100);
        Apple apple4 = new Apple("greed",50);
        List<Apple> appleList = new ArrayList<>();
        appleList.add(apple1);
        appleList.add(apple2);
        appleList.add(apple3);
        appleList.add(apple4);

        for(Apple apple : resultList(
                resultList(appleList,new ApplePredicateByColor()),new ApplePredicateByWeight())){
            System.out.println(apple.toString());
        }

        for(Apple apple : resultList(appleList, new ApplePredicate() {
            @Override
            public boolean predict(Apple apple) {
                if(apple.getColor().equals("greed")){
                    return true;
                }else {
                    return false;
                }
            }
        })
        ){
            System.out.println("result "+apple.toString());
        }

        List<Apple> appleList2 = resultList(appleList,(Apple apple) ->
                apple.getColor().equals("red"));
        for(Apple apple : appleList2){
            System.out.println("resultLambda " + apple);
        }

        List<Apple> appleList3 = filter(appleList,(Apple apple) ->
                apple.getColor().equals("red") && apple.getWeight() > 150);
        for(Apple apple : appleList3){
            System.out.println("resultfanxing " + apple.toString());
        }

        List<Integer> integerList = Arrays.asList(new Integer(1),new Integer(2));
        List<Integer> integerList1 = filter(integerList,(Integer i) ->
                i > 1);
        for(Integer i : integerList1){
            System.out.println("integer " + i);
        }

        appleFormatterList(appleList,new AppleFancyFormatter());

        for(Apple a : appleList){
            System.out.println(a.toString());
        }
        Collections.sort(appleList,(Apple a1,Apple a2) -> (int)(a1.getWeight()-a2.getWeight()));
        System.out.println("after sort----------");
        for(Apple a : appleList){
            System.out.println(a.toString());
        }
    }

    public static List<Apple> resultList(List<Apple> inventory, ApplePredicate p){

        List<Apple> result = new ArrayList<>();
        for(Apple apple : inventory){
            if(p.predict(apple)){
                result.add(apple);
            }
        }
        return result;
    }

    public static void appleFormatterList(List<Apple> inventory, AppleFormatter formatter){

        List<Apple> result = new ArrayList<>();
        for(Apple apple : inventory){
            System.out.println(formatter.accept(apple));
        }
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p){

        List<T> result = new ArrayList<>();
        for(T t : list){
            if(p.test(t)){
                result.add(t);
            }
        }
        return result;
    }
}
