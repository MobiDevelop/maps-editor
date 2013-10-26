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

package com.mobidevelop.maps.editor.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.SnapshotArray;

/**
 * A {@code VerticalFlowGroup} lays out its children in columns, filling the
 * available vertical space and expanding horizontally as necessary. During a
 * layout pass, new columns will automatically be added when a child would
 * overflow the available vertical space.
 * 
 * @author Justin Shapcott
 */
public class VerticalFlowGroup extends WidgetGroup {

	private float prefWidth;
	private float prefHeight;
	private float lastPrefWidth;
	private boolean sizeInvalid = true;
	private float spacing = 0;

	public float getSpacing() {
		return spacing;
	}

	public void setSpacing(float spacing) {
		this.spacing = spacing;
	}

	public VerticalFlowGroup () {
		setTouchable(Touchable.childrenOnly);
	}

	public void invalidate () {
		super.invalidate();
		sizeInvalid = true;
	}

	private void computeSize () {
		prefWidth = 0;
		prefHeight = 0;
		sizeInvalid = false;
		SnapshotArray<Actor> children = getChildren();
		float groupHeight = getHeight();
		float y = 0;
		float maxWidth = 0;
		for (int i = 0, n = children.size; i < n; i++) {
			Actor child = children.get(i);
			float width = child.getWidth();
			float height = child.getHeight();
			if (child instanceof Layout) {
				Layout layout = (Layout) child;
				width = layout.getPrefWidth();
				height = layout.getPrefHeight();
			}
			if (y + height <= groupHeight) {
				prefHeight += height + spacing;
				y += height + spacing;
				maxWidth = Math.max(width, maxWidth);
			} else {
				prefWidth += maxWidth + spacing;
				maxWidth = width;
				y = height + spacing;
			}
		}
		prefWidth += maxWidth;
	}

	public void layout () {
		if (sizeInvalid) {
			computeSize();
			if (lastPrefWidth != prefWidth) {
				lastPrefWidth = prefWidth;
				invalidateHierarchy();
			}
		}
		SnapshotArray<Actor> children = getChildren();
		float groupHeight = getHeight();
		float x = 0;
		float y = getHeight();
		float maxWidth = 0;
		for (int i = 0, n = children.size; i < n; i++) {
			Actor child = children.get(i);
			float width = child.getWidth();
			float height = child.getHeight();
			if (child instanceof Layout) {
				Layout layout = (Layout) child;
				width = layout.getPrefWidth();
				height = layout.getPrefHeight();
			}
			if (y - height >= 0) {				
				maxWidth = Math.max(width, maxWidth);
			} else {
				x += maxWidth + spacing;
				maxWidth = width;
				y = groupHeight;
			}
			child.setBounds(x, y - height, width, height);
			y -= height + spacing;
		}
	}

	public float getPrefWidth () {
		if (sizeInvalid) computeSize();
		return prefWidth;
	}

	public float getPrefHeight () {
		if (sizeInvalid) computeSize();
		return prefHeight;
	}

}
