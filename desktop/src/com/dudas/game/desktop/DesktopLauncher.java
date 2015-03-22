package com.dudas.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.dudas.game.Match2;

public class DesktopLauncher {

	private static boolean rebuildAtlas = false;
	private static boolean drawDebugOutline = false;

	public static void main (String[] arg) {

		System.setProperty("logback.configurationFile", "E:\\ligdxspace\\match2\\desktop\\src\\com\\dudas\\game\\desktop\\logback.xml");

		if (rebuildAtlas) {
			TexturePacker.Settings settings = new TexturePacker.Settings();
			settings.maxWidth = 2048;
			settings.maxHeight = 2048;
			settings.debug = drawDebugOutline;
			TexturePacker.process(settings, "../assets-raw/images", "images", "match.pack");
		}

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "MultipleStages";
//		config.width = 1280;
		config.width = 360;
//		config.height = 720;
		config.height = 360;
		new LwjglApplication(new Match2(), config);
	}
}
