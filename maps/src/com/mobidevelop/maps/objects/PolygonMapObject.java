package com.mobidevelop.maps.objects;

import com.mobidevelop.maps.MapObject;

public interface PolygonMapObject extends MapObject {

	public abstract float[] getVertices();

	public abstract void setVertices(float[] vertices);
	
}
