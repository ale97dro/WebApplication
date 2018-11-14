package ch.supsi.webapp.web;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RUOLO")
public class Ruolo {

    @Id
    private String name;

    public Ruolo()
    {

    }

    public Ruolo(String name)
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
