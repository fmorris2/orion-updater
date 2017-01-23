package org;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;

import com.sun.net.ssl.HttpsURLConnection;
import com.sun.net.ssl.SSLContext;
import com.sun.net.ssl.TrustManager;
import com.sun.net.ssl.X509TrustManager;

public class Updater
{
	@SuppressWarnings("deprecation")
	private static final TrustManager[] TRUST_MANAGER = getTrustManager();
	
	static final String USER_HOME = System.getProperty("user.home");
	static final String OCC_DEST = USER_HOME + "/Desktop/OCC.jar";
	static final String OSBOT_DEST = USER_HOME + "/Desktop/OSBot.jar";
	static final String ORION_DEST = USER_HOME + "/OSBot/Scripts/Orion.jar";
	
	static final String WEBSITE = "https://vikingscripts.io/orion/downloads/";
	static final String OCC_DL = WEBSITE + "OCC.jar";
	static final String OSBOT_DL = WEBSITE + "OSBot.jar";
	static final String ORION_DL = WEBSITE + "Orion.jar";
	
	
	public static void main(String[] args)
	{
		installTrustManager();
		
		System.out.println("Downloading most recent OCC");
		download(OCC_DL, OCC_DEST);
		
		System.out.println("Downloading most recent OSBot");
		download(OSBOT_DL, OSBOT_DEST);
		
		System.out.println("Downloading most recent Orion script");
		download(ORION_DL, ORION_DEST);
	}
	
	
	private static void download(String u, String dest)
	{
		try
		{
			URL url = new URL(u);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestProperty("User-Agent", "mUUgbWKTFeQW2W9BgcRzAbThTPc4YrEDAqdWJFnHXPygAufe");
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);
			urlConnection.connect();
			
			File file = new File(dest);

			FileOutputStream fileOutput = new FileOutputStream(file);
			InputStream inputStream = urlConnection.getInputStream();

			byte[] buffer = new byte[1024];
			int bufferLength = 0;

			while ((bufferLength = inputStream.read(buffer)) > 0)
			{
				fileOutput.write(buffer, 0, bufferLength);
			}
			fileOutput.close();

		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void installTrustManager()
	{
		try 
		{
		    SSLContext sc = SSLContext.getInstance("SSL"); 
		    sc.init(null, TRUST_MANAGER, new java.security.SecureRandom()); 
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} 
		catch (GeneralSecurityException e) 
		{
			e.printStackTrace();
		} 
	}
	
	private static TrustManager[] getTrustManager()
	{
		// Create a trust manager that does not validate certificate chains
		return new TrustManager[] 
		{ 
			new X509TrustManager()
			{
				public java.security.cert.X509Certificate[] getAcceptedIssuers()
				{
					return new X509Certificate[0];
				}
	
				public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
				{}
	
				public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
				{}

				@Override
				public boolean isClientTrusted(X509Certificate[] arg0)
				{
					return false;
				}

				@Override
				public boolean isServerTrusted(X509Certificate[] arg0)
				{
					return false;
				}
			} 
		};
	}
}
