package com.mobidevelop.maps.basic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.ShortArray;
import com.mobidevelop.maps.Map;
import com.mobidevelop.maps.MapLayer;
import com.mobidevelop.maps.MapObject;
import com.mobidevelop.maps.objects.PolygonMapObject;
import com.mobidevelop.maps.objects.TextureRegionMapObject;

public class BasicMapRenderer {
	
	private Map map;
	
	private SpriteBatch batcher;
	
	private ShapeRenderer renderer;
	
	private float[] vertices = new  float[8];
	
	private boolean debug = true;
	
	public BasicMapRenderer(Map map) {
		this.map = map;
		this.batcher = new SpriteBatch();
		this.renderer = new ShapeRenderer();
	}
	
	private Rectangle view = new Rectangle();
	private Rectangle temp = new Rectangle();
	
	public void setView(OrthographicCamera camera) {
		batcher.setProjectionMatrix(camera.combined);
		renderer.setProjectionMatrix(camera.combined);
		float width = camera.viewportWidth * camera.zoom;
		float height = camera.viewportHeight * camera.zoom;
		view.set(camera.position.x - width / 2, camera.position.y - height / 2, width, height);
	}
	
	EarClippingTriangulator t = new EarClippingTriangulator();
	Vector2 v = new Vector2();
	boolean spriteBatchDrawing = false;
	
	int objectsTotal = 0;
	int objectsDrawn = 0;

	public void render() {
		objectsTotal = 0;
		objectsDrawn = 0;
		batcher.begin();
		float mapX = map.getX();
		float mapY = map.getY();
		for (MapLayer layer : map.getLayers()) {
			if (layer.getVisible()) {
				float layerX = mapX + layer.getX();
				float layerY = mapY + layer.getY();
				temp.set(layerX, layerY, layer.getWidth(), layer.getHeight());

				/* Skip the layer if it is completely out of the view.
				 * Because objects are not necessarily constrained to the layer bounds
				 * this could result in objects that would otherwise have been visible
				 * to be not rendered. This is not likely to be a common issue, and it
				 * can be avoided by having the layer size grow to encompass all of
				 * its objects.
				 * 
				 * TODO: Is this the desired behavior?
				 */
				if (view.overlaps(temp) || view.contains(temp)) {
					for (MapObject object : layer.getObjects()) {
						objectsTotal += 1;
						if (object.getVisible()) {
							float objectX = layerX + object.getX();
							float objectY = layerY + object.getY();
							temp.set(objectX, objectY, object.getWidth(), object.getHeight());
							
							/* Skip the object if it is completely out of the view.
							 * Because objects are not necessarily constrained to the layer bounds
							 * we only check that they are in the view, and not whether they are
							 * outside of the layer bounds.
							 * 
							 * Note this assumes that the object bounds encompass the entire object.
							 * 
							 * TODO: Is this the desired behavior?
							 */
							float rotation = object.getRotation();
							if (rotation == 0) {
								temp.set(objectX, objectY, object.getWidth(), object.getHeight());
							} else {
								float minX = Float.MAX_VALUE;
								float maxX = Float.MIN_VALUE;
								float minY = Float.MAX_VALUE;
								float maxY = Float.MIN_VALUE;
								
								float r = (float) Math.toRadians(object.getRotation());
								float c = (float) Math.cos(r);
								float s = (float) Math.sin(r);
								
								for (int i = 0; i < 4; i++) {
									switch (i) {
										case 0:
											v.set(0, 0);
											break;
										case 1:
											v.set(object.getWidth(), 0);
											break;
										case 2:
											v.set(object.getWidth(), object.getHeight());
											break;
										case 3:
											v.set(0, object.getHeight());
											break;
									}
									
									float x = objectX + c * (v.x - object.getWidth() / 2) + -s * (v.y - object.getHeight() / 2);
									float y = objectY + s * (v.x - object.getWidth() / 2) +  c * (v.y - object.getHeight() / 2);
									
									minX = Math.min(minX, x + object.getWidth() / 2);
									maxX = Math.max(maxX, x + object.getWidth() / 2);
									minY = Math.min(minY, y + object.getHeight() / 2);
									maxY = Math.max(maxY, y + object.getHeight() / 2);
								}
								temp.set(minX, minY, maxX - minX, maxY - minY);
							}
							/* TODO: It would be nice to be able to batch filled polygons here
							 * Using a custom batching solution might provide some interesting
							 * opportunities on this front. For now, we'll just draw all
							 * shapes as separate ShapeRenderer batches.
							 * 
							 * FIXME: The following is terribly inefficient.*/
							if (view.overlaps(temp) || view.contains(temp)) {
								objectsDrawn += 1;
								if (object instanceof TextureRegionMapObject) {
									TextureRegion region = map.getResources().get(((TextureRegionMapObject) object).getTextureRegionName(), TextureRegion.class);
									batcher.draw(region, objectX, objectY, object.getWidth() / 2f, object.getHeight() / 2f, object.getWidth(), object.getHeight(), 1, 1, object.getRotation());									
								}
								if (object instanceof PolygonMapObject) {
									batcher.end();
									renderer.begin(ShapeType.Filled);
									Polygon poly = new Polygon(((PolygonMapObject) object).getVertices());
									poly.setPosition(objectX,  objectY);
									poly.setRotation(object.getRotation());
									poly.setOrigin(object.getWidth() / 2f, object.getHeight() / 2f);
									float[] verts = poly.getTransformedVertices();
									ShortArray indices = t.computeTriangles(verts);	
									for (int i = 0; i < indices.size; i += 3) {
										int v1 = indices.get(i) * 2;
										int v2 = indices.get(i + 1) * 2;
										int v3 = indices.get(i + 2) * 2;
										renderer.triangle(
											verts[v1 + 0],
											verts[v1 + 1],
											verts[v2 + 0],
											verts[v2 + 1],
											verts[v3 + 0],
											verts[v3 + 1]
										);
									}
									renderer.end();
									batcher.begin();
								}
							}
						}
					}
				}
			}		
		}		
		batcher.end();
	}
	
