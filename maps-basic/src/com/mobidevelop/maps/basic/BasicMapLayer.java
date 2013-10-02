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

import com.mobidevelop.maps.Map;
import com.mobidevelop.maps.MapLayer;
import com.mobidevelop.maps.MapObjects;
import com.mobidevelop.maps.MapProperties;

public class BasicMapLayer implements MapLayer {

	private transient Map map;

	private String name;

	private float x;

	private float y;

	private float width;

	private float height;

	private boolean visible;

	private MapObjects objects;

	private MapProperties properties;

	@Override
	public Map getMap() {
		return map;
	}

	@Override
	public void setMap(Map map) {
		this.map = map;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public void setWidth(float width) {
		this.width = width;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public void setHeight(float height) {
		this.height = height;
	}

	@Override
	public boolean getVisible() {
		return visible;
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public MapObjects getObjects() {
		return objects;
	}

	@Override
	public MapProperties getProperties() {
		return properties;
	}

	public BasicMapLayer(Map map) {
		this(map, map.getWidth(), map.getHeight());
	}

	public BasicMapLayer(Map map, float width, float height) {
		this(map, 0, 0, width, height);
	}

	public BasicMapLayer(Map map, float x, float y, float width, float height) {
		this.map = map;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.visible = true;
		this.objects = createObjects();
		this.properties = createProperties();
	}

	protected MapObjects createObjects() {
		return new BasicMapObjects();
	}

	protected MapProperties createProperties() {
		return new BasicMapProperties();
	}

}
