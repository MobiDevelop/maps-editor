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
import com.mobidevelop.maps.MapObject;
import com.mobidevelop.maps.MapObjects;

public class BasicMapObjects implements MapObjects {

	private Array<MapObject> data;

	public BasicMapObjects() {
		data = new Array<MapObject>();
	}

	@Override	
	public int getCount() {
		return data.size;
	}

	@Override
	public int getIndex(MapObject object) {
		return data.indexOf(object, true);
	}

	@Override
	public MapObject getObject(int index) {
		return data.get(index);
	}

	@Override
	public MapObject getObject(String name) {
		for (int i = 0, j = data.size; i < j; i++) {
			MapObject object = data.get(i);
			if (name.equals(object.getName())) {
				return object;
			}
		}
		return null;
	}

	@Override
	public void addObject(MapObject object) {
		data.add(object);
	}

	@Override
	public void addObject(int index, MapObject object) {
		data.insert(index, object);
	}

	@Override
	public MapObject removeObject(int index) {
		return data.removeIndex(index);
	}

	@Override
	public void removeObject(MapObject object) {
		data.removeValue(object, true);
	}

	@Override
	public void swapObjects(int index1, int index2) {
		MapObject object1 = data.get(index1);
		MapObject object2 = data.get(index2);
		data.set(index1, object2);
		data.set(index2, object1);
	}

	@Override
	public void swapObjects(MapObject object1, MapObject object2) {
		int index1 = data.indexOf(object1, true);
		int index2 = data.indexOf(object2, true);
		data.set(index1, object2);
		data.set(index2, object1);
	}

	@Override
	public void sortObjects() {
		data.sort();
	}

	@Override
	public void sortObjects(Comparator<MapObject> comparator) {
		data.sort(comparator);
	}

	@Override
	public void clearObjects() {
		data.clear();
	}

	public Iterable<MapObject> selectObjects(Predicate<MapObject> predicate) {
		return data.select(predicate);		
	}

	@Override
	public Iterator<MapObject> iterator() {
		return data.iterator();
	}

}
