package com.superiorcreaturenotify;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class SuperiorCreatureNotifierPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(SuperiorCreatureNotifierPlugin.class);
		RuneLite.main(args);
	}
}