package ch.supsi.webapp.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BlogPostController
{
    private List<BlogPost> posts = new ArrayList<>();

    @RequestMapping(value = "/blogposts", method = RequestMethod.GET)
    public List<BlogPost> get()
    {
        //System.out.println("");
        return posts;
    }

    @RequestMapping(value = "/blogposts", method = RequestMethod.POST)
    public ResponseEntity<BlogPost> post(@RequestBody BlogPost post)
    {
        if((post.getAuthor() != null) && (post.getTitle() != null) && (post.getText() != null))
        {
            post.setId(posts.size());
            System.out.print("ciao");
            posts.add(post);
            return new ResponseEntity<>(post, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(post, HttpStatus.BAD_REQUEST);
    }
}
