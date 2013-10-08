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

	public abstract int getCount();

	public abstract int getIndex(MapLayer layer);
	
	public abstract MapLayer getLayer(int index);
	
	public abstract MapLayer getLayer(String name);
	
	public abstract void addLayer(MapLayer layer);

	public abstract void addLayer(int index, MapLayer layer);

	public abstract MapLayer removeLayer(int index);
	
	public abstract void removeLayer(MapLayer layer);

	public abstract void sortLayers();

	public abstract void sortLayers(Comparator<MapLayer> comparator);

	public abstract void swapLayers(int index1, int index2);

	public abstract void swapLayers(MapLayer layer1, MapLayer layer2);

	public abstract void clearLayers();

	public abstract Iterator<MapLayer> iterator();
	
}
