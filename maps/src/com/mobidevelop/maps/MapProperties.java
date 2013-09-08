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

	public boolean containsKey(String key);

	public Iterable<String> getKeys();

	public Object get(String key);

	public Boolean getAsBoolean(String key);

	public Boolean getAsBoolean(String key, Boolean defaultValue);

	public Byte getAsByte(String key);

	public Byte getAsByte(String key, Byte defaultValue);

	public Double getAsDouble(String key);

	public Double getAsDouble(String key, Double defaultValue);

	public Float getAsFloat(String key);

	public Float getAsFloat(String key, Float defaultValue);
	
	public Integer getAsInteger(String key);

	public Integer getAsInteger(String key, Integer defaultValue);

	public Long getAsLong(String key);

	public Long getAsLong(String key, Long defaultValue);

	public Short getAsShort(String key);

	public Short getAsShort(String key, Short defaultValue);

	public String getAsString(String key);

	public String getAsString(String key, String defaultValue);

	public void put(String key, Object value);

	public void putAll(MapProperties properties);

	public void remove(String key);

	public void clear();
	
}
