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

public interface MapObject {

	public abstract MapLayer getLayer();

	public abstract void setLayer(MapLayer layer);

	public abstract String getName();

	public abstract void setName(String name);
	
	public abstract float getX();

	public abstract void setX(float x);

	public abstract float getY();

	public abstract void setY(float y);

	public abstract float getWidth();

	public abstract void setWidth(float width);

	public abstract float getHeight();

	public abstract void setHeight(float height);

	public abstract float getOriginX();

	public abstract void setOriginX(float originX);

	public abstract float getOriginY();

	public abstract void setOriginY(float originY);

	public abstract float getRotation();

	public abstract void setRotation(float rotation);

	public abstract boolean getVisible();

	public abstract void setVisible(boolean visible);

	public abstract MapProperties getProperties();

}
