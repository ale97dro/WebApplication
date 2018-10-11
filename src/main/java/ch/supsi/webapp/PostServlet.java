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

        /*for(int i=0;i<posts.size();i++)
        {
            String json = mapper.writeValueAsString(posts.get(i));
            res.getWriter().println(json);
        }*/

        res.getWriter().println("200 OK");
        String json = mapper.writeValueAsString(posts);
        res.getWriter().println(json);
    }

    @Override
    protected  void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        BlogPost post = new BlogPost(req.getParameter("title"), req.getParameter("text"), req.getParameter("author"));

        posts.add(post);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(post);

        res.setStatus(HttpServletResponse.SC_OK);
        res.getWriter().println(json);
    }
}
