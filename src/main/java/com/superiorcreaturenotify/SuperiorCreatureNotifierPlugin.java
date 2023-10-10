package com.superiorcreaturenotify;

import com.google.inject.Provides;
import javax.inject.Inject;
import javax.sound.sampled.*;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.io.File;
import java.io.IOException;

@Slf4j
@PluginDescriptor(
		name = "Superior Creature Sound",
		description = "Plays a sound notification if a superior creature spwans"
)
public class SuperiorCreatureNotifierPlugin extends Plugin
{
	@Inject
	private Client client;
	@Inject
	private SuperiorCreatureSoundConfig config;
	private Clip soundClip;

	@Subscribe
	public void onChatMessage(ChatMessage chatMessage){
		if(chatMessage.getMessage().contains("A superior foe has appeared...") && chatMessage.getType() == ChatMessageType.GAMEMESSAGE){
			playSound();
		}
	}

	private void playSound(){
		try {

			// If no soundclip found -> queue new soundclip
			if (soundClip == null) {
				String filteredResult = config.customSoundpath().replaceAll("^\"|\"$", "");
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filteredResult));
				soundClip = AudioSystem.getClip();
				soundClip.open(audioInputStream);
				System.out.println(soundClip.toString());
			}
			// Check if sound is running already
			if (soundClip.isRunning()) {
				soundClip.stop();
			}


			//Volume control
			soundClip.setFramePosition(0);
			FloatControl volume = (FloatControl) soundClip.getControl(FloatControl.Type.MASTER_GAIN);
			float volumeValue = config.volume() - 100;
			volume.setValue(volumeValue);
			soundClip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void startUp() throws Exception {
		log.info("Superior Creature Notifier started!");
	}

	@Override
	protected void shutDown() throws Exception {
		log.info("Superior Creature Notifier stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged) {
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN) {
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Superior Creature Notifier sounds enabled!", null);
		}
	}

	@Provides
	SuperiorCreatureSoundConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(SuperiorCreatureSoundConfig.class);
	}
}

