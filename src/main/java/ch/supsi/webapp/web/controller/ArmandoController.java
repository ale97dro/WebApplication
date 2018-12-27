package ch.supsi.webapp.web.controller;

import ch.supsi.webapp.web.BlogPostService;
import ch.supsi.webapp.web.model.BlogPost;
import ch.supsi.webapp.web.model.Utente;
import ch.supsi.webapp.web.repository.BlogPostRepository;
import ch.supsi.webapp.web.repository.CategoriaRepository;
import ch.supsi.webapp.web.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Autowired
    private UtenteRepository utenteRepository;

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
        //model.addAttribute("allAuthor", utenteRepository.findAll());
        model.addAttribute("operation", "Create blogpost");
        model.addAttribute("submitVal", "Create");
        return "createBlogForm";
    }

    @PostMapping("/blog/new")
    public String addNewPost(@ModelAttribute BlogPost newPost, Model model)
    {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newPost.setAuthor(blogPostService.findUserByUsername(user.getUsername()));
        newPost.setDate(LocalDateTime.now());
        model.addAttribute("newPost", newPost); //todo ok
        blogPostService.addNewBlogPost(newPost);

        return "redirect:/";
    }

    @GetMapping("/blog/{id}/delete")
    public String removePost(@PathVariable int id, Model model)
    {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente utente = blogPostService.findUserByUsername(user.getUsername());

        if(utente.getRole().getName().equals("ROLE_ADMIN"))
            blogPostService.deleteBlogPost(id);

        return "redirect:/";
    }

    @GetMapping("/blog/{id}/edit")
    public String getUpdateBlogPost(@PathVariable int id, Model model)
    {
        BlogPost post = blogPostService.getPost(id);
        model.addAttribute("newPost", post);
        model.addAttribute("allCategory", categoriaRepository.findAll());
        //model.addAttribute("allAuthor", utenteRepository.findAll());
        model.addAttribute("operation", "Edit blogpost");
        model.addAttribute("submitVal", "Edit");

        return "createBlogForm";
        //questo lo pianto nel model e poi lo visualizzo
    }

    @PostMapping("/blog/{id}/edit")
    public String updateBlogPost(@ModelAttribute BlogPost updatedPost, Model model)
    {
        blogPostService.updateBlogPost(updatedPost, updatedPost.getId());

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model)
    {
        return "login";
    }
}
