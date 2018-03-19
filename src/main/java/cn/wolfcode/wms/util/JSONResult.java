package cn.wolfcode.wms.util;

public class JSONResult {
    private boolean success = true;
    private String msg;

    public JSONResult() {
    }

    public void mark(String msg) {
        this.success = false;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getMsg() {
        return this.msg;
    }
}
