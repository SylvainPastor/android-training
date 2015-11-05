package fr.rtone.meteo.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.xml.sax.InputSource;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Network {

	public static HttpResponse postData(String url,
			List<NameValuePair> nameValuePairs) throws ClientProtocolException,
			IOException {

		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		int timeoutConnection = 1200000;
		httpParameters.setLongParameter(ConnManagerPNames.TIMEOUT, 1200000);
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		
		// Set the default socket timeout (SO_TIMEOUT)
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 1200000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient(httpParameters);
		httpclient.getParams().setParameter("http.protocol.content-charset", HTTP.UTF_8);
		HttpPost httppost = new HttpPost(url);
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

		// Execute HTTP Post Request

		HttpResponse response = httpclient.execute(httppost);
		return response;
	}

	public static HttpResponse getData(String url)
			throws ClientProtocolException, IOException, URISyntaxException {
		HttpParams httpParameters = new BasicHttpParams();
		
		// Set the timeout in milliseconds until a connection is established.
		int timeoutConnection = 1200000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		
		// Set the default socket timeout (SO_TIMEOUT)
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 1200000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient(httpParameters);

		URI uri = new URI(url);

		HttpGet httpGet = new HttpGet();
		httpGet.setURI(uri);
		
		// Execute HTTP Post Request
		HttpResponse response = httpclient.execute(httpGet);
		
		return response;
	}

	public static boolean isNetworkAvailable(Context context) {
		// Context context = getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private static InputSource retrieveInputStream(HttpEntity httpEntity) {
		InputSource insrc = null;
		try {
			insrc = new InputSource(httpEntity.getContent());
		} catch (Exception e) {
		}
		return insrc;
	}

}
