package ch.supsi.webapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value="/blogposts/*")
public class PostServlet extends HttpServlet
{
    List<BlogPost> posts = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        /*
        //Leggere JSON
        JsonNode root;
        ObjectMapper mapper = new ObjectMapper();
        root=mapper.readTree("File Jason");
        int valore = root.path("Nome Campo").intValue();
        */

        String[] url = req.getRequestURL().toString().split("/");


        ObjectMapper mapper = new ObjectMapper();
        String json = null;


        if (url[url.length - 1].equals("blogposts")) //return list of resource
        {
            res.setStatus(HttpServletResponse.SC_OK);
            json = mapper.writeValueAsString(posts);

        }
        else
        {
            if(url[url.length-2].equals("blogposts")) //this try to handle link like blogposts/folder/resource
            {
                int resource = Integer.parseInt(url[url.length-1]); //name of the resource

                if(resource<posts.size()) //if the resource exists in list
                {
                    res.setStatus(HttpServletResponse.SC_OK);
                    json = mapper.writeValueAsString(posts.get(resource));
                }
                else //the resource doesn't exist
                    res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
            else
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        res.getWriter().println(json);
    }

    @Override
    protected  void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
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

    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        //stessi controlli di sopra per verificare la corretteza dell'url
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        //stessi controlli di sopra per verificare la corretteza dell'url
    }
}
