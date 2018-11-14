package ch.supsi.webapp.web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Integer>
{
//    @Query("SELECT b FROM BlogPost b ")
//    List<BlogPost> postsList();
//
//    //@Query("INSERT INTO BlogPost b; select b FROM BlogPost b WHERE b.")
//    @Query("SELECT b FROM BlogPost b")
//    BlogPost addNewBlogPost(@Param(value = "post") BlogPost post);
}
