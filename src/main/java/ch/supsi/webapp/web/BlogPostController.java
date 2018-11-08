package ch.supsi.webapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BlogPostController {
    private List<BlogPost> posts = new ArrayList<>();

    @Autowired
    private BlogPostService blogPostService;


    @RequestMapping(value = "/blogposts", method = RequestMethod.GET)
    public List<BlogPost> getAll() {
        //System.out.println("");
        //return posts;
        return blogPostService.getAll();
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.GET)
    public ResponseEntity<BlogPost> getPost(@PathVariable int id) {
        for (BlogPost b : posts) {
            if (b.getId() == id)
                return new ResponseEntity<>(b, HttpStatus.OK);
        }

        return new ResponseEntity<>((BlogPost) null, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/blogposts", method = RequestMethod.POST)
    public ResponseEntity<BlogPost> post(@RequestBody BlogPost post)
    {
        BlogPost newPost = blogPostService.addNewBlogPost(post);

        if(newPost != null)
            return new ResponseEntity<>(post, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(post, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.PUT)
    public ResponseEntity<BlogPost> put(@RequestBody BlogPost post, @PathVariable int id)
    {
        if (!((post.getAuthor() != null) && (post.getTitle() != null) && (post.getText() != null)))
            return new ResponseEntity<>((BlogPost) null, HttpStatus.BAD_REQUEST);

        for(BlogPost b : posts)
        {
            if(b.getId() == id)
            {
                b.setId(post.getId());
                b.setAuthor(post.getAuthor());
                b.setTitle(post.getTitle());
                b.setText(post.getText());

                return new ResponseEntity<>(b, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>((BlogPost) null, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable int id)
    {
        for(BlogPost b : posts)
        {
            if (b.getId() == id)
            {
                posts.remove(b);
                return new ResponseEntity<>("{\"success\": true}", HttpStatus.OK);
            }
        }

        return new ResponseEntity<>((String) null, HttpStatus.NOT_FOUND);
    }
}
