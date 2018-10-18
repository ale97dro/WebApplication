
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class LiveTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String HOST = "http://localhost:8080/";
    //private static final List<String> webapps = Arrays.asList("template");
    private static final List<String> webapps = Arrays.asList("");
    private static final String ENDPOINT = "blogposts";

    private static int size = 0;
    private static String time1 = "";
    private static String time2 = "";

    public static void main(String[] args){
        webapps.forEach( wa -> {
            System.out.print(wa+" -> ");
            System.out.print(" 00:"+(test00(wa) ? "V":"X") );
            System.out.print(" 01:"+(test01(wa) ? "V":"X") );
            System.out.print(" 02:"+(test02(wa) ? "V":"X") );
            System.out.print(" 03:"+(test03(wa) ? "V":"X") );
            System.out.print(" 04:"+(test04(wa) ? "V":"X") );
        });
    }

    // GET / ping
    public static boolean test00(String webapp) {
        HttpResponse<String> response = null;
        try {
            response = Unirest.get(HOST + webapp + "/").asString();
        } catch (Exception e) {
            return false;
        }

        boolean res = true;
        if(response.getHeaders().get("Content-Length").get(0).equals("0")) res = false;
        return res;
    }

    // GET /blogpost -> size == 0
    public static boolean test01(String webapp) {
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(HOST + webapp + "/" + ENDPOINT).header("Accept", "application/json").asJson();
        } catch (Exception e) {
            return false;
        }

        boolean res = true;
        if(response.getStatus() != 200) res = false;
        if(!response.getBody().isArray()) res = false;
        size =  response.getBody().getArray().length();
        return res;
    }

    // POST /blogpost json
    public static boolean test02(String webapp) {
        time1 = new Date().getTime()+"";
        RequestBodyEntity response;
        try {
            response = Unirest.post(HOST + webapp + "/" + ENDPOINT).header("Content-Type", "application/json").body("{\"title\":\"titolo\", \"text\":\"testo\", \"author\":\"autore"+time1+"\"}");//.asJson();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        try {
            return checkResponseAfterPost(response.asObject(JsonNode.class), time1);
        } catch (UnirestException e) {
            e.printStackTrace();
            return false;
        }
    }

    // POST /blogpost x-www-form-urlencoded
    public static boolean test03(String webapp) {
        HttpResponse<JsonNode> response = null;
        time2 = new Date().getTime()+"";
        try {
            response = Unirest.post(HOST + webapp + "/" + ENDPOINT).header("Content-Type", "application/x-www-form-urlencoded").body("title=titolo&text=testo&author=autore"+time2).asJson();
        } catch (Exception e) {
            return false;
        }
        return checkResponseAfterPost(response, time2);
    }

    private static boolean checkResponseAfterPost(HttpResponse<JsonNode> response, String time) {
        boolean res = true;
        if (response.getStatus() != 200) res = false;
        if (response.getBody().isArray()) res = false;
        try {
            if (!response.getBody().getObject().getString("title").equals("titolo")) res = false;
            if (!response.getBody().getObject().getString("text").equals("testo")) res = false;
            if (!response.getBody().getObject().getString("author").equals("autore"+time)) res = false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }

    // GET /blogpost -> size == 1
    public static boolean test04(String webapp) {
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get(HOST + webapp + "/" + ENDPOINT).header("Accept", "application/json").asJson();
        } catch (UnirestException e) {
            return false;
        }

        boolean res = true;
        if(response.getStatus() != 200) res = false;
        if(!response.getBody().isArray()) res = false;
        if(response.getBody().getArray().length() != size +2) return false;

        try {
            if(!((JSONObject)response.getBody().getArray().get(size+1)).getString("title").equals("titolo")) res = false;
            if(!((JSONObject)response.getBody().getArray().get(size+1)).getString("text").equals("testo")) res = false;
            if(!((JSONObject)response.getBody().getArray().get(size+1)).getString("author").equals("autore"+time2)) res = false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return res;
    }

}
