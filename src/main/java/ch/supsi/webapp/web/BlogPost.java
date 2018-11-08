package ch.supsi.webapp.web;

import javax.persistence.*;

@Entity
@Table(name = "BLOGPOST")
public class BlogPost
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String author;
    private String title;
    @Column(columnDefinition = "Text")
    private String text;

    public BlogPost()
    {

    }

    public BlogPost(int id, String author, String title, String text)
    {
        this.id = id;
        this.author = author;
        this.title = title;
        this.text = text;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
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
}
