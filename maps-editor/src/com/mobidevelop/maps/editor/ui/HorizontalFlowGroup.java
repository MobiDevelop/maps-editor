package com.mobidevelop.maps.editor.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.SnapshotArray;

public class HorizontalFlowGroup extends WidgetGroup {

	private float prefWidth;
	private float prefHeight;
	private float lastPrefHeight;
	private boolean sizeInvalid = true;
	private float spacing = 0;
	
	public float getSpacing() {
		return spacing;
	}
	public void setSpacing(float spacing) {
		this.spacing = spacing;
	}
	
	public HorizontalFlowGroup () {
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
		float groupWidth = getWidth();
		float x = 0;
		float maxHeight = 0;
		for (int i = 0, n = children.size; i < n; i++) {
			Actor child = children.get(i);
			float width = child.getWidth();
			float height = child.getHeight();
			if (child instanceof Layout) {
				Layout layout = (Layout) child;
				width = layout.getPrefWidth();
				height = layout.getPrefHeight();
			}
			if (x + width <= groupWidth) {
				prefWidth += width + spacing;
				x += width + spacing;
				maxHeight = Math.max(height, maxHeight);
			} else {
				prefHeight += maxHeight + spacing;
				maxHeight = height;
				x = width + spacing;
			}
		}
		prefHeight += maxHeight;
	}
	
	public void layout () {
		if (sizeInvalid) {
			computeSize();
			if (lastPrefHeight != prefHeight) {
				lastPrefHeight = prefHeight;
				invalidateHierarchy();
			}
		}
		SnapshotArray<Actor> children = getChildren();
		float groupWidth = getWidth();
		float x = 0;
		float y = getHeight();
		float maxHeight = 0;
		for (int i = 0, n = children.size; i < n; i++) {
			Actor child = children.get(i);
			float width = child.getWidth();
			float height = child.getHeight();
			if (child instanceof Layout) {
				Layout layout = (Layout) child;
				width = layout.getPrefWidth();
				height = layout.getPrefHeight();
			}
			if (x + width <= groupWidth) {				
				maxHeight = Math.max(height, maxHeight);
			} else {
				y -= maxHeight + spacing;
				maxHeight = height;
				x = 0;
			}
			child.setBounds(x, y - height, width, height);
			x += width + spacing;
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
