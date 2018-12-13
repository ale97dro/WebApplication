package ch.supsi.webapp.web.model;

import javax.persistence.*;

@Entity
@Table(name = "UTENTE")
public class Utente
{
    @Id
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Ruolo role;

    public Utente()
    {

    }

    public Utente(int id, String name, Ruolo role)
    {
       // this.id = id;
        this.name = name;
        this.role = role;
    }

    public Utente(String name, Ruolo role) {
        this.role = role;
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

    public void setRole(Ruolo role)
    {
        this.role = role;
    }

    public Ruolo getRole()
    {
        return role;
    }

    public String toString()
    {
        return name;
    }
}
