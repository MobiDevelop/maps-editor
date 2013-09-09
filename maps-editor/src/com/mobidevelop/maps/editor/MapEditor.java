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
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mobidevelop.maps.editor.commands.MapLayersCommands;
import com.mobidevelop.maps.editor.controllers.ControllerMap;
import com.mobidevelop.maps.editor.models.ModelMap;
import com.mobidevelop.maps.editor.models.ModelMapLayer;
import com.mobidevelop.maps.editor.models.ModelMapLayers;
import com.mobidevelop.maps.editor.views.ViewMapLayers;
import com.mobidevelop.utils.commands.CommandManager;

public class MapEditor implements ApplicationListener, ControllerMap {

	private SpriteBatch batch;

	private Skin skin;

	private Stage uiStage;

	private Stage viewStage;

	private ModelMap map;

	private CommandManager commands = new CommandManager();

	@Override
	public void create() {

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		skin = new Skin( Gdx.files.internal( "data/uiskin.json" ) );

		uiStage = new Stage(w, h, true, batch);
		viewStage = new Stage(w, h, false, batch);

		map = createMap();

		ViewMapLayers layers = new ViewMapLayers("Layers", skin);
		layers.setController(this);
		layers.setLayers((ModelMapLayers) map.getLayers());
		uiStage.addActor(layers);

		Gdx.input.setInputProcessor(new InputMultiplexer(uiStage, viewStage));

	}

	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		viewStage.act(Math.min(1 / 30f, Gdx.graphics.getDeltaTime()));
		viewStage.draw();
		uiStage.act(Math.min(1 / 30f, Gdx.graphics.getDeltaTime()));
		uiStage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	public ModelMap createMap() {
		ModelMap map = new ModelMap();
		ModelMapLayer layer = new ModelMapLayer(map);
		layer.setName("Layer 1");
		map.getLayers().addLayer(layer);
		return map;
	}

	@Override
	public void createLayer() {
		ModelMapLayer layer = new ModelMapLayer(map);
		layer.setName("Layer " + map.getLayers().getCount());
		commands.execute(MapLayersCommands.add(map, layer));
	}

}
