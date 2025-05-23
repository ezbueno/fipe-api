package developer.ezandro.fipe;

import developer.ezandro.fipe.ui.Menu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FipeApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(FipeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Menu menu = new Menu();
        menu.displayMainMenu();
    }
}