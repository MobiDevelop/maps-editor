package com.mobidevelop.maps.editor.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class SlideWindow extends Window {

	private Vector2 startPos = new Vector2();
	private Vector2 goalPos = new Vector2();

	/**
	 * creates a window that slides in from screen edge determined by origin (using {@link com.badlogic.gdx.scenes.scene2d.utils.Align Align})
	 * @param skin skin file to use
	 * @param origin which edge of the screen to slide from (see {@link com.badlogic.gdx.scenes.scene2d.utils.Align Align})
	 * @param margin amount of empty space to leave around window
	 */
	public SlideWindow( Skin skin, int origin, float margin ) {

		this( skin, origin, margin, 0 );
	}

	/**
	 * creates a window that slides in from screen edge determined by origin (using {@link com.badlogic.gdx.scenes.scene2d.utils.Align Align})
	 * @param skin skin file to use
	 * @param origin which edge of the screen to slide from (see {@link com.badlogic.gdx.scenes.scene2d.utils.Align Align})
	 * @param margin amount of empty space to leave around window
	 * @param lip size of window along sliding axis (0 = fullscreen)
	 */
	public SlideWindow( Skin skin, int origin, float margin, float lip ) {

		super( "", skin );

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		float ww = w - 2*margin;
		float wh = h - 2*margin;

		// setup window with desired dimensions to slide in from edge of screen determined by origin
		if ( ( origin & Align.left ) != 0 ) {

			ww = lip > 0 ? lip : ww;
			setupWindow(	skin, ww, wh,		-( ww + 2*margin ), margin,		margin, margin );

		} else if ( ( origin & Align.right ) != 0 ) {

			ww = lip > 0 ? lip : ww;
			setupWindow(	skin, ww, wh,		w + 2*margin, margin,					w - ( ww + margin ), margin );

		} else if ( ( origin & Align.top ) != 0 ) {

			wh = lip > 0 ? lip : wh;
			setupWindow(	skin, ww, wh,		margin, h + 2*margin,				margin, h - ( wh + margin ) );

		} else if ( ( origin & Align.bottom ) != 0 ) {

			wh = lip > 0 ? lip : wh;
			setupWindow(	skin, ww, wh,		margin, -( wh + 2*margin ),		margin, margin );

		// default center
		} else {

			setupWindow( skin, ww, wh, margin, margin, margin, margin );
		}
	}

	/**
	 * creates a slide window with all properties explicitly defined
	 * @param skin
	 * @param windowWidth
	 * @param windowHeight
	 * @param startPosX
	 * @param startPosY
	 * @param goalPosX
	 * @param goalPosY
	 */
	public SlideWindow(	Skin skin, float windowWidth, float windowHeight, float startPosX, float startPosY, float goalPosX, float goalPosY ) {

		super( "", skin );

		setupWindow( skin, windowWidth, windowHeight, startPosX, startPosY, goalPosX, goalPosY );
	}

	private void setupWindow(	Skin skin, float width, float height, float startPosX, float startPosY, float goalPosX, float goalPosY ) {

		startPos.set( startPosX, startPosY );
		goalPos.set( goalPosX, goalPosY );

//		// remove listener which pushes window to front when clicked
//		removeCaptureListener( getCaptureListeners().get( 0 ) );

		// kill click events on the window
		addListener( new ClickListener() {
			@Override
			public boolean touchDown( InputEvent event, float x, float y, int pointer, int button ) {
				super.touchDown( event, x, y, pointer, button );
				return true;
			}
		} );
		setTouchable( Touchable.enabled );
		setBackground( skin.getDrawable( "default-round" ) );
		setKeepWithinStage( false );

		// add a close button
		TextButton dismisser = new TextButton( "X", skin );
		dismisser.addListener( new ClickListener() {
			@Override
			public void clicked( InputEvent event, float x, float y ) {
				hideWindow();
			}
		});
		row();
		add( dismisser ).align( Align.right );

		setSize( width, height );
		setPosition( startPos.x, startPos.y );
	}

	/**
	 * show the window with a slide and fade-in
	 */
	public void showWindow() {
		
		clearActions();
		setPosition( startPos.x, startPos.y );
		setColor( 1, 1, 1, 0 );
		addAction( Actions.parallel( Actions.moveTo( goalPos.x, goalPos.y, 0.3f, Interpolation.swingOut ), Actions.fadeIn( 0.3f ) ) );
	}

	/**
	 * hide the window with a slide and fade-out
	 */
	public void hideWindow() {

		clearActions();
		setPosition( goalPos.x, goalPos.y );
		addAction( Actions.parallel( Actions.moveTo( startPos.x, startPos.y, 0.3f ), Actions.fadeOut( 0.3f, Interpolation.exp10Out ) ) );
	}
}
