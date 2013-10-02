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

package com.mobidevelop.maps.basic.objects;

import com.mobidevelop.maps.MapLayer;
import com.mobidevelop.maps.basic.BasicMapObject;
import com.mobidevelop.maps.objects.TextureRegionMapObject;

public class BasicTextureRegionMapObject extends BasicMapObject implements TextureRegionMapObject {

	private String textureRegionName;

	@Override
	public String getTextureRegionName() {
		return textureRegionName;
	}

	public void setTextureRegionName(String textureRegionName) {
		this.textureRegionName = textureRegionName;
	}

	public BasicTextureRegionMapObject(MapLayer layer) {
		super(layer);
	}

	public BasicTextureRegionMapObject(MapLayer layer, float x, float y) {
		super(layer, x, y);
	}

	public BasicTextureRegionMapObject(MapLayer layer, float x, float y, float width, float height) {
		super(layer, x, y, width, height);
	}

}
