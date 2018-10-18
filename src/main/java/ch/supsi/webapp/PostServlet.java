package ch.supsi.webapp;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet(value="/blogposts/*")
public class PostServlet extends HttpServlet
{
    List<BlogPost> posts = new ArrayList<>();
    ObjectMapper mapper;

    public PostServlet()
    {
        mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        /*
        //Leggere JSON
        JsonNode root;
        ObjectMapper mapper = new ObjectMapper();
        root=mapper.readTree("File Jason");
        int valore = root.path("Nome Campo").intValue();
        */

      /*  String[] url = req.getRequestURL().toString().split("/");


        //ObjectMapper mapper = new ObjectMapper();
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

        res.setContentType("application/json");
        res.getWriter().println(json);*/

        String[] url = req.getRequestURL().toString().split("/");
        String json;

        if(url.length == 4)
        {
            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("application/json");
            json = mapper.writeValueAsString(posts);
            res.getWriter().println(json);
            return;
        }

        if(url.length == 5)
        {
            String author = url[url.length-1];
            List<BlogPost> author_posts = new ArrayList<>();

            for(BlogPost b : posts)
                if(b.getAuthor().equals(author))
                    author_posts.add(b);

            prepareResponse(res, author_posts);
            return;
        }

        if(url.length == 6)
        {
            String author = url[url.length-2];
            String title = url[url.length-1];
            List<BlogPost> author_posts = new ArrayList<>();

            for(BlogPost b : posts)
                if((b.getAuthor().equals(author)) && (b.getTitle().equals(title)))
                    author_posts.add(b);

            prepareResponse(res, author_posts);
            return;
        }

        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void prepareResponse(HttpServletResponse res, List<BlogPost> author_posts) throws IOException {
        String json;
        if(author_posts.size() != 0)
        {
            json = mapper.writeValueAsString(author_posts);
            res.setStatus(HttpServletResponse.SC_OK);
            res.setContentType("application/json");
            res.getWriter().println(json);
        }
        else
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected  void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        //ObjectMapper mapper = new ObjectMapper();
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

        res.setStatus(HttpServletResponse.SC_CREATED);
        res.setContentType("application/json");
        res.getWriter().println(json);
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        BlogPost post;
        String[] url = req.getRequestURL().toString().split("/");


        if(req.getParameterMap().keySet().size()!=3) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.out.println("Errore");
            return;
        }

        BlogPost new_post = new BlogPost(req.getParameter("title"), req.getParameter("text"), req.getParameter("author"));

        if(url.length != 6)
        {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String author = url[url.length-2];
        String title = url[url.length-1];

        boolean updated = false;
        Iterator<BlogPost> iterator = posts.iterator();

        while(iterator.hasNext())
        {
            BlogPost b = iterator.next();

            if((b.getTitle().equals(title)) && (b.getAuthor().equals(author)))
            {
                b.setAuthor(new_post.getAuthor());
                b.setText(new_post.getText());
                b.setTitle(new_post.getTitle());
                updated = true;
            }
        }

        if(updated)
            res.setStatus(HttpServletResponse.SC_OK);
        else
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getMethod().equalsIgnoreCase("PATCH"))
            doPatch(req, resp);
        else
            super.service(req, resp);
    }

    private  void doPatch(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String[] url = req.getRequestURL().toString().split("/");

        if(url.length != 6)
        {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        BlogPost to_update = new BlogPost(url[url.length-1], url[url.length-2]);

        boolean updated = false;

        Iterator<BlogPost> iterator = posts.iterator();

        while(iterator.hasNext())
        {
            BlogPost b = iterator.next();

            if((b.getAuthor().equals(to_update.getAuthor())) && (b.getTitle().equals(to_update.getTitle())))
            {
                updated = true;

                BlogPost updated_post = readJsonBlogPost(req);

                if(updated_post.getTitle() != null)
                    b.setTitle(updated_post.getTitle());
                if(updated_post.getAuthor() != null)
                    b.setAuthor(updated_post.getAuthor());
                if(updated_post.getText() != null)
                    b.setText(updated_post.getText());
            }

        }

        if(updated)
            res.setStatus(HttpServletResponse.SC_OK);
        else
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    private BlogPost readJsonBlogPost(HttpServletRequest req)
    {
        ObjectMapper m = new ObjectMapper();

        try {
            return m.readValue(req.getReader(), BlogPost.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        String[] url = req.getRequestURL().toString().split("/");
        BlogPost to_delete;
        boolean deleted = false;

        if(url.length == 6) {
            to_delete = new BlogPost(url[url.length - 1], url[url.length - 2]);

            Iterator<BlogPost> iterator = posts.iterator();
            while (iterator.hasNext())
            {
                BlogPost b = iterator.next();

                if ((b.getAuthor().equals(to_delete.getAuthor())) && (b.getTitle().equals(to_delete.getTitle()))) {
                    iterator.remove();
                    res.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    deleted = true;
                }
            }

            if (!deleted)
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        else
        {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
