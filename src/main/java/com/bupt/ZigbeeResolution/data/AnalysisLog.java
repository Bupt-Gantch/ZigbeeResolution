package com.bupt.ZigbeeResolution.data;

public class AnalysisLog {
    public String gateway;
    public String service;
    public byte res_type;

    public AnalysisLog(){}

    public AnalysisLog(String gatewayName, String serviceName, byte resType){
        this.gateway = gatewayName;
        this.service = serviceName;
        this.res_type = resType;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public byte getRes_type() {
        return res_type;
    }

    public void setRes_type(byte res_type) {
        this.res_type = res_type;
    }

    @Override
    public String toString() {
        return "AnalysisLog{" +
                "gateway='" + gateway + '\'' +
                ", service='" + service + '\'' +
                ", res_type=" + res_type +
                '}';
    }
}
