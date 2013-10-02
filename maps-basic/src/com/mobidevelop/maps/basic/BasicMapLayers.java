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

package com.mobidevelop.maps.basic;

import java.util.Comparator;
import java.util.Iterator;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Predicate;
import com.mobidevelop.maps.MapLayer;
import com.mobidevelop.maps.MapLayers;

public class BasicMapLayers implements MapLayers {

	private Array<MapLayer> data;

	public BasicMapLayers() {
		data = new Array<MapLayer>();
	}

	@Override
	public int getCount() {
		return data.size;
	}

	@Override
	public int getIndex(MapLayer layer) {
		return data.indexOf(layer, true);
	}

	@Override
	public MapLayer getLayer(int index) {
		return data.get(index);
	}

	@Override
	public MapLayer getLayer(String name) {
		for (int i = 0, j = data.size; i < j; i++) {
			MapLayer layer = data.get(i);
			if (name.equals(layer.getName())) {
				return layer;
			}
		}
		return null;
	}

	@Override
	public void addLayer(MapLayer layer) {
		data.add(layer);
	}

	@Override
	public void addLayer(int index, MapLayer layer) {
		data.insert(index, layer);
	}

	@Override
	public MapLayer removeLayer(int index) {
		return data.removeIndex(index);
	}

	@Override
	public void removeLayer(MapLayer layer) {
		data.removeValue(layer, true);
	}

	@Override
	public void sortLayers() {
		data.sort();
	}

	@Override
	public void sortLayers(Comparator<MapLayer> comparator) {
		data.sort(comparator);
	}

	@Override
	public void swapLayers(int index1, int index2) {
		MapLayer layer1 = data.get(index1);
		MapLayer layer2 = data.get(index2);
		data.set(index1, layer2);
		data.set(index2, layer1);
	}

	@Override
	public void swapLayers(MapLayer layer1, MapLayer layer2) {
		int index1 = data.indexOf(layer1, true);
		int index2 = data.indexOf(layer2, true);
		data.set(index1, layer2);
		data.set(index2, layer1);
	}

	@Override
	public void clearLayers() {
		data.clear();
	}

	public Iterable<MapLayer> selectLayers(Predicate<MapLayer> predicate) {
		return data.select(predicate);		
	}

	@Override
	public Iterator<MapLayer> iterator() {
		return data.iterator();
	}

}
