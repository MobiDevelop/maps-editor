package com.mobidevelop.maps.basic.tiled;

import com.mobidevelop.maps.Map;
import com.mobidevelop.maps.MapObjects;
import com.mobidevelop.maps.MapProperties;
import com.mobidevelop.maps.tiled.TiledMapTile;
import com.mobidevelop.maps.tiled.TiledMapTileLayer;

public class BasicTiledMapTileLayer implements TiledMapTileLayer {
	
	private transient Map map;
	
	private String name;
	
	private int x;
	
	private int y;

	private int width;
	
	private int height;

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
		throw new UnsupportedOperationException("Cannot directly set the width.");
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setHeight(int height) {
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
		
		return null;
	}

	@Override
	public void setTile(int x, int y, TiledMapTile tile) {
		
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
	
	public BasicTiledMapTileLayer(Map map, int x, int y, int tileWidth, int tileHeight, int widthInTiles, int heightInTiles) {
		this.map = map;
		this.x = x;
		this.y = y;		
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.widthInTiles = widthInTiles;
		this.heightInTiles = heightInTiles;		
		this.visible = true;
		this.objects = new MapObjects();
		this.properties = new MapProperties();

		this.width = tileWidth * widthInTiles;
		this.height = tileHeight * heightInTiles;		
		this.cells = new Cell[heightInTiles][widthInTiles];
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
