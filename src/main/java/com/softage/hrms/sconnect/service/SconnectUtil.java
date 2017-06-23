package com.softage.hrms.sconnect.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SconnectUtil {

	public StringBuffer getServiceData(String serviceUrl) {
		String output = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			
			
			URL url = new URL(serviceUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			while ((output = br.readLine()) != null) {
				stringBuffer.append(output);
			}			
			conn.disconnect();
		} catch (MalformedURLException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuffer;
	}
	
	
	
	public StringBuffer getPostServiceData(String serviceUrl,String getInput) {
		String output = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {	
			
			URL url = new URL(serviceUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestMethod("POST");

			OutputStream outputStream = conn.getOutputStream();
			outputStream.write(getInput.getBytes());
			outputStream.flush();

			if (conn.getResponseCode() != 200) {
				throw new MalformedURLException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			while ((output = br.readLine()) != null) {
				stringBuffer.append(output);
			}		
			conn.disconnect();
		} catch (MalformedURLException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuffer;
	}
}
