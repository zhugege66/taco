package zhugege.cn.tacocloud.util;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "demo")
    public void userListener(ConsumerRecord<String,String> record){
        Log.log.info(record.topic() + record.offset() + record.value());
    }
}
