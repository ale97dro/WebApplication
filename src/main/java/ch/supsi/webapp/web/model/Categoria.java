package ch.supsi.webapp.web.model;

import javax.persistence.*;

@Entity
@Table(name = "CATEGORIA")
public class Categoria
{
    @Id
    private String name;

    public Categoria()
    {

    }

    public Categoria(String name)
    {
        this.name = name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

}
