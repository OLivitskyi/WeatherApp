package api

import groovy.json.JsonSlurper
import models.Location
import constants.Constants

//UrlConnection
class OpenCageApiClient {
    def getLocation(city) {
        def openCageApiUrl = "https://api.opencagedata.com/geocode/v1/json?q=${URLEncoder.encode(city, "UTF-8")}&key=${Constants.OPEN_CAGE_API_KEY}"
        def url = new URL(openCageApiUrl)
        def connection = url.openConnection()
        def response = connection.content.text
        def jsonSlurper = new JsonSlurper()
        def data = jsonSlurper.parseText(response)
        def location = new Location(lat: data.results[0].geometry.lat, lng: data.results[0].geometry.lng)
        return location
    }
}