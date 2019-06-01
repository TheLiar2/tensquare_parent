package entity;

/**
 * @author xiaokuli
 * @date 2019/5/22 - 14:47
 */
//统一返回对象
public class Result {

    private boolean flag; //是否成功

    private Integer code; //返回状态码

    private String message; //返回信息

    private Object data; //返回数据

    //空构造函数
    public Result() {
    }

    //增删改所需构造函数
    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    //查询所需构造函数
    public Result(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
