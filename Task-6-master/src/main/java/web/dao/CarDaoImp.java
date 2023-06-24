package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Car;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CarDaoImp implements CarDao {

    List<Car> cars = List.of(
            new Car("Lada", "ВАЗ", "2106", "white"),
            new Car("Lada", "ВАЗ", "21099", "black"),
            new Car("Lada", "ВАЗ", "2110", "grey"),
            new Car("Ford", "Focus", "I", "blue"),
            new Car("Kia", "Rio", "x-line", "braun")
    );

    @Override
    public List<Car> getAllCars() {
        return cars;
    }

    @Override
    public List<Car> getCarList(int count) {
        return (count > 0) ? cars.subList(0, Math.min(count, cars.size())) : cars;
    }
}
