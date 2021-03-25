package zhugege.cn.tacocloud.java8.method;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import zhugege.cn.tacocloud.java8.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class method_10 {
    public static void main(String[] args){

        doSomething((Task) () -> System.out.println("this is task!"));
        List<Integer> integerList = Arrays.asList(1,2,3,4);
        integerList.forEach(System.out::println);

        Pattern pattern = Pattern.compile("[0-9]*[a-z]+");
        boolean numberValidator = validate("123a",s -> pattern.matcher(s).matches());
        System.out.println("numberValidator " + numberValidator);

        Feed f = new Feed();
        f.registerObServer(s -> {
            if(s != null && s.contains("money")){
                System.out.println("Breaking news in NY! " + s);
            }
        });
        f.registerObServer(s -> {
            if(s != null && s.contains("queen")){
                System.out.println("yet another news in London... " + s);
            }
        });
        f.registerObServer(new ObServer() {
            @Override
            public void notify(String tweet) {
                if(tweet != null && tweet.contains("fushion")){
                    System.out.println("This is fushion... " + tweet);
                }
            }
        });
        f.notifyObservers("The queen said her favourite book is Java 8 in Action!");

        ProcessingObject<String> p1 = new HeaderTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckerProcessing();
        p1.setProcessor(p2);

        System.out.println(p1.handle("****Aren't labdas really sexy?!!"));


        UnaryOperator<String> headerProcessing = s -> "From Raoul,Mario and Alan: " + s;
        UnaryOperator<String> spellCheckerProcessing = s -> s.replaceAll("labda","lambda");
        Function<String,String> pipline = headerProcessing.andThen(spellCheckerProcessing);
        System.out.println(pipline.apply("####Aren't labdas really sexy?!!"));

    }

    public static void doSomething(Task t){
        t.execute();
    }

    public static void doSomething(Runnable r){
        r.run();
    }


    public static boolean validate(String s,ValidatorStrategy v){
        return v.execute(s);
    }
}

interface ValidatorStrategy{

    boolean execute(String s);

}

interface ObServer{
    void notify(String tweet);
}

interface Subject{
    void registerObServer(ObServer obServer);
    void notifyObservers(String tweet);
}

class Feed implements Subject{

    List<ObServer> list = new ArrayList<>();

    @Override
    public void registerObServer(ObServer obServer) {
        list.add(obServer);
    }

    @Override
    public void notifyObservers(String tweet) {

        list.forEach(o -> o.notify(tweet));

    }
}

abstract class ProcessingObject<T>{

    protected ProcessingObject<T> successor;

    public void setProcessor(ProcessingObject<T> successor){
        this.successor = successor;
    }

    public T handle(T input){
        T t = handleWork(input);
        if(successor != null){
            return successor.handle(t);
        }
        return t;
    }

    abstract protected T handleWork(T input);

}

class HeaderTextProcessing extends ProcessingObject<String>{

    @Override
    protected String handleWork(String input) {
        return "From Raoul,Mario and Alan: " + input;
    }
}

class SpellCheckerProcessing extends ProcessingObject<String>{

    @Override
    protected String handleWork(String input) {
        return input.replace("labda","lambda");
    }
}