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

package com.mobidevelop.maps;

import java.util.Comparator;
import java.util.Iterator;

public interface MapLayers extends Iterable<MapLayer> {

	public int getCount();

	public int getIndex(MapLayer layer);
	
	public MapLayer getLayer(int index);
	
	public MapLayer getLayer(String name);
	
	public void addLayer(MapLayer layer);

	public void addLayer(int index, MapLayer layer);

	public MapLayer removeLayer(int index);
	
	public void removeLayer(MapLayer layer);

	public void sortLayers();

	public void sortLayers(Comparator<MapLayer> comparator);

	public void swapLayers(int index1, int index2);

	public void swapLayers(MapLayer layer1, MapLayer layer2);

	public void clearLayers();

	public Iterator<MapLayer> iterator();
	
}
