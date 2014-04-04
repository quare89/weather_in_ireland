package dataManager;

import java.io.Serializable;

public class City implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String country;
	private double longitude;
	private double latitude;
	private int altitude;
	private int cod_rss;
	
	public City(int _id,String _name,String _country,double _longitude,double _latitude,int _altitude,int _cod_rss)
	{
		id=_id;
		name=_name;
		country=_country;
		longitude=_longitude;
		latitude=_latitude;
		altitude=_altitude;
		cod_rss=_cod_rss;
	}

	
	/**
	 * @return the cod_rss
	 */
	public int getCod_rss() {
		return cod_rss;
	}


	/**
	 * @param cod_rss the cod_rss to set
	 */
	public void setCod_rss(int cod_rss) {
		this.cod_rss = cod_rss;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		
		
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the altitude
	 */
	public int getAltitude() {
		return altitude;
	}

	/**
	 * @param altitude the altitude to set
	 */
	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}

	public String printCoords(double coord) {
		
		int degrees,mins,secs;
		
		if(coord<0)
			coord*=-1;
		
		degrees=(int) Math.floor(coord);
		double minsdouble=(coord-degrees)*60;
		mins=(int)Math.floor(minsdouble);
		double secsdouble=(minsdouble-mins)*60;
		secs=(int)Math.floor(secsdouble);
		
		return degrees+"° "+mins+"' "+secs+"\"";
	}

	
	

	
	
}