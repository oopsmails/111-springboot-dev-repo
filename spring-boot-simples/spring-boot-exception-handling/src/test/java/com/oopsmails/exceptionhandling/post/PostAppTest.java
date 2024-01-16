package com.oopsmails.exceptionhandling.post;

import com.oopsmails.exceptionhandling.post.model.PostInfo;
import com.oopsmails.exceptionhandling.post.model.PostWithAuthor;
import com.oopsmails.exceptionhandling.post.model.PostWithAuthorDTO;
import com.oopsmails.exceptionhandling.post.repo.PostInfoRepository;
import com.oopsmails.exceptionhandling.post.repo.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
public class PostAppTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostInfoRepository postInfoDAO;

    @Test
    public void getPostWithAuthorJpaTest() {
        PostWithAuthor postWithAuthor = postRepository.getPostWithAuthor(1L);
        Assertions.assertEquals("Oops", postWithAuthor.getAuthor().getFirstName());
    }

    @Test
    public void getPostInfoWithConstrutorExpJpaTest() {
        List<PostInfo> list = postRepository.getPostInfoWithConstrutorExp();
        Assertions.assertEquals(2, list.get(0).getViewCount());
    }

    @Test
    public void getPostInfoWithoutJoinClauseJpaTest() {
        List<PostInfo> list = postRepository.getPostInfoWithoutJoinClause();
        Assertions.assertEquals(2, list.get(0).getViewCount());
    }

    @Test
    public void getPostWithAuthorTest() {
        PostWithAuthorDTO postWithAuthor = postInfoDAO.getPostWithAuthor(1L);
        Assertions.assertEquals("Oops", postWithAuthor.getAuthor().getFirstName());
    }

    @Test
    public void getPostInfoWithoutJoinClauseTest() {
        List<PostInfo> list = postInfoDAO.getPostInfoWithoutJoinClause();
        Assertions.assertEquals(2, list.get(0).getViewCount());
    }

    @Test
    public void getPostInfoWithConstrutorExpTest() {
        List<PostInfo> list = postInfoDAO.getPostInfoWithConstrutorExp();
        Assertions.assertEquals(2, list.get(0).getViewCount());
    }
}
