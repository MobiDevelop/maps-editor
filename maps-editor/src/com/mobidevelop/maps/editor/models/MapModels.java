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

import com.mobidevelop.maps.Map;
import com.mobidevelop.maps.MapLayer;
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
		
	}
	
	public static class MapLayersModel extends Model {

		private MapModel model;
		
		public MapLayersModel() {
			
		}
		
		public void setMapModel(MapModel model) {
			this.model = model;
		}
	
		public void addLayer(MapLayer layer) {
			this.model.map.getLayers().addLayer(layer);
			this.dispatchEvent(new LayerAddedEvent());
		}
		
		public void removeLayer(MapLayer layer) {
			this.model.map.getLayers().removeLayer(layer);
			this.dispatchEvent(new LayerRemovedEvent());
		}
		
		public static class LayerAddedEvent extends Event {
			
		}
		public static class LayerRemovedEvent extends Event {
			
		}
	}
	
	public static class MapObjectsModel extends Model {
		
		private MapLayer model;
		
	}
	
}
