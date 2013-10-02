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

package com.mobidevelop.maps.basic;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.mobidevelop.maps.MapResources;

public class BasicMapResources implements MapResources, Disposable {

	private ObjectMap<Class<?>, ObjectMap<String, Object>> data;

	public BasicMapResources() {
		data = new ObjectMap<Class<?>, ObjectMap<String, Object>>();
	}

	public <T> T get(Class<T> type) {
		ObjectMap<String, Object> typeResources = data.get(type);
		if (typeResources != null) {
			return (T) typeResources.get("default");
		}
		return null;
	}

	public <T> T get(String name, Class<T> type) {
		ObjectMap<String, Object> typeResources = data.get(type);
		if (typeResources != null) {
			return (T) typeResources.get(name);
		}
		return null;
	}

	public void put(String key, Object value) {
		put(key, value, value.getClass());
	}

	public void put(String name, Object value, Class<?> type) {
		ObjectMap<String, Object> typeResources = data.get(type);
		if (typeResources == null) {
			typeResources = new ObjectMap<String, Object>();
			data.put(type, typeResources);
		}
		typeResources.put(name, value);
	}

	@Override
	public <T> void remove(String name, Class<T> type) {
		ObjectMap<String, Object> typeResources = data.get(type);
		if (typeResources != null) {
			T object = (T) typeResources.remove(name);
			if (object instanceof Disposable) {
				((Disposable) object).dispose();
			}
		}		
	}

	@Override
	public void dispose() {
		for (ObjectMap<String, Object> entry : data.values()) {
			for (Object resource : entry.values()) {
				if (resource instanceof Disposable) ((Disposable) resource).dispose();
			}
		}
	}

}
