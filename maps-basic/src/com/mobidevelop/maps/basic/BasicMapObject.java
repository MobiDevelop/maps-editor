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
		this.properties = new MapProperties();
	}
	
}
