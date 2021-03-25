package zhugege.cn.tacocloud.java8.impl;

import zhugege.cn.tacocloud.java8.AppleFormatter;
import zhugege.cn.tacocloud.java8.entity.Apple;

public class AppleFancyFormatter implements AppleFormatter {

    @Override
    public String accept(Apple a) {
        String appleWeight = a.getWeight() > 150 ? "heavy" : "light";
        return "A " + appleWeight + " " + a.getColor() + " apple";
    }
}
