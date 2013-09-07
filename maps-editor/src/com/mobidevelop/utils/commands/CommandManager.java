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

package com.mobidevelop.utils.commands;

import java.util.*;

import com.mobidevelop.maps.editor.models.MapModels.MapModel;
import com.mobidevelop.utils.events.Event;


/**
 * The {@code CommandManager} class manages the execution and reversal of {@link Command Commands}.
 * 
 * @author Justin Shapcott
 */
public class CommandManager {
	
	private Deque<Command> undoStack;
	private Deque<Command> redoStack;

	private MapModel model;

	public CommandManager( MapModel model ) {
		this.model = model;
		undoStack = new ArrayDeque<Command>();
		redoStack = new ArrayDeque<Command>();
	}
	
	/**
	 * Executes the given {@link Command} and adds it to the undo stack.
	 * 
	 * @param command The {@link Command} to execute.
	 */
	public void execute(Command command) {
		Event event = command.execute();
		if ( event != null )
			model.dispatchEvent( event );
		undoStack.push(command);
		redoStack.clear();
	}
	
	/**
	 * Reverses the last executed {@link Command}.
	 * 
	 * @throws RuntimeException if the undo stack is empty.
	 */
	public void undo() {
		if (undoStack.isEmpty()) {
			throw new RuntimeException("No commands to undo.");
		}
		Command command = undoStack.pop();
		Event event = command.reverse();
		if ( event != null )
			model.dispatchEvent( event );
		redoStack.push(command);		
	}

	/**
	 * Reverses all previously executed commands up to the given count.
	 * 
	 * @throws RuntimeException if the undo stack is empty.
	 */
	public void undo(int count) {
		if (undoStack.isEmpty()) {
			throw new RuntimeException("No commands to undo.");
		}
		while (!undoStack.isEmpty() && count > 0) {
			Command command = undoStack.pop();
			Event event = command.reverse();
			if ( event != null )
				model.dispatchEvent( event );
			redoStack.push(command);
			count--;
		}		
	}
	
	/**
	 * Reverses all previously executed commands up and including the given {@link Command}.
	 * 
	 * @param command The {@link Command} to reverse to.
	 * 
	 * @throws RuntimeException if the command in not in the undo stack.
	 */
	public void undoTo(Command command) {
		if (!undoStack.contains(command)) {
			throw new RuntimeException("Command is not in the undo stack.");
		}
		while (undoStack.peek() != command) {
			undo();			
		}
		undo();
	}

	/**
	 * Executes the last reversed {@link Command}.
	 * 
	 * @throws RuntimeException if the redo stack is empty.
	 */
	public void redo() {
		if (redoStack.isEmpty()) {
			throw new RuntimeException("No commands to redo.");
		}
		Command command = redoStack.pop();
		Event event = command.execute();
		if ( event != null )
			model.dispatchEvent( event );
		undoStack.push(command);		
	}

	public void redo(int count) {
		if (redoStack.isEmpty()) {
			throw new RuntimeException("No commands to redo.");
		}
		while (!undoStack.isEmpty() && count > 0) {
			Command command = redoStack.pop();
			Event event = command.execute();
			if ( event != null )
				model.dispatchEvent( event );
			undoStack.push(command);
			count--;
		}		
	}
	
	/**
	 * Executes all previously reversed commands up and including the given {@link Command}.
	 * 
	 * @param command The {@link Command} to execute to.
	 * 
	 * @throws RuntimeException if the command in not in the undo stack.
	 */
	public void redoTo(Command command) {
		if (!redoStack.contains(command)) {
			throw new RuntimeException("Command is not in the redo stack.");
		}
		while (redoStack.peek() != command) {
			redo();			
		}
		redo();
	}
	
	/**
	 * Whether or not there are any {@link Command Commands} that can be reversed.
	 * 
	 * @return if are any commands that can be reversed.
	 */
	public boolean canUndo() {
		return undoStack.size() > 0;
	}

	/**
	 * Whether or not there are any previously reversed {@link Command Commands} that can be executed.
	 * @return if there are any previously reversed commands that executed.
	 */
	public boolean canRedo() {
		return redoStack.size() > 0;
	}
	
}
