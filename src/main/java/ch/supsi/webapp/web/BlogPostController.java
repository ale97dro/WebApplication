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
        return blogPostService.getAll();
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.GET)
    public ResponseEntity<BlogPost> getPost(@PathVariable int id)
    {
        BlogPost post = blogPostService.getPost(id);

        if(post != null)
            return new ResponseEntity<>(post, HttpStatus.OK);

        return new ResponseEntity<>((BlogPost) null, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/blogposts", method = RequestMethod.POST)
    public ResponseEntity<BlogPost> post(@RequestBody BlogPost post)
    {
        BlogPost newPost = blogPostService.addNewBlogPost(post);

        if(newPost != null)
            return new ResponseEntity<>(post, HttpStatus.CREATED);

        return new ResponseEntity<>(post, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.PUT)
    public ResponseEntity<BlogPost> put(@RequestBody BlogPost post, @PathVariable int id)
    {
        if(!blogPostService.isValidBlogPost(post))
            return new ResponseEntity<>((BlogPost) null, HttpStatus.BAD_REQUEST);

        BlogPost updatedPost = blogPostService.updateBlogPost(post, id);

        if(updatedPost != null)
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);

        return new ResponseEntity<>((BlogPost) null, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable int id)
    {
        String result = blogPostService.deleteBlogPost(id);

        if(result != null)
            return new ResponseEntity<>(result, HttpStatus.OK);

        return new ResponseEntity<>((String) null, HttpStatus.NOT_FOUND);
    }
}
