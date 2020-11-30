package com.internetapplications.entity;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
public class Car extends BaseEntity{

    @Column(
            nullable = false
    )
    private String name;
    @ColumnDefault("0")
    private Double price;
    @ColumnDefault("0")
    private Integer seatsNumber;
    @ColumnDefault("false")
    private boolean sold = false;
    private String buyerName;
    private Double retailPrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(Integer seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Double getRetailPrice() {
        Parameter profitRateParam = this.parameterRepository.findTopByName(defaultSeatsNumberParamName);
        int defaultSeatsNumber = Integer.parseInt(defaultSeatsNumberParam.getValue());

        retailPrice = this.price * (.1);
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

}
