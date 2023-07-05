def app = new WeatherApp()
def city = "Kyiv"
def weather = app.getWeatherForCity(city)
println "Температура в ${city}: ${weather.temperature}"
println "Рекомендація щодо одягу: ${app.recommendClothing(weather)}"