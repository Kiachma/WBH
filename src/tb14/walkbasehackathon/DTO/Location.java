package tb14.walkbasehackathon.DTO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Location {
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	private long id;
	private Double longitude;
	private Double latitude;
	private Double accuracy;
	private String name;
	private Date timestamp;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public Double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(Double accuracy) {
		this.accuracy = accuracy;
	}
	
	public String getPrettyTimeStamp(){
		return sdf.format(timestamp);
	}
	@Override
	  public String toString() {
	    return name +" "+getPrettyTimeStamp();
	  }
	
	

}
