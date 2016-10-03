/*
 * Copyright (c) 2016 Andreas Gebhardt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.github.agebhar1.facelasticq.facebook.api;

import java.util.Map;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Style;

/**
 * 
 * A lightweight model for a Facebook profile object.
 * 
 * @see https://developers.facebook.com/docs/graph-api/reference/v2.7/profile/
 *
 * A profile can be a:
 *  * User
 *  * Page
 *  * Group
 *  * Event
 *  * Application
 *
 */

@Immutable
@Style(depluralize = true)
public abstract class Profile {
	
	/**
	 * The profile ID, each of User, Page, Group
	 * Event or Application has one.
	 */
	public abstract String getId();
	
	/**
	 * The name of either User, Page, Group,
	 * Event or Application.
	 */
	public abstract String getName();
	
	/**
	 * anything else
	 */
	public abstract Map<String, Object> getJsonProperties();	

}
