package com.mobidevelop.maps.basic;

import com.mobidevelop.maps.MapLayer;
import com.mobidevelop.maps.MapObject;
import com.mobidevelop.maps.MapProperties;

public class BasicMapObject implements MapObject {

	private transient MapLayer layer;
	
	private String name;
	
	private int x;
	
	private int y;

	private int width;
	
	private int height;

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
	public int getX() {
		return x;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setHeight(int height) {
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
	
	public BasicMapObject(MapLayer layer, int x, int y) {
		this(layer, x, y, 0, 0);
	}

	public BasicMapObject(MapLayer layer, int x, int y, int width, int height) {
		this.layer = layer;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.visible = true;
		this.properties = new MapProperties();
	}
	
}
