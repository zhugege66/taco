package zhugege.cn.tacocloud.java8.method;

import zhugege.cn.tacocloud.java8.entity.Trader;
import zhugege.cn.tacocloud.java8.entity.Transaction;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class method_7 {

    public static void main(String[] args){

        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        List<Transaction> transactionsBefore2011SortByValue = transactions.stream()
                .filter(d -> d.getYear()==2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        System.out.println("transactionsBefore2011SortByValue " + transactionsBefore2011SortByValue);

        List<String> listCity = transactions.stream()
                .map(d -> d.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
        System.out.println("listCity " +listCity);

        List<Trader> traderListFromCambridgeSortByName = transactions.stream()
                .map(d -> d.getTrader())
                .filter(d -> d.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.println("traderListFromCambridgeSortByName "  + traderListFromCambridgeSortByName);


        List<String> stringNameListSortByChar = transactions.stream()
                .map(n -> n.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("stringNameListSortByChar " + stringNameListSortByChar);

        boolean traderOptionalWorkInMilan = transactions.stream()
                .anyMatch(d -> d.getTrader().getCity().equals("Milan"));
        System.out.println("traderOptionalWorkInMilan " + traderOptionalWorkInMilan);

        long totalValueWorkInCambridge = transactions.stream()
                .filter(d -> d.getTrader().getCity().equals("Cambridge"))
                .map(n -> n.getValue())
                .reduce(0,(a,b) -> a + b);
        System.out.println("totalValueWorkInCambridge " + totalValueWorkInCambridge);

        Optional<Integer> maxValue = transactions.stream()
                .map(n -> n.getValue())
                .reduce(Integer::max);
        System.out.println("maxValue " + maxValue.get());

        Optional<Transaction> minTransaction = transactions.stream()
                .reduce((a,b) -> a.getValue() < b.getValue() ? a : b);
        System.out.println("minTransaction " + minTransaction.get().toString());


        Stream<double []> GouGUNumber = IntStream.rangeClosed(1,100)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a,100)
                    .mapToObj(b -> new double[]{a,b,Math.sqrt(a * a + b * b)})
                    .filter(t -> t[2] % 1 == 0));
        GouGUNumber.forEach(s -> System.out.println(s[0] + " " + s[1] + " " +s[2]) );


        int[] numbers = new int[]{1,2,3,4,5};
        System.out.println(Arrays.stream(numbers).sum());

        long uniqueWords = 0;
        try(Stream<String> lines = Files.lines(Paths.get("C:\\Users\\朱哥哥\\Desktop\\taco-cl" +
                "oud\\src\\main\\java\\zhugege\\cn\\tacocloud\\java8\\method\\zhugege.json"), Charset.defaultCharset())){
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
            System.out.println(uniqueWords);
        }catch (Exception e){
            e.printStackTrace();
        }

        Stream.iterate(new int[]{0,1},a -> new int[]{a[1],a[0]+a[1]})
                .limit(10)
                .forEach(s -> System.out.println("(" + s[0] + "," + s[1] + ")"));


    }

}
