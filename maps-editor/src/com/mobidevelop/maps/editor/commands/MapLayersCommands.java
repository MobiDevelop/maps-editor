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

import com.mobidevelop.maps.Map;
import com.mobidevelop.maps.MapLayer;
import com.mobidevelop.maps.MapLayers;
import com.mobidevelop.utils.commands.Command;

final public class MapLayersCommands {

	private MapLayersCommands() { }
	
	public static AddLayerCommand add(Map map, MapLayer layer) {
		return new AddLayerCommand(map, layer);
	}
	
	public static InsertLayerCommand insert(Map map, int index, MapLayer layer) {
		return new InsertLayerCommand(map, index, layer);
	}
	
	public static RemoveLayerCommand remove(Map map, MapLayer layer) {
		return new RemoveLayerCommand(map, layer);
	}
	
	public static SwapLayersCommand swap(Map map, MapLayer layer1, MapLayer layer2) {
		return new SwapLayersCommand(map, layer1, layer2);
	}
	
	public static class AddLayerCommand implements Command {
		private Map map;
		private MapLayer layer;
		
		public AddLayerCommand(Map map, MapLayer layer) {
			this.map = map;
			this.layer = layer;
		}
		
		@Override
		public void execute() {
			map.getLayers().addLayer(layer);
		}
		
		@Override
		public void reverse() {
			map.getLayers().removeLayer(layer);
		}
		
	}
	
	public static class InsertLayerCommand implements Command {
		
		private Map map;
		private MapLayer layer;
		
		private int index;

		public InsertLayerCommand(Map map, int index, MapLayer layer) {
			this.map = map;
			this.index = index;
			this.layer = layer;
		}
		
		@Override
		public void execute() {
			map.getLayers().addLayer(index, layer);
		}
		
		@Override
		public void reverse() {
			map.getLayers().removeLayer(layer);
		}
		
	}
	
	public static class RemoveLayerCommand implements Command {
		
		private Map map;
		private MapLayer layer;
		
		private int index;
		
		public RemoveLayerCommand(Map map, MapLayer layer) {
			this.map = map;
			this.layer = layer;
		}
		
		@Override
		public void execute() {
			MapLayers layers = map.getLayers();
			index = layers.getIndex(layer);
			layers.removeLayer(index);
		}
		@Override
		public void reverse() {
			MapLayers layers = map.getLayers();
			layers.addLayer(index, layer);		
		}
		
	}
	
	public static class SwapLayersCommand implements Command {
		
		private Map map;
		private MapLayer layer1;
		private MapLayer layer2;
		
		public SwapLayersCommand(Map map, MapLayer layer1, MapLayer layer2) {
			Map map1 = layer1.getMap();
			Map map2 = layer2.getMap();
			
			if (map1 != map2) {
				throw new RuntimeException("Cannot swap layers on different maps.");
			}
			this.map = map;
			this.layer1 = layer1;
			this.layer2 = layer2;
		}
		
		@Override
		public void execute() {
			map.getLayers().swapLayers(layer1, layer2);
		}
		
		@Override
		public void reverse() {
			map.getLayers().swapLayers(layer1, layer2);
		}
		
	}
	
}
