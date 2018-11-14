package ch.supsi.webapp.web.model;

import javax.persistence.*;

@Entity
@Table(name = "UTENTE")
public class Utente
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String name;

    //@ManyToOne(cascade = {CascadeType.MERGE})
    //@OneToMany(cascade = { CascadeType.ALL }, mappedBy="UTENTE")
    @ManyToOne
    @JoinColumn(name="fk_role")
    private Ruolo role;

    public Utente()
    {

    }

    public Utente(int id)
    {
        this.id = id;
    }

    public Utente(int id, String name, Ruolo role)
    {
        this.id = id;
        this.name = name;
        this.role = role;
    }
//    public Utente(String name)
//    {
//        this.name = name;
//    }
//
//    public Utente(String name, Ruolo role)
//    {
//        this.name = name;
//        this.role = role;
//    }



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

    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }
}
