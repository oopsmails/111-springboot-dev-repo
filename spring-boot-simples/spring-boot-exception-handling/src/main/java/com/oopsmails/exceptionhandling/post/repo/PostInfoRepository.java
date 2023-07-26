package com.oopsmails.exceptionhandling.post.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oopsmails.exceptionhandling.post.model.PostInfo;
import com.oopsmails.exceptionhandling.post.model.PostWithAuthorDTO;

@Repository
@Transactional
public class PostInfoRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public PostWithAuthorDTO getPostWithAuthor(Long id) {
		String query = "select new com.oopsmails.exceptionhandling.post.model.PostWithAuthorDTO(p, a) from Post p inner join Author a on p.authorId = a.id where p.id = ?1";
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery(query, PostWithAuthorDTO.class).setParameter(1, id).getSingleResult();
	}

	public List<PostInfo> getPostInfoWithoutJoinClause() {
		String query = "select new com.oopsmails.exceptionhandling.post.model.PostInfo(p.title, a.firstName || ' ' || a.lastName, count(v)) from Post p, Author a, PageView v where p.authorId = a.id and p.slug = v.slug";
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery(query, PostInfo.class).getResultList();
	}

	public List<PostInfo> getPostInfoWithConstrutorExp() {
		String query = "select new com.oopsmails.exceptionhandling.post.model.PostInfo(p.title, a.firstName || ' ' || a.lastName, count(v)) from Post p inner join Author a on p.authorId = a.id inner join PageView v on p.slug = v.slug";
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery(query, PostInfo.class).getResultList();
	}

}
