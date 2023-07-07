package controllers

import services.WeatherService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class WeatherController {

    private final WeatherService weatherService

    WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService
    }

    @GetMapping("/weather")
    String getWeather(@RequestParam String city) {
        def weather = weatherService.getWeatherForCity(city)
        def recommendation = weatherService.recommendClothing(weather)
        return "Температура в ${city}: ${weather.temperature}. Рекомендація щодо одягу: ${recommendation}"
    }
}