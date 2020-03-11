package fawda.model;

import java.util.List;

public class Proviance {
    private String name;
    private String code;
    private List<com.risen.oa.controller.City> city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<com.risen.oa.controller.City> getCity() {
        return city;
    }

    public void setCity(List<com.risen.oa.controller.City> city) {
        this.city = city;
    }
}
