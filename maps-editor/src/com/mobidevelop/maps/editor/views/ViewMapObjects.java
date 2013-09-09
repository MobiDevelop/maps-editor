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
import com.mobidevelop.maps.MapObject;
import com.mobidevelop.maps.editor.events.MapObjectChangeEvent;
import com.mobidevelop.maps.editor.events.MapObjectsChangeEvent;
import com.mobidevelop.maps.editor.models.ModelMapObject;
import com.mobidevelop.maps.editor.models.ModelMapObjects;
import com.mobidevelop.utils.events.Event;
import com.mobidevelop.utils.events.EventDispatcher;
import com.mobidevelop.utils.events.EventListener;

/**
 * 
 * @author Justin Shapcott
 */
public class ViewMapObjects extends Window implements EventListener {

	private Skin skin;

	private VerticalGroup group;

	private ScrollPane scroll;

	private ModelMapObjects objects;

	public ViewMapObjects(String title, Skin skin) {
		super(title, skin);
		this.skin = skin;
		setup();
	}

	public ViewMapObjects(String title, Skin skin, String styleName) {
		super(title, skin, styleName);
		this.skin = skin;
		setup();
	}

	public ViewMapObjects(String title, WindowStyle style) {
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

	public void setObjects(ModelMapObjects objects) {
		if (this.objects != null) {
			this.objects.removeEventListener(this);
		}
		this.objects = objects;
		if (this.objects != null) {
			this.objects.addEventListener(this);
			for (MapObject object : objects) {
				group.addActor(createView((ModelMapObject) object));
			}
		}
		invalidateHierarchy();
	}

	@Override
	public void onEvent(EventDispatcher dispatcher, Event event) {
		MapObjectsChangeEvent typed = (MapObjectsChangeEvent) event;
		if (objects == typed.getTarget()) {
			MapObject object = typed.getObject();
			if (object != null) {
				if (objects.getIndex(object) > 0) {
					boolean newObject = true;
					for (Actor actor : group.getChildren()) {
						if (actor instanceof ViewMapObject) {
							ViewMapObject view = (ViewMapObject) actor;
							if (object == view.getObject()) {
								// TODO: Anything to do here?
								newObject = false;								
							}
							break;
						}
					}
					if (newObject) {
						group.addActorAt(0, createView((ModelMapObject) object));
					}
				} else {
					for (Actor actor : group.getChildren()) {
						if (actor instanceof ViewMapObject) {
							ViewMapObject view = (ViewMapObject) actor;
							if (view.getObject() == object) {
								view.setObject(null);
								group.removeActor(actor);
								break;
							}
						}
					}					
				}				
			} else {
				// TODO: Must have been a multiple object operation (swap, sort, clear)
			}
		}
	}

	private ViewMapObject createView(ModelMapObject object) {
		return new ViewMapObject(object, skin);
	}

	public class ViewMapObject extends Table implements EventListener {

		private Skin skin;

		private Label label;

		private ModelMapObject object;

		public ViewMapObject(ModelMapObject object, Skin skin) {
			super(skin);
			this.skin = skin;
			setup();
			this.setObject(object);
		}

		private void setup() {
			this.left();
			label = new Label("", skin);
			add(label);
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
				this.label.setText(object.getName());
			} else {
				// TODO: Clear all the things
			}
		}

		@Override
		public void onEvent(EventDispatcher dispatcher, Event event) {
			if (event instanceof MapObjectChangeEvent) {
				MapObjectChangeEvent typed = (MapObjectChangeEvent) event;
				if (object == typed.getTarget()) {
					label.setText(object.getName());
				}
			}
		}
		
	}
	
}
