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

public interface MapObjects extends Iterable<MapObject> {

	public int getCount();

	public int getIndex(MapObject object);
	
	public MapObject getObject(int index);
	
	public MapObject getObject(String name);
	
	public void addObject(MapObject object);

	public void addObject(int index, MapObject object);

	public MapObject removeObject(int index);
	
	public void removeObject(MapObject object);

	public void swapObjects(int index1, int index2);
	
	public void swapObjects(MapObject object1, MapObject object2);

	public void sortObjects();
	
	public void sortObjects(Comparator<MapObject> comparator);
	
	public void clearObjects();

	public Iterator<MapObject> iterator();
	
}
