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
import java.util.Comparator;
import java.util.List;

import com.mobidevelop.maps.MapLayer;
import com.mobidevelop.maps.basic.BasicMapLayers;
import com.mobidevelop.maps.editor.events.MapLayersChangeEvent;
import com.mobidevelop.utils.events.Event;
import com.mobidevelop.utils.events.EventDispatcher;
import com.mobidevelop.utils.events.EventListener;

public class ModelMapLayers extends BasicMapLayers implements Model {

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
	
	@Override
	public void onEvent(EventDispatcher dispatcher, Event event) {
		if (event.getBubble()) {
			if (hasListeners()) {
				for (EventListener listener : listeners) {
					listener.onEvent(dispatcher, event);
				}	
			}	
		}
	}

	@Override
	public void addLayer(MapLayer layer) {
		super.addLayer(layer);
		this.dispatchEvent(new MapLayersChangeEvent(this, layer));
	}

	@Override
	public void addLayer(int index, MapLayer layer) {
		super.addLayer(index, layer);
		this.dispatchEvent(new MapLayersChangeEvent(this, layer));
	}

	@Override
	public MapLayer removeLayer(int index) {
		MapLayer layer = super.removeLayer(index);
		this.dispatchEvent(new MapLayersChangeEvent(this, layer));
		return layer;
	}

	@Override
	public void removeLayer(MapLayer layer) {
		super.removeLayer(layer);
		this.dispatchEvent(new MapLayersChangeEvent(this, layer));
	}

	@Override
	public void sortLayers() {
		super.sortLayers();
		this.dispatchEvent(new MapLayersChangeEvent(this));
	}

	@Override
	public void sortLayers(Comparator<MapLayer> comparator) {
		super.sortLayers(comparator);
		this.dispatchEvent(new MapLayersChangeEvent(this));
	}
	
	@Override
	public void swapLayers(int index1, int index2) {
		super.swapLayers(index1, index2);
		this.dispatchEvent(new MapLayersChangeEvent(this));
	}

	@Override
	public void swapLayers(MapLayer layer1, MapLayer layer2) {
		super.swapLayers(layer1, layer2);
		this.dispatchEvent(new MapLayersChangeEvent(this));
	}

	@Override
	public void clearLayers() {
		super.clearLayers();
		this.dispatchEvent(new MapLayersChangeEvent(this));
	}
	
}
