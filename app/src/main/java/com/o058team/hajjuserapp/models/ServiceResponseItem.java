package com.o058team.hajjuserapp.models;

/**
 * Created by Mekawy on 02/08/2018 AD.
 */

public class ServiceResponseItem  {

    int url;
    String serviceName;

    public ServiceResponseItem(int url, String serviceName) {
        this.url = url;
        this.serviceName = serviceName;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
