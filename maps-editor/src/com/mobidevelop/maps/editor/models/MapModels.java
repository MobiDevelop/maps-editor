/*******************************************************************************
 * Copyright 2013 See AUTHORS File
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.mobidevelop.maps.editor.models;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.files.FileHandle;
import com.mobidevelop.maps.Map;
import com.mobidevelop.maps.MapLayer;
import com.mobidevelop.maps.MapObject;
import com.mobidevelop.maps.editor.commands.MapLayerCommands;
import com.mobidevelop.maps.editor.commands.MapLayersCommands;
import com.mobidevelop.maps.editor.commands.MapObjectsCommands;
import com.mobidevelop.utils.commands.CommandManager;
import com.mobidevelop.utils.events.Event;
import com.mobidevelop.utils.events.EventDispatcher;
import com.mobidevelop.utils.events.EventListener;

public class MapModels {
	
	private static class Model implements EventDispatcher {
		
		private List<EventListener> listeners;
		
		private List<EventListener> listeners() {
			if (listeners == null) {
				listeners = new ArrayList<EventListener>();
			}
			return listeners;
		}
		
		private boolean hasListeners() {
			return listeners != null && listeners.size() > 0;
		}
		
		@Override
		public void addEventListener(EventListener listener) {
			listeners().add(listener);
		}

		@Override
		public void removeEventListener(EventListener listener) {
			if (hasListeners()) {
				listeners.remove(listener);	
			}
		}

		@Override
		public void clearEventListeners() {
			if (hasListeners()) {
				listeners.clear();			
			}
		}

		@Override
		public void dispatchEvent(Event event) {
			if (hasListeners()) {
				for (EventListener listener : listeners) {
					listener.onEvent(this, event);
				}	
			}
		}
		
	}
	
	public static class MapModel extends Model {
		
		private Map map;
		
		private String file;
			
		private MapLayersModel layersModel;
		
		private MapObjectsModel objectsModel;
		
		private CommandManager commands;
		
		public MapModel(Map map) {
			this.map = map;
			commands = new CommandManager( this );
			layersModel = new MapLayersModel( this );
			objectsModel = new MapObjectsModel( this );
		}

		@Override
		public void addEventListener( EventListener listener ) {
		
			super.addEventListener( listener );

			layersModel.addEventListener( listener );
			objectsModel.addEventListener( listener );
		}

		public String getFile() {
			return file;
		}
		public void setFile(String file) {
			this.file = file;
		}
		
		public boolean save() {
			return false;
		}
		
		public boolean saveAs(String file) {
			return false;	
		}

		public void addLayer() {
			layersModel.addLayer();
		}
	
		public void removeLayer( MapLayer layer ) {
			// deleting current layer, set layer to next nearest
			if ( layersModel.getCurrentLayer() == layer ) {
				int index = map.getLayers().getIndex( layer ) - 1;
				if ( index < 0 )
					index = 1;
				MapLayer nextLayer = map.getLayers().getLayer( index );
				layersModel.setCurrentLayer( nextLayer );
			}
			layersModel.removeLayer( layer );
		}

		public MapLayer currentLayer() {
			return layersModel.getCurrentLayer();
		}

		public int numLayers() {

			return map.getLayers().getCount();
		}

		public void addImageBrush( MapLayer layer, MapObject object, FileHandle file ) {

			objectsModel.add( layer, object, file );
		}

		public void setCurrentLayer( MapLayer layer ) {

			layersModel.setCurrentLayer( layer );
		}

		public void showLayer( MapLayer layer ) {

			layersModel.hideLayer( layer );
		}

		public void hideLayer( MapLayer layer ) {

			layersModel.showLayer( layer );
		}
	}

	public static class MapLayersModel extends Model {

		private MapModel model;
		private int currentLayer = 0;
		private int lastCreatedLayer = 0;
		
		public MapLayersModel(MapModel mapModel) {

			setMapModel( mapModel );
		}
		
		public void setCurrentLayer( MapLayer layer ) {

			currentLayer = model.map.getLayers().getIndex( layer );
			dispatchEvent( new LayerSelectedEvent( layer ) );
		}

		public MapLayer getCurrentLayer() {

			return this.model.map.getLayers().getLayer( currentLayer );
		}

		public void setMapModel(MapModel model) {
			this.model = model;
		}

		public void addLayer() {
			model.commands.execute( MapLayersCommands.add( model.map, "Layer " + ++lastCreatedLayer ) );
		}
		
		public void removeLayer(MapLayer layer) {
			model.commands.execute( MapLayersCommands.remove( model.map, layer ) );
		}

		public void hideLayer( MapLayer layer ) {
			model.commands.execute( MapLayerCommands.hide( layer ) );
		}

		public void showLayer( MapLayer layer ) {
			model.commands.execute( MapLayerCommands.show( layer ) );
		}

		public static class LayerEvent extends Event {
			public MapLayer layer;
			public LayerEvent( MapLayer layer ) {
				this.layer = layer;
			}
		}
		public static class LayerAddedEvent extends LayerEvent {
			public LayerAddedEvent( MapLayer layer ) { super( layer ); }
		}
		public static class LayerRemovedEvent extends LayerEvent {
			public LayerRemovedEvent( MapLayer layer ) { super( layer ); }
		}
		public static class LayerSelectedEvent extends LayerEvent {
			public LayerSelectedEvent( MapLayer layer ) { super( layer ); }
		}
		public static class LayerSwappedEvent extends LayerEvent {
			public MapLayer layer2;
			public LayerSwappedEvent( MapLayer layer, MapLayer layer2 ) {
				super( layer );
				this.layer2 = layer2;
			}
		}
		public static class LayerHiddenEvent extends LayerEvent {
			public LayerHiddenEvent( MapLayer layer ) { super( layer ); }
		}
		public static class LayerShownEvent extends LayerEvent {
			public LayerShownEvent( MapLayer layer ) { super( layer ); }
		}
	}

	public static class MapObjectsModel extends Model {
		
		private MapModel model;

		public MapObjectsModel( MapModel mapModel ) {

			setMapModel( mapModel );
		}

		public void setMapModel(MapModel model) {
			this.model = model;
		}

		public void add( MapLayer layer, MapObject mapObject, FileHandle file ) {

			model.commands.execute( MapObjectsCommands.add( layer, mapObject, file ) );
		}

		public static class ObjectAddedEvent extends Event {

			public MapLayer layer;
			public MapObject object;
			public FileHandle file;
			public ObjectAddedEvent( MapLayer layer, MapObject object, FileHandle file ) {

				this.layer = layer;
				this.object = object;
				this.file = file;
			}
		}
		public static class ObjectRemovedEvent extends Event {

			public MapLayer layer;
			public MapObject object;
			public ObjectRemovedEvent( MapLayer layer, MapObject object ) {

				this.layer = layer;
				this.object = object;
			}
		}
		public static class ObjectSwappedEvent extends Event {

			public MapObject object1;
			public MapObject object2;
			public ObjectSwappedEvent( MapObject object1, MapObject object2 ) {

				this.object1 = object1;
				this.object2 = object2;
			}
		}
	}
}
