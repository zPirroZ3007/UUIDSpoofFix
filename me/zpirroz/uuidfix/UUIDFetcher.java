/**
 * This is the UUID Fetcher of UUIDSpoof - Fix
 * This code has been writted by @zPirroZ3007
 * You are not allowed to resell this code, or claim this code as your own.
 * You have been warned.
 * Special thanks to http://tools.glowingmines.eu/ For the API.
 */
package me.zpirroz.uuidfix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UUIDFetcher
{
	public static String get(String username)
	{
		try
		{
			URL url = new URL("http://tools.glowingmines.eu/convertor/nick/" + username);
			InputStream stream = url.openStream();
			InputStreamReader inr = new InputStreamReader(stream);
			BufferedReader reader = new BufferedReader(inr);
			String s = null;
			StringBuilder sb = new StringBuilder();
			while ((s = reader.readLine()) != null)
			{
				sb.append(s);
			}
			String result = sb.toString();

			JsonElement element = new JsonParser().parse(result);
			JsonObject obj = element.getAsJsonObject();

			String uuid = obj.get("offlinesplitteduuid").toString();

			uuid = uuid.substring(1);
			uuid = uuid.substring(0, uuid.length() - 1);

			return uuid;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
