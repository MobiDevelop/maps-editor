package com.mobidevelop.maps.basic;

import com.mobidevelop.maps.Map;
import com.mobidevelop.maps.MapLayer;
import com.mobidevelop.maps.MapObjects;
import com.mobidevelop.maps.MapProperties;

public class BasicMapLayer implements MapLayer {
	private transient Map map;
	
	private String name;
	
	private int x;
	
	private int y;

	private int width;
	
	private int height;

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
	
	public BasicMapLayer(Map map, int width, int height) {
		this(map, 0, 0, width, height);
	}

	public BasicMapLayer(Map map, int x, int y, int width, int height) {
		this.map = map;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.visible = true;
		this.objects = new MapObjects();
		this.properties = new MapProperties();
	}
	
}
