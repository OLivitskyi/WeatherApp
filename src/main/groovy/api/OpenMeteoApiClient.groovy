package api
import groovy.json.JsonSlurper
import models.Weather
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils

//ApacheHttpClient
class OpenMeteoApiClient {
    def getWeather(lat, lng) {
        def openMeteoApiUrl = "https://api.open-meteo.com/v1/forecast?latitude=${lat}&longitude=${lng}&current_weather=true&hourly=temperature_2m,relativehumidity_2m,windspeed_10m"
        def httpClient = HttpClients.createDefault()
        def httpGet = new HttpGet(openMeteoApiUrl)
        def httpResponse = httpClient.execute(httpGet)
        def response = EntityUtils.toString(httpResponse.entity)
        def jsonSlurper = new JsonSlurper()
        def data = jsonSlurper.parseText(response)
        def weather = new Weather(temperature: data.hourly.temperature_2m[0])
        return weather
    }
}