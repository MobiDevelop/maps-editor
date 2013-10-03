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

import com.mobidevelop.maps.MapLayer;
import com.mobidevelop.maps.MapObject;
import com.mobidevelop.maps.MapProperties;

public class BasicMapObject implements MapObject {

	private transient MapLayer layer;

	private String name;

	private float x;

	private float y;

	private float width;

	private float height;

	private float originX;

	private float originY;

	private float rotation;

	private boolean visible;

	private MapProperties properties;

	@Override
	public MapLayer getLayer() {
		return layer;
	}

	@Override
	public void setLayer(MapLayer layer) {
		this.layer = layer;
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

	public float getOriginX() {
		return originX;
	}

	public void setOriginX(float originX) {
		this.originX = originX;
	}

	public float getOriginY() {
		return originY;
	}

	public void setOriginY(float originY) {
		this.originY = originY;
	}

	@Override
	public float getRotation() {
		return rotation;
	}

	@Override
	public void setRotation(float rotation) {
		this.rotation = rotation;
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
	public MapProperties getProperties() {
		return properties;
	}

	public BasicMapObject(MapLayer layer) {
		this(layer, 0, 0);
	}

	public BasicMapObject(MapLayer layer, float x, float y) {
		this(layer, x, y, 0, 0);
	}

	public BasicMapObject(MapLayer layer, float x, float y, float width, float height) {
		this.layer = layer;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.visible = true;
		this.properties = createProperties();
	}

	protected MapProperties createProperties() {
		return new BasicMapProperties();
	}

}
