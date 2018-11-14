package ch.supsi.webapp.web;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "UTENTE")
public class Utente
{
    private String name;

    private Ruolo role;

    public Utente()
    {

    }

    public Utente(String name, Ruolo role)
    {
        this.name = name;
        this.role = role;
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
}
