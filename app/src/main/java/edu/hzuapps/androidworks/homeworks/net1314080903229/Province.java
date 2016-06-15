package com.perkin.weather.model;

/**
 * Created by perkin on 2016/6/13.
 */
public class Province {
    private int id;
    private String provinceName;
    private String provinceCode;

    public Province(String provinceCode, String provinceName, int id) {
        this.provinceCode = provinceCode;
        this.provinceName = provinceName;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String privinceCode) {
        this.provinceCode = privinceCode;
    }

    public Province() {
    }
}
