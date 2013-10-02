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
import com.mobidevelop.maps.Map;
import com.mobidevelop.maps.MapLayers;
import com.mobidevelop.maps.MapProperties;
import com.mobidevelop.maps.MapResources;

public class BasicMap implements Map, Disposable {

	private String name;

	private float x;

	private float y;

	private float width;

	private float height;

	private MapLayers layers;

	private MapProperties properties;

	private MapResources resources;

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
	public MapLayers getLayers() {
		return layers;		
	}

	@Override
	public MapProperties getProperties() {
		return properties;
	}

	@Override
	public MapResources getResources() {
		return resources;
	}

	public BasicMap() {
		this(0, 0);
	}

	public BasicMap(float width, float height) {
		this(0, 0, width, height);
	}

	public BasicMap(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.layers = createMapLayers();
		this.properties = createMapProperties();
		this.resources = createMapResources();
	}

	protected MapLayers createMapLayers() {
		return new BasicMapLayers();
	}

	protected MapProperties createMapProperties() {
		return new BasicMapProperties();
	}

	protected MapResources createMapResources() {
		return new BasicMapResources();
	}

	@Override
	public void dispose() {
		resources.dispose();
	}

}
