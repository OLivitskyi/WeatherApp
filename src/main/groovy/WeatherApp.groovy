import api.OpenCageApiClient
import api.OpenMeteoApiClient

class WeatherApp {
    OpenCageApiClient openCageApiClient = new OpenCageApiClient()
    OpenMeteoApiClient openMeteoApiClient = new OpenMeteoApiClient()

    def getWeatherForCity(city) {
        def location = openCageApiClient.getLocation(city)
        def weather = openMeteoApiClient.getWeather(location.lat, location.lng)
        return weather
    }

    def recommendClothing(weather) {
        if (weather.temperature < 0) {
            return "Одягніть теплу куртку, шарф та рукавички."
        } else if (weather.temperature >= 0 && weather.temperature < 15) {
            return "Одягніть легку куртку або светр."
        } else {
            return "Одягніть футболку або сорочку."
        }
    }
}