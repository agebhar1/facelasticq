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
package com.github.agebhar1.facelasticq.facebook.databind.jackson;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.agebhar1.facelasticq.facebook.api.ImmutablePost;
import com.github.agebhar1.facelasticq.facebook.api.Post;
import com.github.agebhar1.facelasticq.facebook.api.Profile;

@SuppressWarnings("serial")
public final class FacebookModule extends SimpleModule {

	public FacebookModule() {
		super("Facebook", new Version(1, 0, 0, null, "com.github.agebhar1", "facelasticq"));
	}

	@Override
	public void setupModule(final SetupContext context) {
						
		/*
		 * Profile
		 */
		context.setMixInAnnotations(Profile.class, ProfileMixIn.class);
		context.setMixInAnnotations(Profile.ProfileBuilder.class, ProfileMixIn.ProfileBuilderMixIn.class);
		/*
		 * Post
		 */
		context.setMixInAnnotations(Post.class, PostMixIn.class);
		context.setMixInAnnotations(ImmutablePost.Builder.class, ImmutablePostMixIn.Builder.class);

		final Module module = new JavaTimeModule().addSerializer(Instant.class, new JsonSerializer<Instant>() {
			
			final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
	                .parseCaseInsensitive()
	                .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
	                .appendOffset("+HH:MM:ss", "+0000")
	                .toFormatter();			

			@Override
			public void serialize(final Instant value, final JsonGenerator generator, 
					final SerializerProvider provider) throws IOException, JsonProcessingException {
				
				generator.writeString(formatter.format(value.atZone(ZoneOffset.UTC)));
			}
		
		});
		module.setupModule(context);
		
	}

}
