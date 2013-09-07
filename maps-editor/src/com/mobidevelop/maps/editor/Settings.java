package com.mobidevelop.maps.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class Settings {

	private static Preferences prefs;

	public static void load() {
		prefs = Gdx.app.getPreferences( "settings" );
	}

	public static void save() {
		if ( prefs != null )
			prefs.flush();
	}

	public static String getLastImagePath() {
		return prefs.getString( "lastImagePath" );
	}

	public static void setLastImagePath( String imagePath ) {
		prefs.putString( "lastImagePath", imagePath );
	}
}
