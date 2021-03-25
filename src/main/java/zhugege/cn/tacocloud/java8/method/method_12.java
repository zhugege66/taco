package zhugege.cn.tacocloud.java8.method;

import org.apache.logging.log4j.util.PropertySource;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class method_12 {

    public static void main(String[] args){

        List<Integer> numberList = Arrays.asList(2,1,4,3,5);
        List<Student> studentList = Arrays.asList(
                new Student(18,"man"),
                new Student(20,"women"),
                new Student(19,"man")
        );
        numberList.sort(Comparator.reverseOrder());
        System.out.println(numberList.toString());

        studentList.sort((a,b)->(a.age - b.age));
        for(Student s : studentList){
            System.out.println(s.toString());
        }

        List<Draw> resizableShapes = Arrays.asList(
                new Ellipse(),new Square(),new Rectangle()
        );
        Utils.paint(resizableShapes);
        resizableShapes.get(0).setRelativeSize(10,10);
        resizableShapes.get(1).setRelativeSize(11,11);
        resizableShapes.forEach(a -> {
            System.out.println("width: " + a.getWidth() + " height: " +a.getHeight());
        });
    }

}

final class Utils{

    public static void paint(List<Draw> l){

        l.forEach(a -> {
            a.setAbsoluteSize(23,25);
        });
    }

}

class Ellipse implements Draw{

    int width;
    int height;

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setWidth(int width) {

        this.width = width;
    }

    @Override
    public void setHeight(int height) {

        this.height = height;
    }

    @Override
    public void setAbsoluteSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void setRelativeSize(int width, int height) {
        System.out.println("width: " + width + " height: " + height);
        this.width = width;
        this.height = height;
    }
}

class Rectangle implements Draw{

    int width;
    int height;

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setWidth(int width) {

        this.width = width;
    }

    @Override
    public void setHeight(int height) {

        this.height = height;
    }

    @Override
    public void setAbsoluteSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

}

class Square implements Draw{

    int width;
    int height;

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setWidth(int width) {

        this.width = width;
    }

    @Override
    public void setHeight(int height) {

        this.height = height;
    }

    @Override
    public void setAbsoluteSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

}

class Student{
    int age;
    String sex;

    public Student(int age, String sex){
        this.age = age;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "this student is " + age + " and " + sex;
    }
}

interface Draw{

    int getWidth();
    int getHeight();
    void setWidth(int width);
    void setHeight(int height);
    void setAbsoluteSize(int width, int height);
    default void setRelativeSize(int width, int height){
        setAbsoluteSize(width,height);
    };
    default void setABS(){};

}
