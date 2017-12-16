/**
 * ---------------------------------------------- *
 * Written by @zPirroZ3007                        *
 * You don't have the right to resell this code   *
 * or claim this code as your own!                *
 * You have been warned.                          *
 * ---------------------------------------------- *
 **/

package me.pirro.uuidfix;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

import com.google.common.base.Charsets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Developed by zPirroZ3007 on 11/12/2017.
 */
public class Fetcher
{
	// This is the method that will fetch the REAL uuid of the player
	public String fetchUUID(String username, Boolean onlinemode)
	{
		// Check if the server is online mode or not
		if (onlinemode)
		{
			try
			{
				// Contact the mojang API to get the player UUID.
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

				return uuid;
			}
			catch (Exception ex)
			{
			}
		}
		else
		{
			// Use the OfflinePlayer object to get the real UUID of a player.
			return UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(Charsets.UTF_8)).toString();
		}

		return null;
	}

	// Method that will support plugins like fast-login
	public boolean fastLoginFetcher(String name, String uuid)
	{
		// Check the UUID in every mode.
		if (fetchUUID(name, true).equals(uuid) || fetchUUID(name, false).equals(uuid))
		{
			return true;
		}

		return false;
	}
}