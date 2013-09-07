package com.mobidevelop.maps.editor.ui;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.mobidevelop.maps.MapLayer;
import com.mobidevelop.maps.MapObject;


public class UIEvents {

	public static final int EV_ADD_LAYER					= 1;
	public static final int EV_REMOVE_LAYER				= 2;
	public static final int EV_SELECT_LAYER				= 3;
	public static final int EV_HIDE_LAYER					= 4;
	public static final int EV_SHOW_LAYER					= 5;
	public static final int EV_ADD_IMAGE					= 6;
	public static final int EV_SELECT_MAP_OBJECT	= 7;
	public static final int EV_SELECT_IMAGE_PATH	= 8;

	/**
	 * UIEvents are events the scene2d.ui uses to interact with the model.
	 * They are sent by different ui components and handled by the main uiStage, where the model interaction occurs.
	 * The model has it's own events which each ui component responds to as needed.
	 * @author Josh
	 *
	 */
	public static class UIEvent extends Event {
		public int type;
		public UIEvent( int type ) {
			this.type = type;
		}
	}

	public static class LayerEvent extends UIEvent {
		public MapLayer layer;
		public LayerEvent( int type, MapLayer layer ) {
			super( type );
			this.layer = layer;
		}
	}

	public static class MapObjectEvent extends UIEvent {
		public MapObject object;
		public MapObjectEvent( int type, MapObject object ) {
			super( type );
			this.object = object;
		}
	}

	public static class FileEvent extends UIEvent {
		public FileHandle file;
		public FileEvent( int type, FileHandle file ) {
			super( type );
			this.file = file;
		}
	}

	public static UIEvent addLayerEvent() { return new UIEvent( EV_ADD_LAYER ); }
	public static UIEvent removeLayerEvent() { return new UIEvent( EV_REMOVE_LAYER ); }
	public static UIEvent selectLayer( MapLayer layer ) { return new LayerEvent( EV_SELECT_LAYER, layer ); }
	public static UIEvent hideLayer( MapLayer layer ) { return new LayerEvent( EV_HIDE_LAYER, layer ); }
	public static UIEvent showLayer( MapLayer layer ) { return new LayerEvent( EV_SHOW_LAYER, layer ); }
	public static UIEvent addImageEvent( FileHandle file ) { return new FileEvent( EV_ADD_IMAGE, file ); }
	public static UIEvent selectObject( MapObject object ) { return new MapObjectEvent( EV_SELECT_MAP_OBJECT, object ); }
	public static UIEvent selectImagePath( FileHandle file ) { return new FileEvent( EV_SELECT_IMAGE_PATH, file ); }
}
