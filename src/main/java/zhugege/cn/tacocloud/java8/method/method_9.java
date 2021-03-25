package zhugege.cn.tacocloud.java8.method;


import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class method_9 {

    public static void main(String[] args){

        System.out.println("sequentialSum in: " +
                measureSunPerf(ParallerStreams::sequentialSum,10_000_000) + " ms");

        System.out.println("interativeSum in: " +
                measureSunPerf(ParallerStreams::interativeSum,100_000_000) + " ms");

        System.out.println("parallelSum in: " +
                measureSunPerf(ParallerStreams::parallelSum,10_000_000) + " ms");

        System.out.println("rangeSum in: " +
                measureSunPerf(ParallerStreams::rangeSum,100_000_000) + " ms");

        System.out.println("parallelRangeSum in: " +
                measureSunPerf(ParallerStreams::parallelRangeSum,100_000_000) + " ms");
    }

    public static long measureSunPerf(Function<Long,Long> addr,long n){
        long faster = Long.MAX_VALUE;
        for(int i=0;i<10;i++){
            long start = System.currentTimeMillis();
            long sum = addr.apply(n);
            long duration = System.currentTimeMillis() - start;
            System.out.println("result=" + sum);
            if(duration < faster){
                faster = duration;
            }
        }
        return faster;
    }

}

class ParallerStreams{

    public static long sequentialSum(long n){
        return Stream.iterate(1L,i -> i + 1)
                .limit(n)
                .reduce(0L,Long::sum);
    }

    public static long interativeSum(long n){
        long result = 0;
        for(long i=1L;i<=n;i++){
            result += i;
        }
        return result;
    }

    public static long parallelSum(long n){
        return Stream.iterate(1L,i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0L,(a,b) -> a+b);
    }

    public static long rangeSum(long n){
        return LongStream.rangeClosed(1,n)
                .reduce(0L,Long::sum);
    }

    public static long parallelRangeSum(long n){
        return LongStream.rangeClosed(1,n)
                .parallel()
                .reduce(0L,Long::sum);
    }
}
