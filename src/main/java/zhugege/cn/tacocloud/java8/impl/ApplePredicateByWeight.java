package zhugege.cn.tacocloud.java8.impl;

import zhugege.cn.tacocloud.java8.ApplePredicate;
import zhugege.cn.tacocloud.java8.entity.Apple;

public class ApplePredicateByWeight implements ApplePredicate {

    @Override
    public boolean predict(Apple apple) {
        if(apple.getWeight() > 150){
            return true;
        }else{
            return false;
        }
    }
}
