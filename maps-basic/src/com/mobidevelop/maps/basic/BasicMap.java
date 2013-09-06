package com.mobidevelop.maps.basic;

import com.mobidevelop.maps.Map;
import com.mobidevelop.maps.MapLayers;
import com.mobidevelop.maps.MapProperties;
import com.mobidevelop.maps.MapResources;

public class BasicMap implements Map {

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
		this.layers = new MapLayers();
		this.properties = new MapProperties();
		this.resources = new MapResources();
	}

	@Override
	public void dispose() {
		resources.dispose();
	}
	
}
