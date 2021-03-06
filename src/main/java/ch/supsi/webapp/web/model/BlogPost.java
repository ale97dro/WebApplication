package ch.supsi.webapp.web.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "BLOGPOST")
public class BlogPost
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;


    @ManyToOne
    @JoinColumn(name="fk_author")
    private Utente author;
    @ManyToOne
    @JoinColumn(name="fk_category")
    private Categoria category;


    private String title;
    @Column(columnDefinition = "Text")
    private String text;
    private LocalDateTime date;
    private boolean deleted;

//    @OneToMany
//    private List<Comment> comments;

    public BlogPost()
    {
        //author = new Utente();
        //category = new Categoria();
    }

    public BlogPost(String title, String text, Utente author, Categoria category)
    {
        this.title = title;
        this.text = text;
        this.author = author;
        this.category = category;
    }


    public BlogPost(int id, Utente author, String title, String text)
    {
        this.id = id;
        this.author = author;
        this.title = title;
        this.text = text;
        this.category = null;
    }

    public BlogPost(int id, Utente author, String title, String text, Categoria category)
    {
        this.id = id;
        this.author = author;
        this.title = title;
        this.text = text;
        this.category = category;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Utente getAuthor()
    {
        return author;
    }

    public void setAuthor(Utente author)
    {
        this.author = author;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void setCategory(Categoria category)
    {
        this.category = category;
    }

    public Categoria getCategory()
    {
        return category;
    }

    public void setDate(LocalDateTime date)
    {
        this.date = date;
    }

    public LocalDateTime getDate()
    {
        return date;
    }

    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }

    public boolean getDeleted()
    {
        return deleted;
    }
}
