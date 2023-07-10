package app

import api.WeatherApp
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException

class WeatherAppRunner {

    static void main(String[] args) {
        WeatherApp.main(args)

        def restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(5000)
                .setReadTimeout(10000)
                .build()
        def url = "http://localhost:8080/weather?city=Kyiv"

        try {
            Thread.sleep(5000)

            def response = restTemplate.getForEntity(url, String.class)

            if (response.statusCode == HttpStatus.OK) {
                println("Отримана відповідь: ${response.body}")
            } else {
                println("Сталася помилка: ${response.statusCode}")
            }
        } catch (HttpClientErrorException e) {
            println("Сталася помилка HTTP: ${e.statusCode}")
        } catch (Exception e) {
            println("Сталася помилка: ${e.message}")
        }
    }
}