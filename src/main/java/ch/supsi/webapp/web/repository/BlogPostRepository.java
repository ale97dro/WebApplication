package ch.supsi.webapp.web.repository;

import ch.supsi.webapp.web.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Integer>
{
    @Query("SELECT b FROM BlogPost b ORDER BY b.date DESC ")
    List<BlogPost> recentBlogPostFirst();

    @Query("SELECT b FROM BlogPost b WHERE b.deleted=true")
    List<BlogPost> deletedBlogPost();
}
