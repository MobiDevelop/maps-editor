package com.mobidevelop.maps.editor.ui;

import java.util.HashMap;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;


public class ImagePicker extends Table implements Disposable {

	private PixmapPacker thumbPacker = null;
	private TextureAtlas thumbImageAtlas;
	private HashMap<String, FileHandle> projectImages;

	private Table thumbTable;

	public ImagePicker( final Skin skin ) {

		thumbImageAtlas = new TextureAtlas();
		projectImages = new HashMap<String, FileHandle>();

		// thumbnails sit in a table within a scroll pane
		thumbTable = new Table();
		thumbTable.row();
		thumbTable.add( new Label( "No images loaded", skin ) );
		final ScrollPane thumbScroll = new ScrollPane( thumbTable );

		// add click listener to send event for new image to stage
		thumbScroll.addListener( new ClickListener() {
			@Override
			public void clicked( InputEvent event, float x, float y ) {

				Actor actor = thumbScroll.hit( x, y, false );
				if ( actor != null ) {
					FileHandle file = projectImages.get( actor.getName() );
					if ( file != null )
						fire( UIEvents.addImageEvent( file ) );
				}
				super.clicked( event, x, y );
			}
		} );

		add( thumbScroll );
	}

	/**
	 * creates a 64x64 thumbnail from the given file (should be an image file)
	 * @param img
	 */
	private void packImageThumb( FileHandle img ) {

		projectImages.put( img.name(), img );
		Pixmap fullImage = new Pixmap( img );
		Pixmap thumb = new Pixmap( 64, 64, Format.RGB565 );
		thumb.drawPixmap( fullImage, 0, 0, fullImage.getWidth(), fullImage.getHeight(), 0, 0, thumb.getWidth(), thumb.getHeight() );
		thumbPacker.pack( img.name(), thumb );
	}

	/**
	 * loads images from the project image folder into thumbnails on the image window
	 * @param file the folder to load images from
	 */
	public void loadProjectImages( FileHandle folder ) {

		// reset thumbs
		if ( thumbPacker != null )
			thumbPacker.dispose();
		thumbPacker = new PixmapPacker( 1024, 1024, Format.RGBA8888, 2, false );
		projectImages.clear();

		// get image thumbs and pack into atlas
		FileHandle[] list = folder.list();
		for ( int i = 0; i < list.length; i++ ) {
			FileHandle img = list[i];
			String ext = img.extension().toLowerCase();
			if ( ext.contentEquals( "png" ) || ext.contentEquals( "jpg" ) ) {
				packImageThumb( img );
			}
		}
		thumbPacker.updateTextureAtlas( thumbImageAtlas, TextureFilter.Linear, TextureFilter.Linear, true );

		// load thumbs into image listbox
		Array<AtlasRegion> regions = thumbImageAtlas.getRegions();
		thumbTable.clear();
		for ( int i = 0; i < regions.size; i++ ) {
			AtlasRegion region = regions.get( i );
			Image img = new Image( region );
			img.setName( region.name );
			thumbTable.row();
			thumbTable.add( img );
		}
	}

	@Override
	public void dispose() {

		if ( thumbPacker != null )
			thumbPacker.dispose();
	}
}
