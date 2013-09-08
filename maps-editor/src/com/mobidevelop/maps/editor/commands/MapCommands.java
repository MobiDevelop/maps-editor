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
import com.mobidevelop.utils.commands.Command;

public class MapCommands {

	public static MoveMapCommand move(Map map, int newX, int newY) {
		return new MoveMapCommand(map, newX, newY);
	}
	
	public static ResizeMapCommand resize(Map map, int newWidth, int newHeight) {
		return new ResizeMapCommand(map, newWidth, newHeight);
	}
	
	public static RenameMapCommand rename(Map map, String newName) {
		return new RenameMapCommand(map, newName);
	}

	public static class MoveMapCommand implements Command {

		private Map map;
		private float oldX;
		private float oldY;
		private float newX;
		private float newY;
		
		public MoveMapCommand(Map map, float newX, float newY) {
			this.map = map;
			this.oldX = map.getX();
			this.oldY = map.getY();
			this.newX = newX;
			this.newY = newY;
		}
		
		@Override
		public void execute() {
			map.setX(newX);
			map.setY(newY);			
		}

		@Override
		public void reverse() {
			map.setX(oldX);
			map.setY(oldY);
		}
		
	}
	
	public static class ResizeMapCommand implements Command {

		private Map map;
		private float oldWidth;
		private float oldHeight;
		private float newWidth;
		private float newHeight;
		
		public ResizeMapCommand(Map map, int newWidth, int newHeight) {
			this.map = map;
			this.oldWidth = map.getWidth();
			this.oldHeight = map.getHeight();
			this.newWidth = newWidth;
			this.newHeight = newHeight;
		}
		
		@Override
		public void execute() {
			map.setWidth(newWidth);
			map.setHeight(newHeight);			
		}

		@Override
		public void reverse() {
			map.setWidth(oldWidth);
			map.setHeight(oldHeight);
		}
		
	}
	
	public static class RenameMapCommand implements Command {
		
		private Map map;
		private String newName;
		private String oldName;
		
		public RenameMapCommand(Map map, String newName) {
			this.map = map;
			this.oldName = map.getName();
			this.newName = newName;
		}
		
		@Override
		public void execute() {
			map.setName(newName);
		}

		@Override
		public void reverse() {
			map.setName(oldName);
		}
		
	}	
	
}
