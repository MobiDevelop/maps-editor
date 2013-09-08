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

package com.mobidevelop.maps.editor.events;

import com.mobidevelop.maps.MapProperties;

public class MapPropertiesChangeEvent extends ChangeEvent<MapProperties> {
	
	private String property;
	
	public String getProperty() {
		return property;
	}
	
	public void setProperty(String property) {
		this.property = property;
	}
	
	public MapPropertiesChangeEvent() {
		super();
	}
	
	public MapPropertiesChangeEvent(MapProperties properties) {
		super(properties);
	}
	
	public MapPropertiesChangeEvent(MapProperties properties, String property) {
		super(properties);
		this.property = property;
	}

	@Override
	public void reset() {
		super.reset();
		setProperty(null);
	}

}
