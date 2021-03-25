package zhugege.cn.tacocloud.java8.method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class method_11 {


    final static Map<String, Supplier<Product>> productMap = new HashMap<>();
    static int a = 1;

    public static void main(String[] args){

        Loan loan = (Loan) ProductFactory.createProduct("loan");

        System.out.println("After map ---------------");

        System.out.println(a++);

        productMap.put("loan",Loan::new);
        productMap.put("stock",Stock::new);
        productMap.put("bond",Bond::new);

        Stock stock = (Stock) creatProduct("stock");


    }

    public static Product creatProduct(String name){
        Supplier<Product> productSupplier = productMap.get(name);
        if(productSupplier != null) return productSupplier.get();
        throw  new RuntimeException("No such Product " + name);
    }
}

class ProductFactory{

    public static Product createProduct(String name){

        switch (name){
            case "loan" : return new Loan();
            case "stock" : return new Stock();
            case "bond" : return new Bond();
            default : throw new RuntimeException("No such product " + name);
        }

    }


}


class Product{

    public Product(){
        System.out.println("This is Product!");
    }
}

class Loan extends Product{

    public Loan(){
        System.out.println("This is Loan!");
    }

}

class Stock extends Product{

    public Stock(){
        System.out.println("This is Stock!");
    }

}

class Bond extends Product{

    public Bond(){
        System.out.println("This is Bond!");
    }

}