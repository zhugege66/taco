package zhugege.cn.tacocloud.java8.impl;

import zhugege.cn.tacocloud.java8.ApplePredicate;
import zhugege.cn.tacocloud.java8.entity.Apple;

public class ApplePredicateByColor implements ApplePredicate {

    @Override
    public boolean predict(Apple apple) {
        if(apple.getColor().equals("red")){
            return true;
        }else{
            return false;
        }
    }
}
