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
import com.mobidevelop.maps.editor.models.MapModels.MapLayersModel.LayerHiddenEvent;
import com.mobidevelop.maps.editor.models.MapModels.MapLayersModel.LayerShownEvent;
import com.mobidevelop.utils.commands.Command;
import com.mobidevelop.utils.events.Event;

public final class MapLayerCommands {
	
	private MapLayerCommands() { }

	public static HideLayerCommand hide(MapLayer layer) {
		return new HideLayerCommand(layer);
	}	
	
	public static ShowLayerCommand show(MapLayer layer) {
		return new ShowLayerCommand(layer);
	}	
	
	public static MoveLayerCommand move(MapLayer layer, int newX, int newY) {
		return new MoveLayerCommand(layer, newX, newY);
	}
	
	public static ResizeLayerCommand resize(MapLayer layer, int newWidth, int newHeight) {
		return new ResizeLayerCommand(layer, newWidth, newHeight);
	}
	
	public static RenameLayerCommand rename(MapLayer layer, String newName) {
		return new RenameLayerCommand(layer, newName);
	}
	
	public static class HideLayerCommand implements Command {

		private MapLayer layer;
		
		public HideLayerCommand(MapLayer layer) {
			this.layer = layer;
		}
		
		@Override
		public Event execute() {
			layer.setVisible(false);
			return new LayerHiddenEvent( layer );
		}

		@Override
		public Event reverse() {
			layer.setVisible(true);
			return new LayerShownEvent( layer );
		}
	}

	public static class ShowLayerCommand implements Command {

		private MapLayer layer;
		
		public ShowLayerCommand(MapLayer layer) {
			this.layer = layer;
		}
		
		@Override
		public Event execute() {
			layer.setVisible(true);
			return new LayerShownEvent( layer );
		}

		@Override
		public Event reverse() {
			layer.setVisible(false);
			return new LayerHiddenEvent( layer );
		}
	}

	public static class MoveLayerCommand implements Command {

		private MapLayer layer;
		private float oldX;
		private float oldY;
		private float newX;
		private float newY;
		
		public MoveLayerCommand(MapLayer layer, int newX, int newY) {
			this.layer = layer;
			this.oldX = layer.getX();
			this.oldY = layer.getY();
			this.newX = newX;
			this.newY = newY;
		}
		
		@Override
		public Event execute() {
			layer.setX(newX);
			layer.setY(newY);
			return null;
		}

		@Override
		public Event reverse() {
			layer.setX(oldX);
			layer.setY(oldY);
			return null;
		}
		
	}
	
	public static class ResizeLayerCommand implements Command {

		private MapLayer layer;
		private float oldWidth;
		private float oldHeight;
		private float newWidth;
		private float newHeight;
		
		public ResizeLayerCommand(MapLayer layer, int newWidth, int newHeight) {
			this.layer = layer;
			this.oldWidth = layer.getWidth();
			this.oldHeight = layer.getHeight();
			this.newWidth = newWidth;
			this.newHeight = newHeight;
		}
		
		@Override
		public Event execute() {
			layer.setWidth(newWidth);
			layer.setHeight(newHeight);
			return null;
		}

		@Override
		public Event reverse() {
			layer.setWidth(oldWidth);
			layer.setHeight(oldHeight);
			return null;
		}
		
	}
	
	public static class RenameLayerCommand implements Command {
		
		private MapLayer layer;
		private String newName;
		private String oldName;
		
		public RenameLayerCommand(MapLayer layer, String newName) {
			this.layer = layer;
			this.oldName = layer.getName();
			this.newName = newName;
		}
		
		@Override
		public Event execute() {
			layer.setName(newName);
			return null;
		}

		@Override
		public Event reverse() {
			layer.setName(oldName);
			return null;
		}
		
	}
}