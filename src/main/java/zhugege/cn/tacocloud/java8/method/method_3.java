package zhugege.cn.tacocloud.java8.method;

import zhugege.cn.tacocloud.java8.BufferedReaderProcessor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class method_3 {

    public static void main(String[] args)throws IOException{

        System.out.println(processFile((BufferedReader br) -> br.readLine()));
        System.out.println(processFile((BufferedReader br) -> br.readLine() + br.readLine()));
    }

    public static String processFile(BufferedReaderProcessor p) throws IOException{
        try{
            BufferedReader br = new BufferedReader(new FileReader(
                    "C:\\Users\\朱哥哥\\Desktop\\taco-cloud\\src\\main\\java\\zhugege\\cn\\tacocloud\\java8\\method\\zhugege.json"
            ));
            return p.process(br);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
