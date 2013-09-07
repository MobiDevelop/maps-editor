import com.badlogic.gdx.tools.imagepacker.TexturePacker2;


public class BuildAssets {

	public static void main( String[] args ) {

//		Settings settings = new Settings();
		TexturePacker2.process( "assets-raw/skin", "../maps-editor-android/assets/data", "uiskin.atlas" );
	}
}
