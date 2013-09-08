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

package com.mobidevelop.maps.basic.tiled;

import com.mobidevelop.maps.Map;
import com.mobidevelop.maps.MapObjects;
import com.mobidevelop.maps.MapProperties;
import com.mobidevelop.maps.basic.BasicMapObjects;
import com.mobidevelop.maps.basic.BasicMapProperties;
import com.mobidevelop.maps.tiled.TiledMapTile;
import com.mobidevelop.maps.tiled.TiledMapTileLayer;

public class BasicTiledMapTileLayer implements TiledMapTileLayer {
	
	private transient Map map;
	
	private String name;
	
	private float x;
	
	private float y;

	private float width;
	
	private float height;

	private boolean visible;
	
	private MapObjects objects;
	
	private MapProperties properties;
	
	private int tileWidth;
	
	private int tileHeight;
	
	private int widthInTiles;
	
	private int heightInTiles;
	
	private Cell[][] cells;
	
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
		throw new UnsupportedOperationException("Cannot directly set the width.");
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public void setHeight(float height) {
		throw new UnsupportedOperationException("Cannot directly set the height.");
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

	@Override
	public int getTileWidth() {
		return tileWidth;
	}

	@Override
	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	@Override
	public int getTileHeight() {
		return tileHeight;
	}

	@Override
	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}
	
	@Override
	public int getWidthInTiles() {
		return widthInTiles;
	}

	@Override
	public void setWidthInTiles(int widthInTiles) {
		throw new UnsupportedOperationException("Cannot change the size of a BasicTiledMapTileLayer.");
	}

	@Override
	public int getHeightInTiles() {
		return heightInTiles;
	}
	
	@Override
	public void setHeightInTiles(int heightInTiles) {
		throw new UnsupportedOperationException("Cannot change the size of a BasicTiledMapTileLayer.");
	}
	
	@Override
	public TiledMapTile getTile(int x, int y) {
		return cells[y][x].tile;
	}

	@Override
	public void setTile(int x, int y, TiledMapTile tile) {
		cells[y][x].tile = tile;
	}

	@Override
	public boolean isFlipX(int x, int y) {
		Cell cell = cells[y][x];
		if (cell != null) {
			return cell.isFlipX();
		}
		return false;
	}

	@Override
	public void setFlipX(int x, int y, boolean flipX) {
		Cell cell = cells[y][x];
		if (cell == null) {
			cell = cells[y][x] = new Cell();
		}
		cell.setFlipX(flipX);
	}

	@Override
	public boolean isFlipY(int x, int y) {
		Cell cell = cells[y][x];
		if (cell != null) {
			return cell.isFlipY();
		}
		return false;
	}

	@Override
	public void setFlipY(int x, int y, boolean flipY) {
		Cell cell = cells[y][x];
		if (cell == null) {
			cell = cells[y][x] = new Cell();
		}
		cell.setFlipY(flipY);
	}

	@Override
	public int getRotation(int x, int y) {
		Cell cell = cells[y][x];
		if (cell != null) {
			return cell.getRotation();
		}		
		return 0;
	}
	
	@Override
	public void setRotation(int x, int y, int rotation) {
		Cell cell = cells[y][x];
		if (cell == null) {
			cell = cells[y][x] = new Cell();
		}
		cell.setRotation(rotation);
	}

	public Cell getCell(int x, int y) {
		return cells[y][x];
	}
	
	public void setCell(int x, int y, Cell cell) {
		cells[y][x] = cell;
	}
	
	public Cell[][] getCells() {
		return cells;
	}
	
	public BasicTiledMapTileLayer(Map map) {
		this(map, 0, 0);
	}

	public BasicTiledMapTileLayer(Map map, int tileWidth, int tileHeight) {
		this(map, tileWidth, tileHeight, 0, 0);
	}
	
	public BasicTiledMapTileLayer(Map map, int tileWidth, int tileHeight, int widthInTiles, int heightInTiles) {
		this(map, 0, 0, tileWidth, tileHeight, widthInTiles, heightInTiles);
	}
	
	public BasicTiledMapTileLayer(Map map, float x, float y, int tileWidth, int tileHeight, int widthInTiles, int heightInTiles) {
		this.map = map;
		this.x = x;
		this.y = y;		
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.widthInTiles = widthInTiles;
		this.heightInTiles = heightInTiles;		
		this.visible = true;
		this.objects = createObjects();
		this.properties = createProperties();

		this.width = tileWidth * widthInTiles;
		this.height = tileHeight * heightInTiles;		
		this.cells = new Cell[heightInTiles][widthInTiles];
	}
	
	protected MapObjects createObjects() {
		return new BasicMapObjects();
	}

	protected MapProperties createProperties() {
		return new BasicMapProperties();
	}

	public static class Cell {
		
		private TiledMapTile tile;
		
		private boolean flipX;
		
		private boolean flipY;
		
		private int rotation;

		public TiledMapTile getTile() {
			return tile;
		}

		public void setTile(TiledMapTile tile) {
			this.tile = tile;
		}

		public boolean isFlipX() {
			return flipX;
		}

		public void setFlipX(boolean flipX) {
			this.flipX = flipX;
		}

		public boolean isFlipY() {
			return flipY;
		}

		public void setFlipY(boolean flipY) {
			this.flipY = flipY;
		}

		public int getRotation() {
			return rotation;
		}

		public void setRotation(int rotation) {
			this.rotation = rotation;
		}
		
	}
	
}
