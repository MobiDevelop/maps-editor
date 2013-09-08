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

import com.mobidevelop.maps.MapResources;

public class MapResourcesChangeEvent extends ChangeEvent<MapResources> {

	private Object resource;
	
	public Object getResource() {
		return resource;
	}

	public void setResource(Object resource) {
		this.resource = resource;
	}

	public MapResourcesChangeEvent() {
		super();
	}

	public MapResourcesChangeEvent(MapResources resources) {
		super(resources);
	}

	public MapResourcesChangeEvent(MapResources resources, Object resource) {
		super(resources);
		this.resource = resource;
	}

	@Override
	public void reset() {
		super.reset();
		setResource(null);
	}

}
