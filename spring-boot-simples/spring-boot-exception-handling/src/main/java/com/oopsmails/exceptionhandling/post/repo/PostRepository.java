package com.oopsmails.exceptionhandling.post.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oopsmails.exceptionhandling.post.model.Post;
import com.oopsmails.exceptionhandling.post.model.PostInfo;
import com.oopsmails.exceptionhandling.post.model.PostWithAuthor;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	@Query("select p as post, a as author from Post p inner join Author a on p.authorId = a.id where p.id = ?1")
	public PostWithAuthor getPostWithAuthor(Long id);

	@Query("select new com.oopsmails.exceptionhandling.post.model.PostInfo(p.title, a.firstName || ' ' || a.lastName, count(v)) from Post p inner join Author a on p.authorId = a.id inner join PageView v on p.slug = v.slug")
	public List<PostInfo> getPostInfoWithConstrutorExp();

	@Query("select new com.oopsmails.exceptionhandling.post.model.PostInfo(p.title, a.firstName || ' ' || a.lastName, count(v)) from Post p, Author a, PageView v where p.authorId = a.id and p.slug = v.slug")
	public List<PostInfo> getPostInfoWithoutJoinClause();
}
