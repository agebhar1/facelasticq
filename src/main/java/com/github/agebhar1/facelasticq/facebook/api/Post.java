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

import javax.annotation.Nullable;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Style;

@Immutable
@Style(depluralize = true)
public abstract class Post {

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
	public abstract String getId();
	
	/**
	 * The caption of a link in the post (appears 
	 * beneath the name). 
	 */
	@Nullable 
	public abstract String getCaption();
	
	/**
	 * The time the post was initially published. 
	 * For a post about a life event, this will 
	 * be the date and time of the life event
	 */
	public abstract Instant getCreatedTime();
	
	/**
	 * A description of a link in the post (appears 
	 * beneath the caption).
	 */
	@Nullable 
	public abstract String getDescription();
	
	/**
	 * Information about the profile that posted 
	 * the message.
	 * 
	 */
	public abstract Profile getFrom();
	
	/**
	 * A link to an icon representing the type of 
	 * this post.
	 */
	@Nullable 
	public abstract String getIcon();
	
	/**
	 * The link attached to this post.
	 */
	@Nullable 
	public abstract String getLink();
	
	/**
	 * The status message in the post.
	 */
	@Nullable 
	public abstract String getMessage();
	
	/**
	 * The name of the link.
	 */
	@Nullable 
	public abstract String getName();
	
	/**
	 * The ID of any uploaded photo or video attached
	 * to the post.
	 */
	@Nullable 
	public abstract String getObjectId();
	
	/**
	 * The ID of a parent post for this post, if it 
	 * exists. For example, if this story is a 'Your 
	 * Page was mentioned in a post' story, the 
	 * parent_id will be the original post where the 
	 * mention happened
	 */
	@Nullable 
	public abstract String getParentId();
	
	/**
	 * The picture scraped from any link included 
	 * with the post.
	 */
	@Nullable 
	public abstract String getPicture();
	
	/**
	 * Any location information attached to the post.
	 */
	@Nullable 
	public abstract String getPlace();
	
	/**
	 * Description of the type of a status update.
	 */
	@Nullable 
	public abstract StatusType getStatusType();
	
	/**
	 * Text from stories not intentionally generated 
	 * by users, such as those generated when two 
	 * people become friends, or when someone else 
	 * posts on the person's wall.
	 */
	@Nullable 
	public abstract String getStory();
	
	/**
	 * A string indicating the object type of this 
	 * post.
	 */
	public abstract Type getType();
	
	/**
	 * The time when the post was created, last 
	 * edited or the time of the last comment that 
	 * was left on the post.
	 * 
	 * For a post about a life event, this will be 
	 * the date and time of the life event
	 */
	public abstract Instant getUpdatedTime();
		
	/**
	 * anything else
	 */
	public abstract Map<String, Object> getJsonProperties();	
	
}
