package api

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["api", "config", "controllers", "services"])
class WeatherApp {
    static void main(String[] args) {
        SpringApplication.run(WeatherApp, args)
    }
}