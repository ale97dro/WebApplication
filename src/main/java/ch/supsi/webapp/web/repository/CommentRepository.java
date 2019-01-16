package ch.supsi.webapp.web.repository;

import ch.supsi.webapp.web.model.BlogPost;
import ch.supsi.webapp.web.model.Categoria;
import ch.supsi.webapp.web.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c FROM Comment c ORDER BY c.date DESC ")
    List<Comment> recentCommentsFirst();
}

