package zhugege.cn.tacocloud.java8.method;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class method_14 {

    public static void main(String[] args){

        List<Integer> number1 = Arrays.asList(1,2,3);
        List<Integer> number2 = Arrays.asList(2,3,4);
        List<int[]> result = number1.stream()
                .flatMap(i -> number2.stream()
                .filter(j -> (i+j) % 2 == 0)
                .map(j -> new int[]{i,j}))
                .collect(Collectors.toList());
        result.forEach(a -> System.out.println("i " + a[0] + " j " + a[1]));

        Map<Integer, Integer> map = new HashMap<>();
        int i = 0;
        Optional<Integer> opInteger = Optional.ofNullable(map.get(i));
        System.out.println(opInteger.orElse(-1));
    }

    public static String getCarInsuranceName(Optional<Person> person){

        return person.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("UnKnown");
    }

    public static Insurance findCheapestInsurance(Person person,Car car){
        return new Insurance();
    }

    public static Optional<Insurance> nullSafeFindCheapestInsurance(
            Optional<Person> person,Optional<Car> car){
        return person.flatMap(p -> car.map(c -> findCheapestInsurance(p,c)));
    }

    public static String getCarInsuranceName(Optional<Person> person,int minAge){

        return person.filter(p -> p.getAge() >= minAge)
                .flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("UnKnown");
    }
}

class Insurance{

    private String name;

    public String getName() {
        return name;
    }
}

class Car{

    private Optional<Insurance> insurance;

    public Optional<Insurance> getInsurance() {
        return insurance;
    }
}

class Person{

    private Optional<Car> car;

    private int age;

    public Optional<Car> getCar(){
        return car;
    }

    public int getAge(){
        return age;
    }
}
