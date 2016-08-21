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

import java.time.Instant;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/**
 * 
 * A lightweight model for a Facebook post object.
 * 
 * @see https://developers.facebook.com/docs/graph-api/reference/v2.7/post/
 *
 */

@Value
@Builder
public class Post {
	
	public enum StatusType {
		
		mobile_status_update, 
		created_note, 
		added_photos, 
		added_video, 
		shared_story, 
		created_group, 
		created_event, 
		wall_post, 
		app_created_story, 
		published_story, 
		tagged_in_photo, 
		approved_friend
		
	}
	
	public enum Type {
		
		link, 
		status, 
		photo, 
		video, 
		offer
		
	}
	
	/**
	 * The post ID
	 */
	@NotNull
	@Size(min = 1, max = 5, message = "The id '${validatedValue}' must be between {min} and {max} characters long")
	String id;
	
	/**
	 * The caption of a link in the post (appears 
	 * beneath the name). 
	 */
	String caption;
	
	/**
	 * The time the post was initially published. 
	 * For a post about a life event, this will 
	 * be the date and time of the life event
	 */
	Instant createdTime;
	
	/**
	 * A description of a link in the post (appears 
	 * beneath the caption).
	 */
	String description;
	
	/**
	 * Information about the profile that posted 
	 * the message.
	 * 
	 */
	Profile from;
	
	/**
	 * A link to an icon representing the type of 
	 * this post.
	 */
	String icon;
	
	/**
	 * The link attached to this post.
	 */
	String link;
	
	/**
	 * The status message in the post.
	 */
	String message;
	
	/**
	 * The name of the link.
	 */
	String name;
	
	/**
	 * The ID of any uploaded photo or video attached
	 * to the post.
	 */
	String objectId;
	
	/**
	 * The ID of a parent post for this post, if it 
	 * exists. For example, if this story is a 'Your 
	 * Page was mentioned in a post' story, the 
	 * parent_id will be the original post where the 
	 * mention happened
	 */
	String parentId;
	
	/**
	 * The picture scraped from any link included 
	 * with the post.
	 */
	String picture;
	
	/**
	 * Any location information attached to the post.
	 */
	String place;
	
	/**
	 * Description of the type of a status update.
	 */
	StatusType statusType;
	
	/**
	 * Text from stories not intentionally generated 
	 * by users, such as those generated when two 
	 * people become friends, or when someone else 
	 * posts on the person's wall.
	 */
	String story;
	
	/**
	 * A string indicating the object type of this 
	 * post.
	 */
	Type type;
	
	/**
	 * The time when the post was created, last 
	 * edited or the time of the last comment that 
	 * was left on the post.
	 * 
	 * For a post about a life event, this will be 
	 * the date and time of the life event
	 */
	Instant updatedTime;
		
	/**
	 * anything else
	 */
	@Singular Map<String, Object> jsonProperties;

}
