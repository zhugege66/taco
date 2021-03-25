package zhugege.cn.tacocloud.java8.method;

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

        shopList = Arrays.asList(
                new Shop("test1"),
                new Shop("test2"),
                new Shop("test3"),
                new Shop("test4"),
                new Shop("test5"));

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

        start = System.nanoTime();
        findPriceBase("iphone").forEach(System.out::println);
        duringTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in findPriceBase " + duringTime + " ms");

        start = System.nanoTime();
        findPriceBase2("iphone").forEach(System.out::println);
        duringTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in findPriceBase2 " + duringTime + " ms");
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

    public static Executor getExecutor(){

        final Executor executor = Executors.newFixedThreadPool(Math.min(shopList.size(), 100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });
        return executor;
    }

    public static List<String> findPriceFuture(String product){

        List<CompletableFuture<String>> futurePrice = shopList.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.name + "price is " + shop.calculatePrice(product),getExecutor()))
                .collect(Collectors.toList());

        return futurePrice.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public static List<String> findPriceBase(String product){
        return shopList.stream()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    public static List<String> findPriceBase2(String product){
        List<CompletableFuture<String>> futurePrice = shopList.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product),getExecutor()))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(
                        quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote),getExecutor())))
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

    public String getPrice(String product){

        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[
                new Random().nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s",name,price,code);

    }

    public double calculatePrice(String product){
        delay(1);
        return new Random().nextDouble() * product.charAt(0) + product.charAt(0);
    }

    public static void delay(int second){

        try {
            Thread.sleep(second*1000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class Quote{

    private final String shopName;
    private final double price;
    private final Discount.Code discountCode;

    public Quote(String shopName, double price, Discount.Code discountCode){
        this.shopName = shopName;
        this.price = price;
        this.discountCode = discountCode;
    }

    public static Quote parse(String s){
        String[] stringParse = s.split(":");
        String shopName = stringParse[0];
        double price = Double.parseDouble(stringParse[1]);
        Discount.Code discountCode = Discount.Code.valueOf(stringParse[2]);
        return new Quote(shopName,price,discountCode);
    }

    public String getShopName() {
        return shopName;
    }

    public double getPrice() {
        return price;
    }

    public Discount.Code getDiscountCode() {
        return discountCode;
    }
}



class Discount{

    public enum Code{
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);
        
        private final int percentage;

        Code(int percentage){
            this.percentage = percentage;
        }
    }

    public static String applyDiscount(Quote quote){
        return quote.getShopName() + " price is " + apply(quote.getPrice(),quote.getDiscountCode());
    }

    public static double apply(double price, Code code){
        Shop.delay(1);
        return price * (100 - code.percentage) / 100;
    }

}