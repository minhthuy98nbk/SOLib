package zingplay;

class SOKafkaItemMsg {
    public String action;
    public String msg;

    SOKafkaItemMsg(String action, String msg) {
        this.action = action;
        this.msg = msg;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
