package com.bupt.ZigbeeResolution.data;

public class OpLog {
    public String device_type;
    public String control_type;
    public String res_status;
    public String err_msg;

    public OpLog(){}

    public OpLog(String device_type, String control_type, String error, String err_msg){
        this.device_type = device_type;
        this.control_type = control_type;
        this.res_status = error;
        this.err_msg = err_msg;
    }

    public OpLog(String device_type, String control_type, String succeed){
        this.device_type = device_type;
        this.control_type = control_type;
        this.res_status = succeed;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getControl_type() {
        return control_type;
    }

    public void setControl_type(String control_type) {
        this.control_type = control_type;
    }

    public String getRes_status() {
        return res_status;
    }

    public void setRes_status(String res_status) {
        this.res_status = res_status;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    @Override
    public String toString() {
        return "OpLog{" +
                "device_type='" + device_type + '\'' +
                ", control_type='" + control_type + '\'' +
                ", res_status='" + res_status + '\'' +
                ", err_msg='" + err_msg + '\'' +
                '}';
    }
}
