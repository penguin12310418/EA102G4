package weatherTool;

public class WeatherVO {
	private String time;
	private String weatherType;
	private String weatherDescribe;
	
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getWeatherType() {
		return weatherType;
	}
	public void setWeatherType(String weatherType) {
		this.weatherType = weatherType;
	}
	public String getWeatherDescribe() {
		return weatherDescribe;
	}
	public void setWeatherDescribe(String weatherDescribe) {
		this.weatherDescribe = weatherDescribe;
	}
	@Override
	public String toString() {
		return "weatherVO [time=" + time + ", weatherType=" + weatherType + ", weatherDescribe=" + weatherDescribe
				+ "]";
	}
}
