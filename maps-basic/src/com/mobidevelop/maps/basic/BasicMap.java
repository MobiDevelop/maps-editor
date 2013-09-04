package com.mobidevelop.maps.basic;

import com.mobidevelop.maps.Map;
import com.mobidevelop.maps.MapLayers;
import com.mobidevelop.maps.MapProperties;
import com.mobidevelop.maps.MapResources;

public class BasicMap implements Map {

	private String name;
	
	private int x;
	
	private int y;

	private int width;
	
	private int height;

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
	
	public BasicMap(int width, int height) {
		this(0, 0, width, height);
	}

	public BasicMap(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.layers = new MapLayers();
		this.properties = new MapProperties();
		this.resources = new MapResources();
	}
	
}
