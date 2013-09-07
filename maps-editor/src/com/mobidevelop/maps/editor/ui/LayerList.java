package com.mobidevelop.maps.editor.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mobidevelop.maps.MapLayer;
import com.mobidevelop.maps.editor.models.MapModels.MapLayersModel.LayerAddedEvent;
import com.mobidevelop.maps.editor.models.MapModels.MapLayersModel.LayerRemovedEvent;
import com.mobidevelop.maps.editor.models.MapModels.MapLayersModel.LayerSelectedEvent;
import com.mobidevelop.maps.editor.models.MapModels.MapLayersModel.LayerSwappedEvent;
import com.mobidevelop.utils.events.Event;
import com.mobidevelop.utils.events.EventDispatcher;
import com.mobidevelop.utils.events.EventListener;


public class LayerList extends Table implements EventListener {

	private Skin skin;
	private VerticalGroup layers;
	private Label lastSelected;

	public LayerList( final Skin skin ) {

		this.skin = skin;

		// rich list box for the layers, just a vertical group of LayerActors in a ScrollPane
		layers = new VerticalGroup();
		layers.addListener( new ClickListener() {
			@Override
			public void clicked( InputEvent event, float x, float y ) {
				Actor actor = layers.hit( x, y, true );
				if ( actor instanceof Label ) {
					// hacky way to get the layer
					Label lbl = (Label) actor;
					fire( UIEvents.selectLayer( ((LayerListActor)lbl.getParent()).getLayer() ) );
				}
			}
		} );
		final ScrollPane layerScroll = new ScrollPane( layers );
		layerScroll.setOverscroll( false, true );

		// controls for add/removing layers
		final TextButton addLayerButton = new TextButton( "New Layer", skin );
		addLayerButton.addListener( new ClickListener() {
			@Override
			public void clicked( InputEvent event, float x, float y ) {
				fire( UIEvents.addLayerEvent() );
			}
		});
		final TextButton removeLayerButton = new TextButton( "Remove Layer", skin );
		removeLayerButton.addListener( new ClickListener() {
			@Override
			public void clicked( InputEvent event, float x, float y ) {
				fire( UIEvents.removeLayerEvent() );
			}
		});

		defaults().pad( 10 );
		row();
		add( layerScroll ).colspan( 2 );
		row();
		add( addLayerButton );
		add( removeLayerButton );
	}

	@Override
	public void onEvent( EventDispatcher dispatcher, Event event ) {

		// model added layer
		if ( event instanceof LayerAddedEvent ) {
			MapLayer layer = ((LayerAddedEvent)event).layer;
			LayerListActor actor = new LayerListActor( layer, skin );
			layers.addActorAt( layer.getMap().getLayers().getIndex( layer ), actor );
			return;
		}

		// model removed layer
		if ( event instanceof LayerRemovedEvent ) {
			Actor actor = getLayerActor( ((LayerRemovedEvent)event).layer );
			if ( actor != null )
				actor.remove();
			return;
		}

		// model changed selected layer
		if ( event instanceof LayerSelectedEvent ) {
			selectLayer( ((LayerSelectedEvent)event).layer );
		}

		// model swapped layers
		if ( event instanceof LayerSwappedEvent ) {
			LayerSwappedEvent evt = (LayerSwappedEvent)event;
			Actor actor1 = getLayerActor( evt.layer );
			Actor actor2 = getLayerActor( evt.layer2 );
			if ( actor1 != null && actor2 != null )
				layers.swapActor( actor1, actor2 );
			return;
		}
	}

	public LayerListActor getLayerActor( MapLayer layer ) {

		SnapshotArray<Actor> ls = layers.getChildren();
		for ( int i = 0; i < ls.size; i++ ) {
			LayerListActor l = (LayerListActor)ls.get( i );
			if ( layer == l.getLayer() )
				return l;
		}
		return null;
	}

	public void selectLayer( MapLayer layer ) {

		if ( lastSelected != null ) {
			lastSelected.setStyle( skin.get( "default", LabelStyle.class ) );
		}
		lastSelected = getLayerActor( layer ).getLabel();
		lastSelected.setStyle( skin.get( "selected", LabelStyle.class ) );
	}

	// LayerListActor will represent a layer in the listbox, with controls to handle locking/visibility, etc.
	public class LayerListActor extends Table {

		private MapLayer layer;
		private Label label;

		public LayerListActor( MapLayer layer, Skin skin ) {

			this.layer = layer;

			setTouchable( Touchable.childrenOnly );
			setName( layer.getName() );
			label = new Label( getName(), skin );
			label.setTouchable( Touchable.enabled );

			final CheckBox showHideButton = new CheckBox( "", skin );
			showHideButton.addListener( new ClickShowHideListener( showHideButton, layer ) );
			showHideButton.setChecked( true );

			row();
			add( showHideButton );
			add( label );
		}

		public Label getLabel() {
			return label;
		}

		public MapLayer getLayer() {
			return layer;
		}
	}

	public class ClickShowHideListener extends ClickListener {
		private Button button;
		private MapLayer layer;
		public ClickShowHideListener( Button button, MapLayer layer ) {
			this.button = button;
			this.layer = layer;
		}
		@Override
		public void clicked( InputEvent event, float x, float y ) {
			if ( button.isChecked() )
				fire( UIEvents.hideLayer( layer ) );
			else
				fire( UIEvents.showLayer( layer ) );
			super.clicked( event, x, y );
		}
	}
}
