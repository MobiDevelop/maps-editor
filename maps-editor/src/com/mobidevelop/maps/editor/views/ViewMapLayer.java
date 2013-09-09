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

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mobidevelop.maps.MapObject;
import com.mobidevelop.maps.editor.events.MapLayerChangeEvent;
import com.mobidevelop.maps.editor.events.MapObjectsChangeEvent;
import com.mobidevelop.maps.editor.models.ModelMapLayer;
import com.mobidevelop.maps.editor.models.ModelMapObject;
import com.mobidevelop.utils.events.Event;
import com.mobidevelop.utils.events.EventDispatcher;
import com.mobidevelop.utils.events.EventListener;

/**
 * 
 * @author Justin Shapcott
 */
public class ViewMapLayer extends Group implements EventListener {

	private ModelMapLayer layer;

	public ViewMapLayer(ModelMapLayer layer) {
		super();
		this.setLayer(layer);
	}

	public ModelMapLayer getLayer() {
		return layer;
	}

	public void setLayer(ModelMapLayer layer) {
		if (this.layer != null) {
			this.layer.removeEventListener(this);
		}		
		this.layer = layer;
		if (this.layer != null) {
			this.layer.addEventListener(this);
			setX(this.layer.getX());
			setY(this.layer.getY());
			setWidth(this.layer.getWidth());
			setHeight(this.layer.getHeight());
		} else {
			// TODO: Clear all the things
		}
	}

	@Override
	public void onEvent(EventDispatcher dispatcher, Event event) {
		if (event instanceof MapLayerChangeEvent) {
			MapLayerChangeEvent typed = (MapLayerChangeEvent) event;
			if (layer == typed.getTarget()) {
				setX(layer.getX());
				setY(layer.getY());
				setWidth(layer.getWidth());
				setHeight(layer.getHeight());
			}
		}
		if (event instanceof MapObjectsChangeEvent) {
			MapObjectsChangeEvent typed = (MapObjectsChangeEvent) event;
			if (layer.getObjects() == typed.getTarget()) {
				MapObject object = typed.getObject();
				if (layer.getObjects().getIndex(object) > 0) {
					boolean newObject = true;
					for (Actor actor : getChildren()) {
						if (actor instanceof ViewMapObject) {
							ViewMapObject view = (ViewMapObject) actor;
							if (object == view.getObject()) {
								newObject = false;							
								break;
							}
						}
					}
					if (newObject) {
						addActor(createView((ModelMapObject) object));
					}
				} else {
					for (Actor actor : getChildren()) {
						if (actor instanceof ViewMapObject) {
							ViewMapObject view = (ViewMapObject) actor;
							if (object == view.getObject()) {
								view.setObject(null);
								removeActor(actor);
								break;
							}
						}
					}					
				}
			}			
		}
	}

	public ViewMapObject createView(ModelMapObject object) {
		return new ViewMapObject(object);
	}

}
