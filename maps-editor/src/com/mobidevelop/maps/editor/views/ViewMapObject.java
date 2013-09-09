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

package com.mobidevelop.maps.editor.views;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.mobidevelop.maps.editor.events.MapObjectChangeEvent;
import com.mobidevelop.maps.editor.models.ModelMapObject;
import com.mobidevelop.utils.events.Event;
import com.mobidevelop.utils.events.EventDispatcher;
import com.mobidevelop.utils.events.EventListener;

/**
 * 
 * @author Justin Shapcott
 */
public class ViewMapObject extends Group implements EventListener {

	private ModelMapObject object;

	public ViewMapObject(ModelMapObject object) {
		super();
		this.setObject(object);
	}

	public ModelMapObject getObject() {
		return object;
	}
	public void setObject(ModelMapObject object) {
		if (this.object != null) {
			this.object.removeEventListener(this);
		}		
		this.object = object;
		if (this.object != null) {
			this.object.addEventListener(this);		
			setX(this.object.getX());
			setY(this.object.getY());
			setWidth(this.object.getWidth());
			setHeight(this.object.getHeight());
		} else {
			// TODO: Clear all the things
		}
	}

	@Override
	public void onEvent(EventDispatcher dispatcher, Event event) {
		if (event instanceof MapObjectChangeEvent) {
			MapObjectChangeEvent typed = (MapObjectChangeEvent) event;
			if (object == typed.getTarget()) {
				setX(object.getX());
				setY(object.getY());
				setWidth(object.getWidth());
				setHeight(object.getHeight());
			}
		}
	}

}
