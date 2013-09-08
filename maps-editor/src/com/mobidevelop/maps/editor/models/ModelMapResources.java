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

import com.mobidevelop.maps.basic.BasicMapResources;
import com.mobidevelop.maps.editor.events.MapResourcesChangeEvent;
import com.mobidevelop.utils.events.Event;
import com.mobidevelop.utils.events.EventDispatcher;
import com.mobidevelop.utils.events.EventListener;

public class ModelMapResources extends BasicMapResources implements Model {

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
	public void put(String key, Object value) {
		super.put(key, value);
		this.dispatchEvent(new MapResourcesChangeEvent(this, value));
	}

	@Override
	public void put(String name, Object value, Class<?> type) {
		super.put(name, value, type);
		this.dispatchEvent(new MapResourcesChangeEvent(this, value));
	}
	
	@Override
	public <T> void remove(String name, Class<T> type) {
		super.remove(name, type);
		this.dispatchEvent(new MapResourcesChangeEvent(this));
	}
	
}
