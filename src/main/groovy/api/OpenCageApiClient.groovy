package api

import groovy.json.JsonSlurper
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.util.EntityUtils
import org.springframework.stereotype.Service
import constants.Constants

@Service
class OpenCageApiClient {

    private final CloseableHttpClient httpClient

    OpenCageApiClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient
    }

    def getLocation(city) {
        def openCageApiUrl = "https://api.opencagedata.com/geocode/v1/json?q=${URLEncoder.encode(city, "UTF-8")}&key=${Constants.OPEN_CAGE_API_KEY}"
        def httpGet = new HttpGet(openCageApiUrl)
        def httpResponse = httpClient.execute(httpGet)
        def response = EntityUtils.toString(httpResponse.entity)
        def jsonSlurper = new JsonSlurper()
        def data = jsonSlurper.parseText(response)
        def location = new models.Location(lat: data.results[0].geometry.lat, lng: data.results[0].geometry.lng)
        return location
    }
}