package zhugege.cn.tacocloud.entity;

import lombok.Data;

@Data
public class Ingredient {

    private Integer id;
    private String name;
    private Type type;

    public static enum Type{
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
