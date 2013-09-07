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

import com.mobidevelop.maps.MapObject;
import com.mobidevelop.utils.commands.Command;
import com.mobidevelop.utils.events.Event;

public final class MapObjectCommands {
	
	private MapObjectCommands() { }
	
	public static HideObjectCommand hide(MapObject object) {
		return new HideObjectCommand(object);
	}	
	
	public static MoveObjectCommand move(MapObject object, float newX, float newY) {
		return new MoveObjectCommand(object, newX, newY);
	}
	
	public static ResizeObjectCommand resize(MapObject object, float newWidth, float newHeight) {
		return new ResizeObjectCommand(object, newWidth, newHeight);
	}
	
	public static RenameObjectCommand rename(MapObject object, String newName) {
		return new RenameObjectCommand(object, newName);
	}
	
	public static ShowObjectCommand show(MapObject object) {
		return new ShowObjectCommand(object);
	}	
	
	public static class HideObjectCommand implements Command {

		private MapObject object;
		
		public HideObjectCommand(MapObject object) {
			this.object = object;
		}
		
		@Override
		public Event execute() {
			object.setVisible(false);
			return null;
		}

		@Override
		public Event reverse() {
			object.setVisible(true);
			return null;
		}
		
	}
	
	public static class MoveObjectCommand implements Command {

		private MapObject object;
		private float oldX;
		private float oldY;
		private float newX;
		private float newY;
		
		public MoveObjectCommand(MapObject object, float newX, float newY) {
			this.object = object;
			this.oldX = object.getX();
			this.oldY = object.getY();
			this.newX = newX;
			this.newY = newY;
		}
		
		@Override
		public Event execute() {
			object.setX(newX);
			object.setY(newY);
			return null;
		}

		@Override
		public Event reverse() {
			object.setX(oldX);
			object.setY(oldY);
			return null;
		}
		
	}
	
	public static class ResizeObjectCommand implements Command {

		private MapObject object;
		private float oldWidth;
		private float oldHeight;
		private float newWidth;
		private float newHeight;
		
		public ResizeObjectCommand(MapObject object, float newWidth, float newHeight) {
			this.object = object;
			this.oldWidth = object.getWidth();
			this.oldHeight = object.getHeight();
			this.newWidth = newWidth;
			this.newHeight = newHeight;
		}
		
		@Override
		public Event execute() {
			object.setWidth(newWidth);
			object.setHeight(newHeight);			
			return null;
		}

		@Override
		public Event reverse() {
			object.setWidth(oldWidth);
			object.setHeight(oldHeight);
			return null;
		}
		
	}
	
	public static class RenameObjectCommand implements Command {
		
		private MapObject object;
		private String newName;
		private String oldName;
		
		public RenameObjectCommand(MapObject object, String newName) {
			this.object = object;
			this.oldName = object.getName();
			this.newName = newName;
		}
		
		@Override
		public Event execute() {
			object.setName(newName);
			return null;
		}

		@Override
		public Event reverse() {
			object.setName(oldName);
			return null;
		}
		
	}
	
	public static class ShowObjectCommand implements Command {

		private MapObject object;
		
		public ShowObjectCommand(MapObject object) {
			this.object = object;
		}
		
		@Override
		public Event execute() {
			object.setVisible(true);
			return null;
		}

		@Override
		public Event reverse() {
			object.setVisible(false);
			return null;
		}
		
	}
	
}
