package ch.supsi.webapp.web;

import ch.supsi.webapp.web.model.BlogPost;
import ch.supsi.webapp.web.model.Utente;
import ch.supsi.webapp.web.repository.BlogPostRepository;
import ch.supsi.webapp.web.repository.CategoriaRepository;
import ch.supsi.webapp.web.repository.RuoloRepository;
import ch.supsi.webapp.web.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BlogPostService
{
    @Autowired
    private BlogPostRepository blogPostRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private RuoloRepository ruoloRepository;
    @Autowired
    private UtenteRepository utenteRepository;

    //TODO: taggare questi metodi come modelAttribute come fatto in Armando
    @ModelAttribute("allPosts")
    public List<BlogPost> getAll()
    {
        return blogPostRepository.findAll();
    }

    @ModelAttribute("post")
    public BlogPost getPost(int id)
    {
        Optional<BlogPost> optionalPost = blogPostRepository.findById(id);

        return optionalPost.orElse(null);
    }

    public List<BlogPost> recentBlogPost()
    {
        List<BlogPost> temp = blogPostRepository.recentBlogPostFirst();
        List<BlogPost> recent = new ArrayList<>();

        for(int i=0;i<temp.size() && i<3; i++)
            recent.add(temp.get(i));

        return recent;
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
            post.setCategory(newPost.getCategory());

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
