package com.tianyi.helmet.server.entity.data;

public class HelmetDeviceInfo extends HelmetData {
    private String version;
    private Integer categoryId;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

}
