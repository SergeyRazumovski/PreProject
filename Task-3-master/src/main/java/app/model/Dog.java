package app.model;

import org.springframework.stereotype.Component;

/**
 * Создал класс Dog и пометил аннотацией @Component, так же переопределил метод toString()
 */
@Component
public class Dog extends Animal {
    @Override
    public String toString() {
        return "Im a Dog";
    }
}
