package zhugege.cn.tacocloud.java8.method;

import java.util.HashMap;
import java.util.function.Consumer;

public class method_13 implements A,B{

    public static void main(String[] args){
        new method_13().hello();
        HashMap<String,String> map = new HashMap<>();
        map.put("1","zhugege");
        map.put("2","xiaofei");
        map.put("3","qianqian");
        forEach(map,s -> System.out.println("key:" + s + " value:" + map.get(s)));

    }

    public static <K,V> void forEach(HashMap<K,V> map, Consumer<K> consumer){

        for(K k : map.keySet()){
            consumer.accept(k);
        }
    }

    @Override
    public void hello() {
        System.out.println("Override!!");
    }
}

interface A{
    default void hello(){
        System.out.println("Hello from A");
    }
}

interface B extends A{
    default void hello(){
        System.out.println("Hello from B");
    }
}

