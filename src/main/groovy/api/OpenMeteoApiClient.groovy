package api

import groovy.json.JsonSlurper
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.util.EntityUtils
import org.springframework.stereotype.Service

@Service
class OpenMeteoApiClient {

    private final CloseableHttpClient httpClient

    OpenMeteoApiClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient
    }

    def getWeather(lat, lng) {
        def openMeteoApiUrl = "https://api.open-meteo.com/v1/forecast?latitude=${lat}&longitude=${lng}&current_weather=true&hourly=temperature_2m,relativehumidity_2m,windspeed_10m"
        def httpGet = new HttpGet(openMeteoApiUrl)
        def httpResponse = httpClient.execute(httpGet)
        def response = EntityUtils.toString(httpResponse.entity)
        def jsonSlurper = new JsonSlurper()
        def data = jsonSlurper.parseText(response)
        def weather = new models.Weather(temperature: data.hourly.temperature_2m[0])
        return weather
    }
}