package com.mobidevelop.maps.editor.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class FileDialog extends Window {

	List fileList;
	TextField fileName;
	private String lastFolder;

	public FileDialog( String title, FileHandle initialFolder, Skin skin ) {

		super( title, skin );

		setModal( true );
		setFillParent( true );

		lastFolder = initialFolder.path();
		fileList = new List( initialFolder.list(), skin );
		fileList.addListener( new ClickListener() {
			@Override
			public void clicked( InputEvent event, float x, float y ) {

				String path = lastFolder + "/" + Gdx.files.external( fileList.getSelection() ).name();
				FileHandle file = Gdx.files.external( path );
				if ( file.isDirectory() ) {
					lastFolder = path;
					loadFileList( file.list() );
				} else {
					fileName.setText( file.name() );
				}
			}
		} );
		final ScrollPane listScroll = new ScrollPane( fileList );

		final TextButton upButton = new TextButton( "Up", skin );
		upButton.addListener( new ClickListener() {
			@Override
			public void clicked( InputEvent event, float x, float y ) {
				lastFolder = Gdx.files.external( lastFolder ).parent().name();
				loadFileList( Gdx.files.external( lastFolder ).list() ); 
			}
		});

		final TextButton okButton = new TextButton( "OK", skin );
		okButton.addListener( new ClickListener() {
			@Override
			public void clicked( InputEvent event, float x, float y ) {

				String path = lastFolder + "/" + fileList.getSelection();
				FileHandle file = Gdx.files.external( path );
//				if ( file.isDirectory() ) {
//					loadFileList( file.list() );
//				} else {
					fire( UIEvents.selectImagePath( file ) );
					remove();
//				}
			}
		});
		final TextButton cancelButton = new TextButton( "Cancel", skin );
		cancelButton.addListener( new ClickListener() {
			public void clicked( InputEvent event, float x, float y ) {
				remove();
			}
		} );

		fileName = new TextField( "", skin );

		Table table = new Table( skin );
		table.defaults().fill().padTop( 10 ).padBottom( 10 );
		table.row();
		table.add( upButton ).colspan( 2 );
		table.row();
		table.add( listScroll ).expand().colspan( 2 );
		table.row();
		table.add( fileName );
		table.row();
		table.add( okButton );
		table.add( cancelButton );

		add( table );
	}

	public void editFileName( boolean enable ) {

		fileName.setDisabled( !enable );
	}

	private void loadFileList( FileHandle[] list ) {

		String[] items = new String[list.length];
		for ( int i = 0; i < items.length; i++ ) {
			items[i] = list[i].name();
		}
		fileList.setItems( items );
	}
}
