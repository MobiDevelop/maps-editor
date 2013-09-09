package com.mobidevelop.maps.editor.views;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mobidevelop.maps.MapLayer;
import com.mobidevelop.maps.editor.events.MapLayerChangeEvent;
import com.mobidevelop.maps.editor.events.MapLayersChangeEvent;
import com.mobidevelop.maps.editor.models.ModelMapLayer;
import com.mobidevelop.maps.editor.models.ModelMapLayers;
import com.mobidevelop.utils.events.Event;
import com.mobidevelop.utils.events.EventDispatcher;
import com.mobidevelop.utils.events.EventListener;

/**
 * 
 * @author Justin Shapcott
 */
public class ViewMapLayers extends Window implements EventListener {

	private Skin skin;
	
	private VerticalGroup group;
	
	private ScrollPane scroll;
	
	private ModelMapLayers layers;
	
	public ViewMapLayers(String title, Skin skin) {
		super(title, skin);
		this.skin = skin;
		setup();
	}
	
	public ViewMapLayers(String title, Skin skin, String styleName) {
		super(title, skin, styleName);
		this.skin = skin;
		setup();
	}

	public ViewMapLayers(String title, WindowStyle style) {
		super(title, style);
	}
	
	private void setup() {
		group = new VerticalGroup();
		scroll = new ScrollPane(group, skin);
		add(scroll).colspan(2).expand().fill();
		row();
		Button add = new TextButton("+", skin);
		Button rem = new TextButton("-", skin);
		add(add).expandX().fill();
		add(rem).expandX().fill();
		
		add.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

			}
		});
		
		rem.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
			}
		});
		
	}
	
	public void setLayers(ModelMapLayers layers) {
		if (this.layers != null) {
			this.layers.removeEventListener(this);
		}
		this.layers = layers;
		if (this.layers != null) {
			this.layers.addEventListener(this);
			for (MapLayer layer : layers) {
				group.addActor(createView((ModelMapLayer) layer));
			}
		}
		invalidateHierarchy();
	}
	
	@Override
	public void onEvent(EventDispatcher dispatcher, Event event) {
		MapLayersChangeEvent typed = (MapLayersChangeEvent) event;
		if (layers == typed.getTarget()) {
			MapLayer layer = typed.getLayer();
			if (layer != null) {
				if (layers.getIndex(layer) > 0) {
					boolean newLayer = true;
					for (Actor actor : group.getChildren()) {
						if (actor instanceof ViewMapLayer) {
							ViewMapLayer view = (ViewMapLayer) actor;
							if (layer == view.getLayer()) {
								// TODO: Anything to do here?
								newLayer = false;								
							}
							break;
						}
					}
					if (newLayer) {
						group.addActorAt(0, createView((ModelMapLayer) layer));
					}
				} else {
					for (Actor actor : group.getChildren()) {
						if (actor instanceof ViewMapLayer) {
							ViewMapLayer view = (ViewMapLayer) actor;
							if (view.getLayer() == layer) {
								view.setLayer(null);
								group.removeActor(actor);
								break;
							}
						}
					}					
				}				
			} else {
				// TODO: Must have been a multiple layer operation (swap, sort, clear)
			}
		}
	}
	
	private ViewMapLayer createView(ModelMapLayer layer) {
		return new ViewMapLayer(layer, skin);
	}
	
	public class ViewMapLayer extends Table implements EventListener {

		private Skin skin;
		
		private Label label;
		
		private ModelMapLayer layer;

		public ViewMapLayer(ModelMapLayer layer, Skin skin) {
			super(skin);
			this.skin = skin;
			setup();
			this.setLayer(layer);
		}

		private void setup() {
			this.left();
			label = new Label("", skin);
			add(label);
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
				this.label.setText(layer.getName());
			} else {
				// TODO: Clear all the things
			}
		}
		
		@Override
		public void onEvent(EventDispatcher dispatcher, Event event) {
			if (event instanceof MapLayerChangeEvent) {
				MapLayerChangeEvent typed = (MapLayerChangeEvent) event;
				if (layer == typed.getTarget()) {
					label.setText(layer.getName());
				}
			}
		}
		
	}
	
}
