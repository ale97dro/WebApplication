package ch.supsi.webapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value="/blogpost")
public class PostServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        //res.getWriter().println("coap");


        /*
        //Leggere JSON
        JsonNode root;
        ObjectMapper mapper = new ObjectMapper();
        root=mapper.readTree("File Jason");
        int valore = root.path("Nome Campo").intValue();
        */

        ObjectMapper mapper = new ObjectMapper();

        BlogPost prova = new BlogPost("ciao", "ciao", "ciao");
        String json=mapper.writeValueAsString(prova);

        res.getWriter().println(json);


    }

    @Override
    protected  void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {

    }
}
