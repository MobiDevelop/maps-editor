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

	public abstract int getCount();

	public abstract int getIndex(MapObject object);
	
	public abstract MapObject getObject(int index);
	
	public abstract MapObject getObject(String name);
	
	public abstract void addObject(MapObject object);

	public abstract void addObject(int index, MapObject object);

	public abstract MapObject removeObject(int index);
	
	public abstract void removeObject(MapObject object);

	public abstract void swapObjects(int index1, int index2);
	
	public abstract void swapObjects(MapObject object1, MapObject object2);

	public abstract void sortObjects();
	
	public abstract void sortObjects(Comparator<MapObject> comparator);
	
	public abstract void clearObjects();

	public abstract Iterator<MapObject> iterator();
	
}
