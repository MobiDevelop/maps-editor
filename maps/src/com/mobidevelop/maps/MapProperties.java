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

package com.mobidevelop.maps;

public interface MapProperties {

	public abstract boolean containsKey(String key);

	public abstract Iterable<String> getKeys();

	public abstract Object get(String key);

	public abstract Boolean getAsBoolean(String key);

	public abstract Boolean getAsBoolean(String key, Boolean defaultValue);

	public abstract Byte getAsByte(String key);

	public abstract Byte getAsByte(String key, Byte defaultValue);

	public abstract Double getAsDouble(String key);

	public abstract Double getAsDouble(String key, Double defaultValue);

	public abstract Float getAsFloat(String key);

	public abstract Float getAsFloat(String key, Float defaultValue);
	
	public abstract Integer getAsInteger(String key);

	public abstract Integer getAsInteger(String key, Integer defaultValue);

	public abstract Long getAsLong(String key);

	public abstract Long getAsLong(String key, Long defaultValue);

	public abstract Short getAsShort(String key);

	public abstract Short getAsShort(String key, Short defaultValue);

	public abstract String getAsString(String key);

	public abstract String getAsString(String key, String defaultValue);

	public abstract void put(String key, Object value);

	public abstract void putAll(MapProperties properties);

	public abstract void remove(String key);

	public abstract void clear();
	
}
