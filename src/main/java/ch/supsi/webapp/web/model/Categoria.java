package ch.supsi.webapp.web.model;

import javax.persistence.*;

@Entity
@Table(name = "CATEGORIA")
public class Categoria
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private String name;

    public Categoria()
    {

    }

    public Categoria(int id)
    {
        this.id = id;
    }

//    public Categoria(String id, String name)
//    {
//        this.id = Integer.parseInt(id);
//        this.name = name;
//    }
//
//    public Categoria(int id, String name)
//    {
//        this.id = id;
//        this.name = name;
//    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }
}