	/** Renders debug lines and shape based objects
	 *  <p><b>Note</b>: This draws atop anything rendered previously, 
	 *  meaning shape based objects will not be drawn beneath
	 *  texture based objects on layers above them.</p> */
	public void renderDebug() {		
		renderer.begin(ShapeType.Line);
		float mapX = map.getX();
		float mapY = map.getY();
		renderer.setColor(0,0,1,1);
		renderer.rect(mapX, mapY, map.getWidth(), map.getHeight());
		for (MapLayer layer : map.getLayers()) {
			if (layer.getVisible()) {
				float layerX = mapX + layer.getX();
				float layerY = mapY + layer.getY();
				temp.set(layerX, layerY, layer.getWidth(), layer.getHeight());

				// See above for notes
				if (view.overlaps(temp) || view.contains(temp)) {
					renderer.setColor(1,0,0,1);
					renderer.rect(layerX, layerY, layer.getWidth(), layer.getHeight());
					for (MapObject object : layer.getObjects()) {
						if (object.getVisible()) {
							float objectX = layerX + object.getX();
							float objectY = layerY + object.getY();
							temp.set(objectX, objectY, object.getWidth(), object.getHeight());
							
							// See above for notes
							float rotation = object.getRotation();
							if (rotation == 0) {
								temp.set(objectX, objectY, object.getWidth(), object.getHeight());
							} else {
								float minX = Float.MAX_VALUE;
								float maxX = Float.MIN_VALUE;
								float minY = Float.MAX_VALUE;
								float maxY = Float.MIN_VALUE;
								
								float r = (float) Math.toRadians(object.getRotation());
								float c = (float) Math.cos(r);
								float s = (float) Math.sin(r);
								
								for (int i = 0; i < 4; i++) {
									switch (i) {
										case 0:
											v.set(0, 0);
											break;
										case 1:
											v.set(object.getWidth(), 0);
											break;
										case 2:
											v.set(object.getWidth(), object.getHeight());
											break;
										case 3:
											v.set(0, object.getHeight());
											break;
									}
									
									float x = objectX + c * (v.x - object.getWidth() / 2) + -s * (v.y - object.getHeight() / 2);
									float y = objectY + s * (v.x - object.getWidth() / 2) +  c * (v.y - object.getHeight() / 2);
									
									minX = Math.min(minX, x + object.getWidth() / 2);
									maxX = Math.max(maxX, x + object.getWidth() / 2);
									minY = Math.min(minY, y + object.getHeight() / 2);
									maxY = Math.max(maxY, y + object.getHeight() / 2);
								}
								temp.set(minX, minY, maxX - minX, maxY - minY);
							}
							if (view.overlaps(temp) || view.contains(temp)) {
								if (rotation == 0) {
									renderer.setColor(0,1,0,1);
									renderer.rect(objectX, objectY, object.getWidth(), object.getHeight());										
								} else {
									renderer.setColor(0,1,0,1);
									renderer.rect(temp.x, temp.y, temp.width, temp.height);
									renderer.setColor(0,0.5f,0,1);
									renderer.rect(objectX, objectY, object.getWidth(), object.getHeight(), object.getWidth() / 2, object.getHeight() / 2, object.getRotation());
								}
							}
						}
					}
				}
			}
		}
		renderer.end();		
	}

}
