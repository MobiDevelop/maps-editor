package com.mobidevelop.maps.basic.objects;

import com.badlogic.gdx.math.Polygon;
import com.mobidevelop.maps.MapLayer;
import com.mobidevelop.maps.basic.BasicMapObject;
import com.mobidevelop.maps.objects.PolygonMapObject;

public class BasicPolygonMapObject extends BasicMapObject implements PolygonMapObject {

	private Polygon polygon;

	public float[] getVertices() {
		return polygon.getVertices();		
	}
	
	public void setVertices(float[] vertices) {
		polygon.setVertices(vertices);
	}
	
	public BasicPolygonMapObject(MapLayer layer) {
		super(layer);
		this.polygon = new Polygon();
	}

	public BasicPolygonMapObject(MapLayer layer, float x, float y) {
		super(layer, x, y);
		this.polygon = new Polygon();
	}
	
	public BasicPolygonMapObject(MapLayer layer, float x, float y, float width, float height) {
		super(layer, x, y, width, height);
		this.polygon = new Polygon();
	}
	
}
