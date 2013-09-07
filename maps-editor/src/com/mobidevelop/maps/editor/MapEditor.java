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

package com.mobidevelop.maps.editor;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.mobidevelop.maps.MapLayer;
import com.mobidevelop.maps.MapObject;
import com.mobidevelop.maps.basic.BasicMap;
import com.mobidevelop.maps.brush.ImageBrushMapObject;
import com.mobidevelop.maps.editor.models.MapModels.MapModel;
import com.mobidevelop.maps.editor.ui.FileDialog;
import com.mobidevelop.maps.editor.ui.ImagePicker;
import com.mobidevelop.maps.editor.ui.LayerList;
import com.mobidevelop.maps.editor.ui.SlideWindow;
import com.mobidevelop.maps.editor.ui.UIEvents;
import com.mobidevelop.maps.editor.ui.UIEvents.FileEvent;
import com.mobidevelop.maps.editor.ui.UIEvents.LayerEvent;
import com.mobidevelop.maps.editor.ui.UIEvents.MapObjectEvent;
import com.mobidevelop.maps.editor.ui.UIEvents.UIEvent;

public class MapEditor implements ApplicationListener, EventListener {

	private SpriteBatch batch;

	private Skin skin;
	private Stage uiStage;
	private LevelStage worldViewport;

	private SlideWindow fileWindow;
	private SlideWindow imageWindow;

	private ImagePicker imagePicker;
	private LayerList layerList;
	private FileDialog fileDialog;

	private MapModel mapModel;

	private Array<Disposable> trash;

	private String projectImagePath;

	@Override
	public void create() {

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		skin = new Skin( Gdx.files.internal( "data/uiskin.json" ) );

		fileDialog = new FileDialog( "Set Image Path...", Gdx.files.external( "" ), skin );

		// File Window ********************************************************************************

		final TextButton openProjectButton = new TextButton( "Set Image Path", skin );
		openProjectButton.addListener( new ClickListener() {
			@Override
			public void clicked( InputEvent event, float x, float y ) {

				fileDialog.editFileName( false );
				uiStage.addActor( fileDialog );
				super.clicked( event, x, y );
			}
		} );
		fileWindow = new SlideWindow( skin, Align.top, 10, 200 );
		fileWindow.add( openProjectButton );

		// Image Window *******************************************************************************
		// holds the image picker and layer list

		imagePicker = new ImagePicker( skin );
		layerList = new LayerList( skin );
		Table pickerTable = new Table();
		pickerTable.add( imagePicker ).pad( 10 );
		Table layerTable = new Table();
		layerTable.add( layerList ).pad( 10 );
		SplitPane splitPane = new SplitPane( pickerTable, layerTable, true, skin );
		imageWindow = new SlideWindow( skin, Align.right, 10, 200 );
		imageWindow.add( splitPane ).expand().fill().pad( 10 );

		// UI Stage ***********************************************************************************
		// holds the menu overlay, with sliding windows for different tool palettes

		uiStage = new Stage( w, h, true, batch ){
			@Override
			public boolean touchDown( int screenX, int screenY, int pointer, int button ) {

				if ( screenY < 20 )
					fileWindow.showWindow();
				else if ( screenX > Gdx.graphics.getWidth() - 20 )
					imageWindow.showWindow();

				return super.touchDown( screenX, screenY, pointer, button );
			}
		};
		uiStage.addListener( this );
		uiStage.addActor( fileWindow );
		uiStage.addActor( imageWindow );

		// World Viewport *****************************************************************************
		// the stage where the map is drawn

		worldViewport = new LevelStage( w, h, batch );

		createNewMap();

		Gdx.input.setInputProcessor( new InputMultiplexer( uiStage, worldViewport ) );

		trash = new Array<Disposable>();
		trash.add( imagePicker );
		trash.add( batch );
		trash.add( skin );
		trash.add( uiStage );
		trash.add( worldViewport );

		loadSettings();
	}

	private void createNewMap() {

		mapModel = new MapModel( new BasicMap() );
		mapModel.addEventListener( worldViewport );
		mapModel.addEventListener( layerList );

		// add default layer
		mapModel.addLayer();
		mapModel.setCurrentLayer( mapModel.currentLayer() );
	}

	@Override
	public void pause() {

		saveSettings();
	}

	@Override
	public void resume() {
	}

	private void loadSettings() {

		Settings.load();
		projectImagePath = Settings.getLastImagePath();
		if ( projectImagePath != null )
			imagePicker.loadProjectImages( Gdx.files.external( projectImagePath ) );
	}

	private void saveSettings() {

		Settings.setLastImagePath( projectImagePath );
		Settings.save();
	}

	@Override
	public void resize( int width, int height ) {
	}

	@Override
	public void render() {

		Gdx.gl.glClearColor( 0, 0, 0, 1 );
		Gdx.gl.glClear( GL10.GL_COLOR_BUFFER_BIT );

		worldViewport.act();
		worldViewport.draw();

		uiStage.act();
		uiStage.draw();
	}

	@Override
	public void dispose() {

		for ( int i = 0; i < trash.size; i++ ) {
			Disposable d = trash.get( i );
			if ( d != null )
				d.dispose();
		}
		trash.clear();
	}

	/**
	 * handles events sent by ui components to interact with the model
	 */
	@Override
	public boolean handle( Event event ) {

		if ( event instanceof UIEvent ) {

			MapLayer layer;
			MapObject mapObject;
			switch ( ((UIEvent)event).type ) {
			case UIEvents.EV_ADD_LAYER:
				mapModel.addLayer();				
				break;

			case UIEvents.EV_REMOVE_LAYER:
				// can't remove last layer
				if ( mapModel.numLayers() > 1 )
					mapModel.removeLayer( mapModel.currentLayer() );
				break;

			case UIEvents.EV_SELECT_LAYER:
				layer = ((LayerEvent)event).layer;
				mapModel.setCurrentLayer( layer );
				break;

			case UIEvents.EV_HIDE_LAYER:
				layer = ((LayerEvent)event).layer;
				mapModel.hideLayer( layer );
				break;

			case UIEvents.EV_SHOW_LAYER:
				layer = ((LayerEvent)event).layer;
				mapModel.showLayer( layer );
				break;

			case UIEvents.EV_ADD_IMAGE:
				FileEvent eai = (FileEvent)event;
				FileHandle file = eai.file;
				layer = mapModel.currentLayer();
				Vector2 v = worldViewport.getCenter();
				mapModel.addImageBrush( layer, new ImageBrushMapObject( layer, v.x, v.y, file.name() ), file );
				break;

			case UIEvents.EV_SELECT_MAP_OBJECT:
				MapObjectEvent moe = (MapObjectEvent)event;
				mapObject = moe.object;
				break;

			case UIEvents.EV_SELECT_IMAGE_PATH:
				FileEvent esip = (FileEvent)event;

				// ensure folder
				FileHandle folder = esip.file;
				if ( !folder.isDirectory() )
					folder = folder.parent();

				projectImagePath = folder.path();
				imagePicker.loadProjectImages( esip.file );
				break;

			default:
				break;
			}
			return true;
		}

		return false;
	}
}
