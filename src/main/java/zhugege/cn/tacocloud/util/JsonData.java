package zhugege.cn.tacocloud.util;

public class JsonData {

    private Integer code;
    private Object data;
    private String msg;

    public JsonData(){}

    public JsonData(Integer code,Object data){
        this.code = code;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public JsonData(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static JsonData buildSuccess(Integer code,Object data){
        return new JsonData(code,data);
    }

    public static JsonData buildError(Integer code,String msg){
        return new JsonData(code,msg);
    }

}
