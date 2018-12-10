/**
 * ---------------------------------------------- *
 * Written by @zPirroZ3007 github.com/zPirroZ3007 *
 * You don't have the right to resell this code   *
 * or claim this code as your own!                *
 * You have been warned.                          *
 * ---------------------------------------------- *
 **/

package me.pirro.uuidspoofix.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.Charsets;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Fetcher
{
	private static Map<String, String> uuidsOnline = new HashMap<String, String>();

	public static UUID fetchOffline(String username)
	{
		return UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(Charsets.UTF_8));
	}

	public static UUID fetchOffline(Player player)
	{
		String username = player.getName();

		if (Util.usingBetterNick())
			username = Util.getBetterNick().getRealName(player);
		return UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(Charsets.UTF_8));
	}

	public static String fetchOnline(Player player)
	{
		String username = player.getName();

		if (Util.usingBetterNick())
			username = Util.getBetterNick().getRealName(player);

		if (uuidsOnline.containsKey(username))
			return uuidsOnline.get(username);

		try
		{
			URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
			InputStream stream = url.openStream();
			InputStreamReader inr = new InputStreamReader(stream);
			BufferedReader reader = new BufferedReader(inr);
			String s;
			StringBuilder sb = new StringBuilder();
			while ((s = reader.readLine()) != null)
			{
				sb.append(s);
			}
			String result = sb.toString();

			JsonElement element = new JsonParser().parse(result);
			JsonObject obj = element.getAsJsonObject();

			String uuid = obj.get("id").toString();

			uuid = uuid.substring(1);
			uuid = uuid.substring(0, uuid.length() - 1);

			uuidsOnline.put(username, uuid);
			return uuid;
		}
		catch (Exception ex)
		{
		}
		return null;
	}

	public static String fetchOnline(String username)
	{
		if (uuidsOnline.containsKey(username))
			return uuidsOnline.get(username);

		try
		{
			URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
			InputStream stream = url.openStream();
			InputStreamReader inr = new InputStreamReader(stream);
			BufferedReader reader = new BufferedReader(inr);
			String s;
			StringBuilder sb = new StringBuilder();
			while ((s = reader.readLine()) != null)
			{
				sb.append(s);
			}
			String result = sb.toString();

			JsonElement element = new JsonParser().parse(result);
			JsonObject obj = element.getAsJsonObject();

			String uuid = obj.get("id").toString();

			uuid = uuid.substring(1);
			uuid = uuid.substring(0, uuid.length() - 1);

			uuidsOnline.put(username, uuid);
			return uuid;
		}
		catch (Exception ex)
		{
		}
		return null;
	}
}
