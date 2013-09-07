package com.mobidevelop.maps.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mobidevelop.maps.MapLayer;
import com.mobidevelop.maps.MapObject;
import com.mobidevelop.maps.editor.models.MapModels.MapLayersModel.LayerAddedEvent;
import com.mobidevelop.maps.editor.models.MapModels.MapLayersModel.LayerHiddenEvent;
import com.mobidevelop.maps.editor.models.MapModels.MapLayersModel.LayerRemovedEvent;
import com.mobidevelop.maps.editor.models.MapModels.MapLayersModel.LayerSelectedEvent;
import com.mobidevelop.maps.editor.models.MapModels.MapLayersModel.LayerShownEvent;
import com.mobidevelop.maps.editor.models.MapModels.MapObjectsModel.ObjectAddedEvent;
import com.mobidevelop.maps.editor.ui.UIEvents;
import com.mobidevelop.utils.events.Event;
import com.mobidevelop.utils.events.EventDispatcher;
import com.mobidevelop.utils.events.EventListener;


public class LevelStage extends Stage implements EventListener {

	
	private PixmapPacker imagePacker = null;
	private TextureAtlas imageAtlas;

	private Group layers;

	private OrthographicCamera camera;
	private Vector2 worldspaceCenter = new Vector2();
	int lastX, lastY;
	Vector3 vec3 = new Vector3();

	public LevelStage( float width, float height, SpriteBatch batch ) {

		super( width, height, true, batch );

		// imagePacker packs added image resources into an atlas for stage rendering
		imagePacker = new PixmapPacker( 1024, 1024, Format.RGBA8888, 2, false );
		imageAtlas = new TextureAtlas();

		layers = new Group();
		addActor( layers );

		camera = new OrthographicCamera( width, height );
		setCamera( camera );
	}

	// handles camera controls
	@Override
	public boolean touchDown( int screenX, int screenY, int pointer, int button ) {

		lastX = screenX;
		lastY = screenY;
		if ( Gdx.input.isButtonPressed( Buttons.LEFT ) ) {
			Actor selected = layers.hit( screenX, screenY, false );
			if ( selected != null ) {
				if ( selected instanceof StageObject )
					selected.fire( UIEvents.selectObject( ((StageObject)selected).getMapObject() ) );
			}
		}
		return super.touchDown( screenX, screenY, pointer, button );
	}

	// handles camera controls
	@Override
	public boolean touchDragged( int screenX, int screenY, int pointer ) {

		if ( Gdx.input.isButtonPressed( Buttons.RIGHT ) ) {
			camera.position.sub( screenX - lastX, lastY - screenY, 0 );
			lastX = screenX;
			lastY = screenY;
			updateCamera();
		}

		return super.touchDragged( screenX, screenY, pointer );
	}

	// updates the camera and stores the stage center for use with MapObject addition to the stage
	private void updateCamera() {

		camera.update();
		camera.unproject( vec3.set( 0.5f*Gdx.graphics.getWidth() + camera.position.x / Gdx.graphics.getWidth(),
																0.5f*Gdx.graphics.getHeight() + ( Gdx.graphics.getHeight() - camera.position.y ) / Gdx.graphics.getHeight(), 0 ) );
		worldspaceCenter.set( vec3.x, vec3.y );
	}

	public Vector2 getCenter() {
		return worldspaceCenter;
	}

	// adds an image brush to the stage, packing it into the atlas if not already there
	public void addImageBrush( MapLayer layer, MapObject object, FileHandle file ) {

		// look for image in atlas, pack if not found
		AtlasRegion image = imageAtlas.findRegion( file.name() );
		if ( image == null ) {
			imagePacker.pack( file.name(), new Pixmap( file ) );
			imagePacker.updateTextureAtlas( imageAtlas, TextureFilter.Linear, TextureFilter.Linear, true );
			image = imageAtlas.findRegion( file.name() );
		}

		// add image to stage
		ImageBrush brush = new ImageBrush( image, object );
		brush.setPosition( object.getX(), object.getY() );
		Group layerGroup = getLayerGroup( layer );
		if ( layerGroup != null )
			layerGroup.addActor( brush );
	}

	private LayerGroup getLayerGroup( MapLayer layer ) {

		SnapshotArray<Actor> ls = layers.getChildren();
		for ( int i = 0; i < ls.size; i++ ) {
			LayerGroup l = (LayerGroup)ls.get( i );
			if ( layer == l.getLayer() )
				return l;
		}
		return null;
	}

	@Override
	public void dispose() {
	
		super.dispose();
		imagePacker.dispose();
	}

	@Override
	public void onEvent( EventDispatcher dispatcher, Event event ) {

		// model added a layer
		if ( event instanceof LayerAddedEvent ) {
			LayerAddedEvent evt = (LayerAddedEvent)event;
			LayerGroup layer = new LayerGroup( evt.layer );
			layer.setName( evt.layer.getName() );
			layers.addActor( layer );
			return;
		}

		// model removed a layer
		if ( event instanceof LayerRemovedEvent ) {
			LayerRemovedEvent evt = (LayerRemovedEvent)event;
			LayerGroup layer = getLayerGroup( evt.layer );
			if ( layer != null )
				layer.remove();
			return;
		}

		// model changed selected layer
		if ( event instanceof LayerSelectedEvent ) {
			return;
		}

		// model hid layer
		if ( event instanceof LayerHiddenEvent ) {
			LayerHiddenEvent evt = (LayerHiddenEvent)event;
			LayerGroup layer = getLayerGroup( evt.layer );
			if ( layer != null )
				layer.setVisible( false );
			return;
		}

		// model showed layer
		if ( event instanceof LayerShownEvent ) {
			LayerShownEvent evt = (LayerShownEvent)event;
			LayerGroup layer = getLayerGroup( evt.layer );
			if ( layer != null )
				layer.setVisible( true );
			return;
		}

		// model added an image brush
		if ( event instanceof ObjectAddedEvent ) {
			ObjectAddedEvent evt = (ObjectAddedEvent)event;
			addImageBrush( evt.layer, evt.object, evt.file );
		}
	}

	public class LayerGroup extends Group {
		
		private MapLayer layer;
		public LayerGroup( MapLayer layer ) {

			this.layer = layer;
		}

		public MapLayer getLayer() {

			return layer;
		}
	}

	public interface StageObject {
		public MapObject getMapObject();
	}

	public class ImageBrush extends Image implements StageObject {

		private MapObject object;

		public ImageBrush( AtlasRegion image, MapObject object ) {

			super( image );
			this.object = object;
		}

		@Override
		public MapObject getMapObject() {
			return object;
		}
	}
}
