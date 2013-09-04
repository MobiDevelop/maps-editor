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

package com.mobidevelop.maps.editor.commands;

import com.mobidevelop.maps.MapLayer;
import com.mobidevelop.maps.MapObject;
import com.mobidevelop.maps.MapObjects;
import com.mobidevelop.utils.commands.Command;

public final class MapObjectsCommands {
	
	private MapObjectsCommands() { }
	
	public static AddObjectCommand add(MapLayer layer, MapObject object) {
		return new AddObjectCommand(layer, object);
	}
	
	public static InsertObjectCommand insert(MapLayer layer, int index, MapObject object) {
		return new InsertObjectCommand(layer, index, object);
	}
	
	public static RemoveObjectCommand remove(MapLayer layer, MapObject object) {
		return new RemoveObjectCommand(layer, object);
	}
	
	public static SwapObjectsCommand swap(MapLayer layer, MapObject object1, MapObject object2) {
		return new SwapObjectsCommand(layer, object1, object2);
	}
	
	public static class AddObjectCommand implements Command {

		private MapLayer layer;
		private MapObject object;
		
		public AddObjectCommand(MapLayer layer, MapObject object) {
			this.layer = layer;
			this.object = object;
		}
		
		@Override
		public void execute() {
			layer.getObjects().addObject(object);
		}
		
		@Override
		public void reverse() {
			layer.getObjects().removeObject(object);
		}
		
	}
	
	public static class InsertObjectCommand implements Command {

		private MapLayer layer;
		private MapObject object;
		
		private int index;

		public InsertObjectCommand(MapLayer layer, int index, MapObject object) {
			this.layer = layer;
			this.index = index;
			this.object = object;
		}
		
		@Override
		public void execute() {
			layer.getObjects().addObject(index, object);
		}
		
		@Override
		public void reverse() {
			layer.getObjects().removeObject(object);
		}
		
	}
	
	public static class RemoveObjectCommand implements Command {
		
		private MapLayer layer;
		
		private MapObject object;
		
		private int index;
		
		public RemoveObjectCommand(MapLayer layer, MapObject object) {
			this.layer = layer;
			this.object = object;
		}
		
		@Override
		public void execute() {
			MapObjects objects = layer.getObjects();
			index = objects.getIndex(object);
			objects.removeObject(index);
		}
		@Override
		public void reverse() {
			MapObjects objects = layer.getObjects();
			objects.addObject(index, object);		
		}
		
	}
	
	public static class SwapObjectsCommand implements Command {
		
		private MapLayer layer;
		private MapObject object1;
		private MapObject object2;
		
		public SwapObjectsCommand(MapLayer layer, MapObject object1, MapObject object2) {
			MapLayer layer1 = object1.getLayer();
			MapLayer layer2 = object2.getLayer();
			
			if (layer1 != layer2) {
				throw new RuntimeException("Cannot swap objects on different layers.");
			}
			
			this.layer = layer;
			this.object1 = object1;
			this.object2 = object2;
		}
		
		@Override
		public void execute() {
			layer.getObjects().swapObjects(object1, object2);
		}
		
		@Override
		public void reverse() {
			layer.getObjects().swapObjects(object1, object2);
		}
		
	}
}
