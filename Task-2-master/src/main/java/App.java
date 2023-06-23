import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AppConfig.class);
        HelloWorld bean1 =
                (HelloWorld) applicationContext.getBean("helloworld");
        HelloWorld bean2 =
                (HelloWorld) applicationContext.getBean("helloworld");
        System.out.println(bean1.getMessage());
        System.out.println(bean2.getMessage());

        Cat cat1 = (Cat) applicationContext.getBean("cat");
        Cat cat2 = (Cat) applicationContext.getBean("cat");

        boolean checkHelloWorld = bean1 == bean2;
        boolean checkCats = cat1 == cat2;

        System.out.println(cat1.getCatsName());
        System.out.println(cat2.getCatsName());

        System.out.println("Равенство бинов с сообщениями: " + checkHelloWorld);
        System.out.println("Равенство бинов с кошками: " + checkCats);
    }
}