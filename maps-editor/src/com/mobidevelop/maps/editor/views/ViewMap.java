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
import com.mobidevelop.maps.MapLayer;
import com.mobidevelop.maps.editor.events.MapChangeEvent;
import com.mobidevelop.maps.editor.events.MapLayersChangeEvent;
import com.mobidevelop.maps.editor.models.ModelMap;
import com.mobidevelop.maps.editor.models.ModelMapLayer;
import com.mobidevelop.utils.events.Event;
import com.mobidevelop.utils.events.EventDispatcher;
import com.mobidevelop.utils.events.EventListener;

/**
 * 
 * @author Justin Shapcott
 */
public class ViewMap extends Group implements EventListener {

	private ModelMap map;

	public ViewMap(ModelMap map) {
		super();
		setMap(map);
	}

	public void setMap(ModelMap map) {
		if (this.map != null) {
			this.map.removeEventListener(this);
		}		
		this.map = map;
		if (this.map != null) {
			this.map.addEventListener(this);
			setX(this.map.getX());
			setY(this.map.getY());
			setWidth(this.map.getWidth());
			setHeight(this.map.getHeight());
		} else {
			// TODO: Clear all the things 
		}
	}

	@Override
	public void onEvent(EventDispatcher dispatcher, Event event) {
		if (event instanceof MapChangeEvent) {
			MapChangeEvent typed = (MapChangeEvent) event;
			if (map == typed.getTarget()) {
				setX(map.getX());
				setY(map.getY());
				setWidth(map.getWidth());
				setHeight(map.getHeight());
			}
		}
		if (event instanceof MapLayersChangeEvent) {
			MapLayersChangeEvent typed = (MapLayersChangeEvent) event;
			if (map.getLayers() == typed.getTarget()) {
				MapLayer layer = typed.getLayer();
				if (map.getLayers().getIndex(layer) > 0) {
					boolean newLayer = true;
					for (Actor actor : getChildren()) {
						if (actor instanceof ViewMapLayer) {
							ViewMapLayer view = (ViewMapLayer) actor;
							if (layer == view.getLayer()) {
								newLayer = false;
							}
							break;
						}
					}
					if (newLayer) {
						addActor(createView((ModelMapLayer) layer));
					}
				} else {
					for (Actor actor : getChildren()) {
						if (actor instanceof ViewMapLayer) {
							ViewMapLayer view = (ViewMapLayer) actor;
							if (layer == view.getLayer()) {
								view.setLayer(null);
								removeActor(actor);
								break;
							}
						}
					}					
				}
			}
		}
	}

	public ViewMapLayer createView(ModelMapLayer layer) {
		return new ViewMapLayer(layer);
	}

}
