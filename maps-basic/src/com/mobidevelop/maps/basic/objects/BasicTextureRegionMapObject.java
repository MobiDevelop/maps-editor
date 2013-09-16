package com.mobidevelop.maps.basic.objects;

import com.mobidevelop.maps.MapLayer;
import com.mobidevelop.maps.basic.BasicMapObject;
import com.mobidevelop.maps.objects.TextureRegionMapObject;

public class BasicTextureRegionMapObject extends BasicMapObject implements TextureRegionMapObject {
	
	private String textureRegionName;
	
	@Override
	public String getTextureRegionName() {
		return textureRegionName;
	}
	
	public void setTextureRegionName(String textureRegionName) {
		this.textureRegionName = textureRegionName;
	}
	
	public BasicTextureRegionMapObject(MapLayer layer) {
		super(layer);
	}

	public BasicTextureRegionMapObject(MapLayer layer, float x, float y) {
		super(layer, x, y);
	}
	
	public BasicTextureRegionMapObject(MapLayer layer, float x, float y, float width, float height) {
		super(layer, x, y, width, height);
	}
	
}
