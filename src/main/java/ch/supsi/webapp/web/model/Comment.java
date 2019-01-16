package ch.supsi.webapp.web.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "COMMENT")
public class Comment
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="fk_author")
    private Utente author;

    @ManyToOne
    @JoinColumn(name="fk_blogpost")
    private BlogPost post;

    private String text;
    private LocalDateTime date;

    public Comment()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utente getAuthor() {
        return author;
    }

    public void setAuthor(Utente author) {
        this.author = author;
    }

    public BlogPost getPost() {
        return post;
    }

    public void setPost(BlogPost post) {
        this.post = post;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(LocalDateTime date)
    {
        this.date = date;
    }

    public LocalDateTime getDate()
    {
        return date;
    }
}
