package ch.supsi.webapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BlogPostService
{
    private static int ID = 0;

    @Autowired
    private BlogPostRepository blogPostRepository;

    //private List<BlogPost> posts = new ArrayList<>();

    public List<BlogPost> getAll()
    {
        //return blogPostRepository.postsList();
        return blogPostRepository.findAll();
    }

    public BlogPost addNewBlogPost(BlogPost post) {
        if ((post.getAuthor() != null) && (post.getTitle() != null) && (post.getText() != null)) {
            //post.setId(posts.size());
            post.setId(ID);
            ID++;
            //posts.add(post);
            return blogPostRepository.save(post);
        }
        return null;
    }
        //return blogPostRepository.addNewBlogPost(post);
       // blogPostRepository.sa
}
