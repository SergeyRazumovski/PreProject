package web.model;

import java.util.function.Predicate;

public class Car {

    private String brand;
    private String model;
    private String series;
    private String color;

    public Car() {
    }

    public Car(String brand, String model, String series, String color) {
        this.brand = brand;
        this.model = model;
        this.series = series;
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getSeries() {
        return series;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", series='" + series + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
