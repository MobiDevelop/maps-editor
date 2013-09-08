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
import com.mobidevelop.maps.MapObjects;
import com.mobidevelop.maps.MapProperties;
import com.mobidevelop.maps.basic.BasicMapLayer;
import com.mobidevelop.maps.editor.events.MapLayerChangeEvent;
import com.mobidevelop.utils.events.Event;
import com.mobidevelop.utils.events.EventDispatcher;
import com.mobidevelop.utils.events.EventListener;

public class ModelMapLayer extends BasicMapLayer implements Model {

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
	public void setMap(Map map) {
		super.setMap(map);
		this.dispatchEvent(new MapLayerChangeEvent(this));
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		this.dispatchEvent(new MapLayerChangeEvent(this));
	}

	@Override
	public void setX(float x) {
		super.setX(x);
		this.dispatchEvent(new MapLayerChangeEvent(this));
	}

	@Override
	public void setY(float y) {
		super.setY(y);
		this.dispatchEvent(new MapLayerChangeEvent(this));
	}

	@Override
	public void setWidth(float width) {
		super.setWidth(width);
		this.dispatchEvent(new MapLayerChangeEvent(this));
	}

	@Override
	public void setHeight(float height) {
		super.setHeight(height);
		this.dispatchEvent(new MapLayerChangeEvent(this));
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		this.dispatchEvent(new MapLayerChangeEvent(this));
	}

	public ModelMapLayer(Map map) {
		super(map);
	}
	
	public ModelMapLayer(Map map, float width, float height) {
		super(map, width, height);
	}

	public ModelMapLayer(Map map, float x, float y, float width, float height) {
		super(map, x, y, width, height);
	}

	@Override
	protected MapObjects createObjects() {
		return new ModelMapObjects();
	}

	@Override
	protected MapProperties createProperties() {
		return new ModelMapProperties();
	}
	
}
