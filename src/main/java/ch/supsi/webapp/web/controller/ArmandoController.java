package ch.supsi.webapp.web.controller;

import ch.supsi.webapp.web.BlogPostService;
import ch.supsi.webapp.web.model.BlogPost;
import ch.supsi.webapp.web.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ArmandoController {
    private List<BlogPost> posts = new ArrayList<>();

    @Autowired
    private BlogPostService blogPostService;
    @Autowired
    private BlogPostRepository blogPostRepository;

    @GetMapping("/")
    public String getIndex(Model model)
    {
        model.addAttribute("allPosts", blogPostService.getAll());
        return "index";
    }


//    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.GET)
//    public ResponseEntity<BlogPost> getPost(@PathVariable int id)
//    {
//        BlogPost post = blogPostService.getPost(id);
//
//        if(post != null)
//            return new ResponseEntity<>(post, HttpStatus.OK);
//
//        return new ResponseEntity<>((BlogPost) null, HttpStatus.NOT_FOUND);
//    }
//
//    @RequestMapping(value = "/blogposts", method = RequestMethod.POST)
//    public ResponseEntity<BlogPost> post(@RequestBody BlogPost post)
//    {
//        BlogPost newPost = blogPostService.addNewBlogPost(post);
//
//        if(newPost != null)
//            return new ResponseEntity<>(post, HttpStatus.CREATED);
//
//        return new ResponseEntity<>(post, HttpStatus.BAD_REQUEST);
//    }
//
//    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.PUT)
//    public ResponseEntity<BlogPost> put(@RequestBody BlogPost post, @PathVariable int id)
//    {
//        if(!blogPostService.isValidBlogPost(post))
//            return new ResponseEntity<>((BlogPost) null, HttpStatus.BAD_REQUEST);
//
//        BlogPost updatedPost = blogPostService.updateBlogPost(post, id);
//
//        if(updatedPost != null)
//            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
//
//        return new ResponseEntity<>((BlogPost) null, HttpStatus.NOT_FOUND);
//    }
//
//    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.DELETE)
//    public ResponseEntity<String> delete(@PathVariable int id)
//    {
//        String result = blogPostService.deleteBlogPost(id);
//
//        if(result != null)
//            return new ResponseEntity<>(result, HttpStatus.OK);
//
//        return new ResponseEntity<>((String) null, HttpStatus.NOT_FOUND);
//    }
}
