package com.mobidevelop.maps.brush;

import com.mobidevelop.maps.MapLayer;
import com.mobidevelop.maps.basic.BasicMapObject;


public class ImageBrushMapObject extends BasicMapObject {

	private String imageName;

	public ImageBrushMapObject( MapLayer layer, float x, float y, String imageName ) {

		super( layer, x, y );

		this.imageName = imageName;
	}

	public String getImageName() {

		return imageName;
	}
}
