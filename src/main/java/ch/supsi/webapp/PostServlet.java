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

            prepareGetResponse(res, author_posts);
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

            prepareGetResponse(res, author_posts);
            return;
        }

        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void prepareGetResponse(HttpServletResponse res, List<BlogPost> author_posts) throws IOException {
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
        BlogPost new_post;

        if(req.getHeader("Content-Type").equals("application/x-www-form-urlencoded"))
        {
            new_post = new BlogPost(req.getParameter("title"), req.getParameter("text"), req.getParameter("author"));
        }
        else
        {
            new_post = mapper.readValue(req.getReader(), BlogPost.class);
        }

        posts.add(new_post);

        String json = mapper.writeValueAsString(new_post);

        //res.setStatus(HttpServletResponse.SC_CREATED);
        res.setContentType("application/json");
        res.getWriter().println(json);
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        BlogPost post;
        String[] url = req.getRequestURL().toString().split("/");

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
                BlogPost updated_post = readJsonBlogPost(req);

                b.setAuthor(updated_post.getAuthor());
                b.setTitle(updated_post.getTitle());
                b.setText(updated_post.getText());

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
                updatePostWithPatch(b, readJsonBlogPost(req));
            }
        }

        if(updated)
            res.setStatus(HttpServletResponse.SC_OK);
        else
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    /**
     * Aggiorna gli attributi del blogpost salvato con quelli nuovi
     * @param to_update
     * @param new_data
     */
    private void updatePostWithPatch(BlogPost to_update, BlogPost new_data)
    {
        if(new_data.getTitle() != null)
            to_update.setTitle(new_data.getTitle());
        if(new_data.getAuthor() != null)
            to_update.setAuthor(new_data.getAuthor());
        if(new_data.getText() != null)
            to_update.setText(new_data.getText());
    }

    /**
     * Crea blogpost a partire dal corpo json della richiesta
     * @param req
     * @return
     */
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
