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

package com.mobidevelop.maps.tiled;

import com.mobidevelop.maps.MapLayer;

public interface TiledMapTileLayer extends MapLayer {
	
	public abstract int getTileWidth();
	
	public abstract void setTileWidth(int tileWidth);
	
	public abstract int getTileHeight();
	
	public abstract void setTileHeight(int tileHeight);
	
	public abstract int getWidthInTiles();
	
	public abstract void setWidthInTiles(int widthInTiles);
	
	public abstract int getHeightInTiles();
	
	public abstract void setHeightInTiles(int heightInTiles);
	
	public abstract TiledMapTile getTile(int x, int y);
	
	public abstract void setTile(int x, int y, TiledMapTile tile);
	
	public abstract boolean isFlipX(int x, int y);
	
	public abstract void setFlipX(int x, int y, boolean flipX);
	
	public abstract boolean isFlipY(int x, int y);
	
	public abstract void setFlipY(int x, int y, boolean flipY);
	
	public abstract int getRotation(int x, int y);
	
	public abstract void setRotation(int x, int y, int rotation);
	
}
