package ch.supsi.webapp.web;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORIA")
public class Categoria
{
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
