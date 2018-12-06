package ch.supsi.webapp.web.controller;

import ch.supsi.webapp.web.BlogPostService;
import ch.supsi.webapp.web.model.BlogPost;
import ch.supsi.webapp.web.model.Utente;
import ch.supsi.webapp.web.repository.BlogPostRepository;
import ch.supsi.webapp.web.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class ArmandoController {
    private List<BlogPost> posts = new ArrayList<>();

    @Autowired
    private BlogPostService blogPostService;
    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    /**
     * Ottieni l'homepage del sito
     * @param model
     * @return
     */
    @GetMapping("/")
    public String getIndex(Model model)
    {
        //List<BlogPost> posts = blogPostService.getAll();
        model.addAttribute("allPosts", blogPostService.recentBlogPost());
        return "index";
    }

    /**
     * Ottieni un blogpost partendo dal suo id
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blog/{id}")
    public String getBlogPost(@PathVariable int id, Model model)
    {
        model.addAttribute("post", blogPostService.getPost(id));
        return "blogpostDetails";
    }

    /**
     * Ottieni tutti i blogpost e visualizzali sulla loro pagina dedicata
     * @param model
     * @return
     */
    @GetMapping("/all")
    public String getAllBlogPost(Model model)
    {
        model.addAttribute("allPosts", blogPostService.getAll());
        return "allBlogpost";
    }

    /**
     * Ritorna il form per l'inserimento dei blogpost
     * @param model
     * @return
     */
    @GetMapping("/blog/new")
    public String getCreateBlogpost(Model model)
    {
        model.addAttribute("newPost", new BlogPost());
        model.addAttribute("allCategory", categoriaRepository.findAll());
        return "createBlogForm";
    }

    @PostMapping("/blog/new")
    public String addNewPost(@ModelAttribute BlogPost newPost, Model model)
    {
        Utente user = new Utente();
        user.setId(24);
        newPost.setAuthor(user);
        newPost.setDate(LocalDateTime.now());
        model.addAttribute("newPost", newPost); //todo ok
        blogPostService.addNewBlogPost(newPost);

        return "redirect:/";
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
