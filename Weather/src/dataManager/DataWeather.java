package dataManager;

import java.io.Serializable;
import java.util.Date;

public class DataWeather implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private City city;
	private Date date_weather;
	private Date pub_date;
	private String condition;
	private int min_temp;
	private int max_temp;
	private String wind_dir;
	private int wind_speed;
	private String visibility;
	private int pressure;
	private int humidity;
	private String sunset;
	private String sunrise;
	private int uv_risk;
	
	public DataWeather(City city, Date date_weather, Date pub_date,
			String condition, int min_temp, int max_temp, String wind_dir,
			int wind_speed, String visibility, int pressure, int humidity,
			String sunset, String sunrise, int uv_risk) {
		
		this.city = city;
		this.date_weather = date_weather;
		this.pub_date = pub_date;
		this.condition = condition;
		this.min_temp = min_temp;
		this.max_temp = max_temp;
		this.wind_dir = wind_dir;
		this.wind_speed = wind_speed;
		this.visibility = visibility;
		this.pressure = pressure;
		this.humidity = humidity;
		this.sunset = sunset;
		this.sunrise = sunrise;
		this.uv_risk = uv_risk;
	}

	/**
	 * @return the city
	 */
	public City getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(City city) {
		this.city = city;
	}

	/**
	 * @return the date_weather
	 */
	public Date getDate_weather() {
		return date_weather;
	}

	/**
	 * @param date_weather the date_weather to set
	 */
	public void setDate_weather(Date date_weather) {
		this.date_weather = date_weather;
	}

	/**
	 * @return the pub_date
	 */
	public Date getPub_date() {
		return pub_date;
	}

	/**
	 * @param pub_date the pub_date to set
	 */
	public void setPub_date(Date pub_date) {
		this.pub_date = pub_date;
	}

	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * @return the min_temp
	 */
	public int getMin_temp() {
		return min_temp;
	}

	/**
	 * @param min_temp the min_temp to set
	 */
	public void setMin_temp(int min_temp) {
		this.min_temp = min_temp;
	}

	/**
	 * @return the max_temp
	 */
	public int getMax_temp() {
		return max_temp;
	}

	/**
	 * @param max_temp the max_temp to set
	 */
	public void setMax_temp(int max_temp) {
		this.max_temp = max_temp;
	}

	/**
	 * @return the wind_dir
	 */
	public String getWind_dir() {
		return wind_dir;
	}

	/**
	 * @param wind_dir the wind_dir to set
	 */
	public void setWind_dir(String wind_dir) {
		this.wind_dir = wind_dir;
	}

	/**
	 * @return the wind_speed
	 */
	public int getWind_speed() {
		return wind_speed;
	}

	/**
	 * @param wind_speed the wind_speed to set
	 */
	public void setWind_speed(int wind_speed) {
		this.wind_speed = wind_speed;
	}

	/**
	 * @return the visibility
	 */
	public String getVisibility() {
		return visibility;
	}

	/**
	 * @param visibility the visibility to set
	 */
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	/**
	 * @return the pressure
	 */
	public int getPressure() {
		return pressure;
	}

	/**
	 * @param pressure the pressure to set
	 */
	public void setPressure(int pressure) {
		this.pressure = pressure;
	}

	/**
	 * @return the humidity
	 */
	public int getHumidity() {
		return humidity;
	}

	/**
	 * @param humidity the humidity to set
	 */
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	/**
	 * @return the sunset
	 */
	public String getSunset() {
		return sunset;
	}

	/**
	 * @param sunset the sunset to set
	 */
	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	/**
	 * @return the sunrise
	 */
	public String getSunrise() {
		return sunrise;
	}

	/**
	 * @param sunrise the sunrise to set
	 */
	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	/**
	 * @return the uv_risk
	 */
	public int getUv_risk() {
		return uv_risk;
	}

	/**
	 * @param uv_risk the uv_risk to set
	 */
	public void setUv_risk(int uv_risk) {
		this.uv_risk = uv_risk;
	}

	
	

}
