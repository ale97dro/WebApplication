package ch.supsi.webapp.web;

import ch.supsi.webapp.web.model.BlogPost;
import ch.supsi.webapp.web.model.Categoria;
import ch.supsi.webapp.web.model.Ruolo;
import ch.supsi.webapp.web.model.Utente;
import ch.supsi.webapp.web.repository.BlogPostRepository;
import ch.supsi.webapp.web.repository.CategoriaRepository;
import ch.supsi.webapp.web.repository.RuoloRepository;
import ch.supsi.webapp.web.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
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

    private PasswordEncoder encoder;


    @PostConstruct
    public void init() {

        encoder = new BCryptPasswordEncoder();

        if(utenteRepository.findAll().size() == 0) {
            Utente admin = new Utente("admin", new Ruolo("ROLE_ADMIN"));
            admin.setPassword(encoder.encode("1234")); //salva la password criptata
            utenteRepository.save(admin);
            ruoloRepository.save(new Ruolo("ROLE_USER"));
        }

        if(categoriaRepository.findAll().size() == 0) {
            categoriaRepository.save(new Categoria("Cultura"));
            categoriaRepository.save(new Categoria("Scienza"));
            categoriaRepository.save(new Categoria("Sport"));
        }

        if(blogPostRepository.findAll().size() == 0) {
            BlogPost blogPost = new BlogPost();
            blogPost.setAuthor(utenteRepository.findById("admin").get());
            blogPost.setCategory(categoriaRepository.findById("Sport").get());
            blogPost.setTitle("\"Per ora non sento più dolore\"");
            blogPost.setText("Valon Behrami è con il gruppo e questo conta al di là del suo impiego effettivo sul campo, anche se il ticinese spera di poter scendere in campo almeno per la partita di ritorno contro l'Irlanda del Nord: " +
                    "\"Ieri ho fatto i primi venti minuti di corsa dopo l'infortunio e stamattina ho iniziato con i primi cambi di direzione - ha spiegato il centrocampista - Per ora non ho sentito dolore, appena aumenteremo la velocità, saprò un po' come sto effettivamente\"." +
                    "<br/><br/>Oltre che preoccupare lo staff tecnico della Nazionale e il giocatore stesso, anche l'Udinese (squadra che detiene il cartellino del rossocrociato) vuole essere certa delle condizioni fisiche del 32enne: \"Ci sono delle situazioni al di fuori del " +
                    "campo che non facilitano la mia possibilità di giocare se non sarò al 100%: i proprietari del club sono preoccupati per questa situazione e li capisco\".");
            blogPost.setDate(LocalDateTime.now());
            addNewBlogPost(blogPost);
            blogPost = new BlogPost();
            blogPost.setAuthor(utenteRepository.findById("admin").get());
            blogPost.setCategory(categoriaRepository.findById("Cultura").get());
            blogPost.setTitle("80 voglia di Piano");
            blogPost.setText("Ebbene sì. Nonostante il 14 settembre 2017 compia ottant’anni, ho ancora tanta voglia di Piano.In un momento storico dove la smodata ricerca dell’originale spinge le archistar verso confini sempre più lontani, ritengo necessario tessere le lodi di un " +
                    "innovatore razionale e funzionale quale Renzo Piano.<br/><br/> I caratteri tipici dell’opera del maestro genovese (la quale oscilla all’interno di un ampio spettro di tipologie che comprendono la più grande varietà di forme, materiali e strutture) trovano la " +
                    "loro massima espressione nei progetti legati al mondo culturale. Mondo che, dall’estate del 1988 con l'esposizione Deconstructivist Architecture al Museum of Modern Art di New York, è stato travolto (ed è tutt’ora rapito) dall’architettura \"senza geometria\" " +
                    "promossa da personalità del calibro di Daniel Libeskind, Frank O. Gehry e Zaha Hadid. Questa architettura, basata sul rifiuto della purezza formale e sulla velleità di costruire a tutti i costi qualcosa di innovativo e progressista, ha prodotto una serie di " +
                    "edifici-monumento dove il recipiente ha spodestato irrimediabilmente il contenuto, relegandolo ad un ruolo marginale.");
            blogPost.setDate(LocalDateTime.now());
            addNewBlogPost(blogPost);
        }
    }




    public List<BlogPost> getAll()
    {
        return blogPostRepository.findAll();
    }

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

    public Utente findUserByUsername(String username)
    {
        Optional<Utente> optionalUser = utenteRepository.findById(username);

        return optionalUser.orElse(null);
    }

    public void addUser(Utente utente)
    {
        utente.setPassword(encoder.encode(utente.getPassword()));
        utenteRepository.save(utente);
    }
}
