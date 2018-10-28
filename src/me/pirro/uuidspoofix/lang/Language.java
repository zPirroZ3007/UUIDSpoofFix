/**
 * ---------------------------------------------- *
 * Written by @zPirroZ3007 github.com/zPirroZ3007 *
 * You don't have the right to resell this code   *
 * or claim this code as your own!                *
 * You have been warned.                          *
 * ---------------------------------------------- *
 **/

package me.pirro.uuidspoofix.lang;

import me.pirro.uuidspoofix.config.ConfigManager;

public class Language
{
	public static String kickmessage;
	public static String debugmessage;
	public static String joinmessage;

	public static void loadMessages()
	{
		kickmessage = ConfigManager.language().getString("kick-message");
		debugmessage = ConfigManager.language().getString("debug-message");
		joinmessage = ConfigManager.language().getString("uuidcheck-success");
	}
}
