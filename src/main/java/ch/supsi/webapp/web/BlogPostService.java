package ch.supsi.webapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BlogPostService
{
    @Autowired
    private BlogPostRepository blogPostRepository;

    public List<BlogPost> getAll()
    {
        return blogPostRepository.findAll();
    }

    public BlogPost getPost(int id)
    {
        Optional<BlogPost> optionalPost = blogPostRepository.findById(id);

        return optionalPost.orElse(null);
    }

    public BlogPost addNewBlogPost(BlogPost post)
    {
        if (isValidBlogPost(post))
        {
            return blogPostRepository.save(post);
        }
        return null;
    }

    public BlogPost updateBlogPost(BlogPost newPost, int id)
    {
        Optional<BlogPost> optionalPost;
        BlogPost post;

        optionalPost = blogPostRepository.findById(id);
        if(optionalPost.isPresent())
        {
            post = optionalPost.get();

            post.setAuthor(newPost.getAuthor());
            post.setTitle(newPost.getTitle());
            post.setText(newPost.getText());

            blogPostRepository.save(post);
        }
        else
            post = null;

        return post;
    }

    public String deleteBlogPost(int id)
    {
        Optional<BlogPost> optionalPost = blogPostRepository.findById(id);

        if(optionalPost.isPresent())
        {
            blogPostRepository.delete(optionalPost.get());
            return "{\"success\": true}";
        }

        return null;
    }

    public boolean isValidBlogPost(BlogPost post)
    {
        return ((post.getAuthor() != null) && (post.getTitle() != null) && (post.getText() != null));
    }
}
