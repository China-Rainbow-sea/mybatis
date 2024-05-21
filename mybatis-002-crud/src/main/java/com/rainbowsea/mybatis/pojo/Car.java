package com.rainbowsea.mybatis.pojo;

public class Car {
    //数据表当中的字段应该和pojo类的属性一一对应
    // 建议使用包装类，这样可以防止 null 的问题：int = null; 不行，Int = null 可以
    private Long id;
    private String carNum;
    private String brand;
    private Double guiderPrice;
    private String produceTime;
    private String carType;

    public Car() {
    }

    public Car(Long id, String carNum, String brand, Double guiderPrice, String produceTime, String carType) {
        this.id = id;
        this.carNum = carNum;
        this.brand = brand;
        this.guiderPrice = guiderPrice;
        this.produceTime = produceTime;
        this.carType = carType;
    }


    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", carNum='" + carNum + '\'' +
                ", brand='" + brand + '\'' +
                ", guiderPrice=" + guiderPrice +
                ", produceTime='" + produceTime + '\'' +
                ", carType='" + carType + '\'' +
                '}';
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getGuiderPrice() {
        return guiderPrice;
    }

    public void setGuiderPrice(Double guiderPrice) {
        this.guiderPrice = guiderPrice;
    }

    public String getProduceTime() {
        return produceTime;
    }

    public void setProduceTime(String produceTime) {
        this.produceTime = produceTime;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }
}
