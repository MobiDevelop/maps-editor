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

package com.mobidevelop.maps.basic;

import com.badlogic.gdx.utils.ObjectMap;
import com.mobidevelop.maps.MapProperties;

public class BasicMapProperties implements MapProperties {

	private ObjectMap<String, Object> data;

	public BasicMapProperties() {
		data = new ObjectMap<String, Object>();
	}

	public boolean containsKey(String key) {
		return data.containsKey(key);
	}

	public Iterable<String> getKeys() {
		return data.keys();
	}

	public Object get(String key) {
		return data.get(key);
	}

	public Boolean getAsBoolean(String key) {
		Object value = data.get(key);
		try {
			return (Boolean) value;
		} catch (ClassCastException e) {
			if (value instanceof CharSequence) {
				return Boolean.valueOf(value.toString());
			} else {
				return null;
			}
		}
	}

	public Boolean getAsBoolean(String key, Boolean defaultValue) {
		Boolean value = getAsBoolean(key);
		return value == null? defaultValue : value;
	}

	public Byte getAsByte(String key) {
		Object value = data.get(key);
		if (value != null) {
			if (value instanceof Number) {
				return ((Number) value).byteValue();
			} else {
				if (value instanceof CharSequence) {
					try {
						return Byte.valueOf(value.toString());	
					} catch (NumberFormatException e2) {
						return null;
					}
				} else {
					return null;
				}
			}			
		} else {
			return null;			
		}
	}

	public Byte getAsByte(String key, Byte defaultValue) {
		Byte value = getAsByte(key);
		return value == null? defaultValue : value;
	}

	public Double getAsDouble(String key) {
		Object value = data.get(key);
		if (value != null) {
			if (value instanceof Number) {
				return ((Number) value).doubleValue();
			} else {
				if (value instanceof CharSequence) {
					try {
						return Double.valueOf(value.toString());	
					} catch (NumberFormatException e2) {
						return null;
					}
				} else {
					return null;
				}
			}
		} else {
			return null;			
		}
	}

	public Double getAsDouble(String key, Double defaultValue) {
		Double value = getAsDouble(key);
		return value == null? defaultValue : value;
	}

	public Float getAsFloat(String key) {
		Object value = data.get(key);
		if (value != null) {
			if (value instanceof Number) {
				return ((Number) value).floatValue();
			} else {
				if (value instanceof CharSequence) {
					try {
						return Float.valueOf(value.toString());	
					} catch (NumberFormatException e2) {
						return null;
					}
				} else {
					return null;
				}
			}
		} else {
			return null;			
		}
	}

	public Float getAsFloat(String key, Float defaultValue) {
		Float value = getAsFloat(key);
		return value == null? defaultValue : value;
	}

	public Integer getAsInteger(String key) {
		Object value = data.get(key);
		if (value != null) {
			if (value instanceof Number) {
				return ((Number) value).intValue();
			} else {
				if (value instanceof CharSequence) {
					try {
						return Integer.valueOf(value.toString());	
					} catch (NumberFormatException e2) {
						return null;
					}
				} else {
					return null;
				}
			}
		} else {
			return null;			
		}
	}

	public Integer getAsInteger(String key, Integer defaultValue) {
		Integer value = getAsInteger(key);
		return value == null? defaultValue : value;
	}

	public Long getAsLong(String key) {
		Object value = data.get(key);
		if (value != null) {
			if (value instanceof Number) {
				return ((Number) value).longValue();
			} else {
				if (value instanceof CharSequence) {
					try {
						return Long.valueOf(value.toString());	
					} catch (NumberFormatException e2) {
						return null;
					}
				} else {
					return null;
				}
			}
		} else {
			return null;			
		}
	}

	public Long getAsLong(String key, Long defaultValue) {
		Long value = getAsLong(key);
		return value == null? defaultValue : value;
	}

	public Short getAsShort(String key) {
		Object value = data.get(key);
		if (value != null) {
			if (value instanceof Number) {
				return ((Number) value).shortValue();
			} else {
				if (value instanceof CharSequence) {
					try {
						return Short.valueOf(value.toString());	
					} catch (NumberFormatException e2) {
						return null;
					}
				} else {
					return null;
				}
			}
		} else {
			return null;			
		}
	}

	public Short getAsShort(String key, Short defaultValue) {
		Short value = getAsShort(key);
		return value == null? defaultValue : value;
	}

	public String getAsString(String key) {
		Object value = data.get(key);
		if (value != null) {
			return value.toString();
		} else {
			return null;			
		}
	}

	public String getAsString(String key, String defaultValue) {
		String value = getAsString(key);
		return value == null? defaultValue : value;
	}

	public void put(String key, Object value) {
		data.put(key, value);
	}

	public void putAll(MapProperties properties) {
		for (String key : properties.getKeys()) {
			data.put(key, properties.get(key));
		}
	}

	public void remove(String key) {
		data.remove(key);
	}

	public void clear() {
		data.clear();
	}

}
