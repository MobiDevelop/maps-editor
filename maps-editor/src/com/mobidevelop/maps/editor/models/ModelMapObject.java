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

import com.mobidevelop.maps.MapLayer;
import com.mobidevelop.maps.MapProperties;
import com.mobidevelop.maps.basic.BasicMapObject;
import com.mobidevelop.maps.editor.events.MapObjectChangeEvent;
import com.mobidevelop.utils.events.Event;
import com.mobidevelop.utils.events.EventDispatcher;
import com.mobidevelop.utils.events.EventListener;

public class ModelMapObject extends BasicMapObject implements Model {

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
	public void setLayer(MapLayer layer) {
		super.setLayer(layer);
		this.dispatchEvent(new MapObjectChangeEvent(this));
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		this.dispatchEvent(new MapObjectChangeEvent(this));
	}

	@Override
	public void setX(float x) {
		super.setX(x);
		this.dispatchEvent(new MapObjectChangeEvent(this));
	}

	@Override
	public void setY(float y) {
		super.setY(y);
		this.dispatchEvent(new MapObjectChangeEvent(this));
	}

	@Override
	public void setWidth(float width) {
		super.setWidth(width);
		this.dispatchEvent(new MapObjectChangeEvent(this));
	}

	@Override
	public void setHeight(float height) {
		super.setHeight(height);
		this.dispatchEvent(new MapObjectChangeEvent(this));
	}

	@Override
	public void setOriginX(float originX) {
		super.setOriginX(originX);
		this.dispatchEvent(new MapObjectChangeEvent(this));
	}

	@Override
	public void setOriginY(float originY) {
		super.setOriginY(originY);
		this.dispatchEvent(new MapObjectChangeEvent(this));
	}

	@Override
	public void setRotation(float rotation) {
		super.setRotation(rotation);
		this.dispatchEvent(new MapObjectChangeEvent(this));
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		this.dispatchEvent(new MapObjectChangeEvent(this));
	}

	public ModelMapObject(MapLayer layer) {
		super(layer);
	}
	
	public ModelMapObject(MapLayer layer, float x, float y) {
		super(layer, x, y);
	}

	public ModelMapObject(MapLayer layer, float x, float y, float width, float height) {
		super(layer, x, y, width, height);
	}

	@Override
	protected MapProperties createProperties() {
		ModelMapProperties properties = new ModelMapProperties();
		properties.addEventListener(this);
		return properties;
	}
	
}
