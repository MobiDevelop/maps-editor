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

package com.mobidevelop.maps.editor.events;

import com.mobidevelop.utils.events.Event;

public class ChangeEvent<T> extends Event {

	private T target;
	
	public T getTarget() {
		return target;	
	}
	
	public void setTarget(T target) {
		this.target = target;
	}
	
	public ChangeEvent() {
		
	}
	
	public ChangeEvent(T target) {
		this.target = target;
	}
	
	public void reset() {
		setBubble(true);
		setStopped(false);
		setCanceled(false);
		setTarget(null);
	}

}
