package com.superiorcreaturenotify;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("Superior Creature Notifier ")
public interface SuperiorCreatureSoundConfig extends Config
{

	@ConfigItem(
			keyName = "volume",
			name = "Volume",
			description = "The volume of the sound"
	)
	default int volume(){
		return 70;
	}


	@ConfigItem(
			keyName = "Superior",
			name = "Custom sound path",
			description = "The path the sound to be used. Has to be format .MP3 or .wav"
	)

	default String customSoundpath()
	{
		return "";
	}


}
