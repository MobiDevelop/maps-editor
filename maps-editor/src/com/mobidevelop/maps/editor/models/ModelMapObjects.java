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

import com.mobidevelop.maps.MapObject;
import com.mobidevelop.maps.basic.BasicMapObjects;
import com.mobidevelop.maps.editor.events.MapObjectsChangeEvent;
import com.mobidevelop.utils.events.Event;
import com.mobidevelop.utils.events.EventDispatcher;
import com.mobidevelop.utils.events.EventListener;

public class ModelMapObjects extends BasicMapObjects implements Model {

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
	public void addObject(MapObject object) {
		super.addObject(object);
		this.dispatchEvent(new MapObjectsChangeEvent(this, object));
	}

	@Override
	public void addObject(int index, MapObject object) {
		super.addObject(index, object);
		this.dispatchEvent(new MapObjectsChangeEvent(this, object));
	}

	@Override
	public MapObject removeObject(int index) {
		MapObject object = super.removeObject(index);
		this.dispatchEvent(new MapObjectsChangeEvent(this, object));
		return object;
	}

	@Override
	public void removeObject(MapObject object) {
		super.removeObject(object);
		this.dispatchEvent(new MapObjectsChangeEvent(this, object));
	}

	@Override
	public void swapObjects(int index1, int index2) {
		super.swapObjects(index1, index2);
		this.dispatchEvent(new MapObjectsChangeEvent(this));
	}

	@Override
	public void swapObjects(MapObject object1, MapObject object2) {
		super.swapObjects(object1, object2);
		this.dispatchEvent(new MapObjectsChangeEvent(this));
	}

	@Override
	public void sortObjects() {
		super.sortObjects();
		this.dispatchEvent(new MapObjectsChangeEvent(this));
	}

	@Override
	public void sortObjects(Comparator<MapObject> comparator) {
		super.sortObjects(comparator);
		this.dispatchEvent(new MapObjectsChangeEvent(this));
	}

	@Override
	public void clearObjects() {
		super.clearObjects();
		this.dispatchEvent(new MapObjectsChangeEvent(this));
	}
	
}
