import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import java.net.URL
import groovy.json.JsonSlurper

class WeatherApp {
    static def openCageApiKey = "92e5ead72e76410fa2ca30d2d6c9a897"

    def getCoordinates(city) {
        def geocodingApiUrl = "https://api.opencagedata.com/geocode/v1/json?q=${city}&key=${openCageApiKey}&limit=1"
        def httpClient = HttpClients.createDefault()
        def httpGet = new HttpGet(geocodingApiUrl)

        def response = httpClient.execute(httpGet)
        def statusCode = response.getStatusLine().getStatusCode()

        if (statusCode == 200) {
            def responseBody = EntityUtils.toString(response.getEntity())
            def jsonSlurper = new JsonSlurper()
            def data = jsonSlurper.parseText(responseBody)
            def location = data.results[0].geometry
            return [location.lat, location.lng]
        } else {
            println "Помилка при отриманні координат міста"
            return null
        }
    }

/*    def getWeather(lat, lng) {
        def openMeteoApiUrl = "https://api.open-meteo.com/v1/forecast?latitude=${lat}&longitude=${lng}&current_weather=true&hourly=temperature_2m,relativehumidity_2m,windspeed_10m"
        def httpClient = HttpClients.createDefault()
        def httpGet = new HttpGet(openMeteoApiUrl)

        def response = httpClient.execute(httpGet)
        def statusCode = response.getStatusLine().getStatusCode()

        if (statusCode == 200) {
            def responseBody = EntityUtils.toString(response.getEntity())
            def jsonSlurper = new JsonSlurper()
            def data = jsonSlurper.parseText(responseBody)
            return data.hourly.temperature_2m[0]
        } else {
            println "Помилка при отриманні погоди"
            return null
        }
    }*/
    def getWeather(lat, lng) {
        def openMeteoApiUrl = "https://api.open-meteo.com/v1/forecast?latitude=${lat}&longitude=${lng}&current_weather=true&hourly=temperature_2m,relativehumidity_2m,windspeed_10m"
        def url = new URL(openMeteoApiUrl)
        def connection = url.openConnection()
        def response = connection.content.text
        def jsonSlurper = new JsonSlurper()
        def data = jsonSlurper.parseText(response)
        return data.hourly.temperature_2m[0]
    }

    def chooseClothes(temperature) {
        if (temperature < 15) {
            return "Куртка"
        } else {
            return "Футболка"
        }
    }

    def run() {
        def city = "Київ"
        def coordinates = getCoordinates(city)

        if (coordinates) {
            def weather = getWeather(coordinates[0], coordinates[1])

            if (weather) {
                def clothes = chooseClothes(weather)
                println "Рекомендована одежа для міста $city: $clothes"
            }
        }
    }
}