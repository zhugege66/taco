package zhugege.cn.tacocloud.java8.method;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class method_15 {

    static List<Shop> shopList;

    public static void main(String[] args){

        Shop shop1 = new Shop("Best Shop");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop1.getPriceAsync("rice");
        long invocationTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("invocation return after " + invocationTime + " ms");
        try {
            double price = futurePrice.get(1,TimeUnit.SECONDS);
            System.out.printf("Price is %.2f%n", price);
        }catch (Exception e){
            e.printStackTrace();
        }
        long retrievalTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("price return after " + retrievalTime + " ms");

        shopList = Arrays.asList(new Shop("Best Price"),
                new Shop("LetsSaveBig"),
                new Shop("MyFavoriteShop"),
                new Shop("BuyItAll"),
                new Shop("test1"),
                new Shop("test2"),
                new Shop("test3"),
                new Shop("test4"),
                new Shop("test5"),
                new Shop("test6"));

        start = System.nanoTime();
        findPriceStream("iphone").forEach(System.out::println);
        long duringTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in findPriceStream " + duringTime + " ms");

        start = System.nanoTime();
        findPriceParallelStream("iphone").forEach(System.out::println);
        duringTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in findPriceParallelStream " + duringTime + " ms");

        start = System.nanoTime();
        findPriceFuture("iphone").forEach(System.out::println);
        duringTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in findPriceFuture " + duringTime + " ms");
    }

    public static List<String> findPriceStream(String product){
        return shopList.stream()
                .map(shop -> String.format("%s price is %.2f",shop.name,shop.calculatePrice(product)))
                .collect(Collectors.toList());
    }

    public static List<String> findPriceParallelStream(String product){

        return shopList.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.name, shop.calculatePrice(product)))
                .collect(Collectors.toList());
    }

    public static List<String> findPriceFuture(String product){

        final Executor executor = Executors.newFixedThreadPool(Math.min(shopList.size(), 100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });

        List<CompletableFuture<String>> futurePrice = shopList.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.name + "price is " + shop.calculatePrice(product),executor))
                .collect(Collectors.toList());

        return futurePrice.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

}

class Shop{

    String name;

    public Shop(String name){
        this.name = name;
    }

    public Future<Double> getPriceAsync(String product){
        CompletableFuture<Double> future = new CompletableFuture<>();
        new Thread(() -> {
            try{
                double price = calculatePrice(product);
                future.complete(price);
            }catch (Exception e){
                future.completeExceptionally(e);
            }

        }).start();
        return future;
    }

    public double calculatePrice(String product){
        delay(1);
        return new Random().nextDouble() * product.charAt(0) + product.charAt(0);
    }

    public void delay(int second){

        try {
            Thread.sleep(second*1000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}





class Discount{

    enum Code{
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);
        
        private final int percentage;

        Code(int percentage){
            this.percentage = percentage;
        }
    }

    public static double apply(double price, )

}