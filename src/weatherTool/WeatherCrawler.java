package weatherTool;

import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class WeatherCrawler {
	private static final String CENTER_WEATHER = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-D0047-091?Authorization=CWB-A21EED50-53BD-4309-A0C1-1C3B9F73134A&elementName=WeatherDescription";
	
	public Map<String,List<WeatherVO>> Crawler() throws IOException, JSONException {
		Map weatherTypeCode = new HashMap();
		try {
			// 從輸入流創建Workbook
			Workbook workbook = Workbook.getWorkbook(new File("C:\\EA102G4_WebApp\\eclipse_WTP_EA102G4\\EA102G4\\WebContent\\WEB-INF\\weatherData\\weatherTypeCode.xls"));
			// 由Workbook的getSheet(0)方法選擇第一個工作表（從0開始）
			Sheet sheet1 = workbook.getSheet(0);
			// 取得Sheet表中所包含的總row數
			int rows = sheet1.getRows();
			// 取得Sheet表中所包含的總column數
			int columns = sheet1.getColumns();

			for (int i = 0; i < rows; i++) {
				String key = null;
				String value = null;
				for (int j = 0; j < columns; j++) {
					// Sheet方法的getCell(j, i)方法選擇位置為(j, i)的單元格（兩個參數都從0開始）
					Cell cell = sheet1.getCell(j, i);
					// Cell的getContents方法把單元格中的信息以字符的形式讀取出來
					String contents = cell.getContents();
					if (j == 0) {
						key = contents;
					} else {
						value = contents;
					}
					weatherTypeCode.put(key, value);
				}
			}
			workbook.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
//--------------------------------------------------------------------------------------------------------		
		Map<String,List<WeatherVO>> weatherMap = new HashMap<String,List<WeatherVO>>();
		List<WeatherVO> list = null;
		WeatherVO wVO = null;
		
		
		StringBuilder sb = new StringBuilder();
		URL url = new URL(CENTER_WEATHER);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setUseCaches(false);

		int statusCode = con.getResponseCode();
		System.out.println(statusCode);

		InputStream is = con.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader br = new BufferedReader(isr);

		String data;
		while ((data = br.readLine()) != null) {
			sb.append(data.trim());
		}

		String sbString = sb.toString();
		JSONObject weatherJ = new JSONObject(sbString);
		JSONObject recordsO = ((JSONObject)weatherJ.get("records"));
		JSONArray locationsA = recordsO.getJSONArray("locations");
		JSONArray locationA = locationsA.getJSONObject(0).getJSONArray("location");
		
		for(int i = 0; i<locationA.length();i++) {
			String city = locationA.getJSONObject(i).getString("locationName");//縣市
			list = new ArrayList<WeatherVO>();
			for(int j = 0;j<locationA.getJSONObject(i).getJSONArray("weatherElement").getJSONObject(0).getJSONArray("time").length();j++) {
				wVO = new WeatherVO();
				
				String weatherTotalDescribe = locationA.getJSONObject(i).getJSONArray("weatherElement").getJSONObject(0).getJSONArray("time").getJSONObject(j).getJSONArray("elementValue").getJSONObject(0).getString("value");
				
				String weatherTime = locationA.getJSONObject(i).getJSONArray("weatherElement").getJSONObject(0).getJSONArray("time").getJSONObject(j).getString("startTime");//開始時間 12小時循環
				String weatherType = weatherTotalDescribe.substring(0,weatherTotalDescribe.indexOf("。"));//天氣種類
				String weatherShortDescribe = weatherTotalDescribe.substring(weatherTotalDescribe.indexOf("。")+1,weatherTotalDescribe.length());//天氣短概述
				
				wVO.setTime(weatherTime.substring(0, 10));
				wVO.setWeatherType((String)weatherTypeCode.get(weatherType));
				wVO.setWeatherDescribe(weatherShortDescribe.substring(0,weatherShortDescribe.length()-8));
				
				list.add(wVO);
			}
			weatherMap.put(city, list);
		}
		br.close();
		isr.close();
		is.close();
		
		return weatherMap;
	}
	
	public static void main(String[] args) {
		WeatherCrawler wC = new WeatherCrawler();
		try {
			Map<String,List<WeatherVO>> wM =wC.Crawler();
			List<WeatherVO> list = wM.get("臺北市");
			for(int i = 0;i<list.size();i++) {
				System.out.println(list.get(i).getTime());
				System.out.println(list.get(i).getWeatherType());
				System.out.println(list.get(i).getWeatherDescribe());
				System.out.println("-------------------------------------------------------");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
