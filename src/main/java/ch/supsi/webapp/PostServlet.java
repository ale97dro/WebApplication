package ch.supsi.webapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value="/blogpost")
public class PostServlet extends HttpServlet
{
    List<BlogPost> posts = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        /*
        //Leggere JSON
        JsonNode root;
        ObjectMapper mapper = new ObjectMapper();
        root=mapper.readTree("File Jason");
        int valore = root.path("Nome Campo").intValue();
        */

        ObjectMapper mapper = new ObjectMapper();

        res.setStatus(HttpServletResponse.SC_OK);
        String json = mapper.writeValueAsString(posts);
        res.getWriter().println(json);
    }

    @Override
    protected  void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        //questa roba se ricevo i parametri

        ObjectMapper mapper = new ObjectMapper();
        BlogPost post;

        if(req.getHeader("Content-Type").equals("application/x-www-form-urlencoded"))
        {
            post = new BlogPost(req.getParameter("title"), req.getParameter("text"), req.getParameter("author"));
        }
        else
        {
            post = mapper.readValue(req.getReader(), BlogPost.class);
        }

        posts.add(post);

        String json = mapper.writeValueAsString(post);

        res.setStatus(HttpServletResponse.SC_OK);
        res.getWriter().println(json);
    }
}
