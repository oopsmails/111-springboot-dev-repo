package com.oopsmails.exceptionhandling.post.model;

import lombok.Value;

@Value
public class PostWithAuthorDTO {
	private Post post;
	private Author author;
}
