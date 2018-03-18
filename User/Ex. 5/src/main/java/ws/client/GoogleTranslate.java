package ws.client;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;


public class GoogleTranslate {

    private final String API_KEY;

    public GoogleTranslate(String api_key) {
        API_KEY = "AIzaSyA7zvKKRjn4OHEToBGzcXE3w7_CD2PQjUQ";
    }

    public String translationFor(String input) {

        WebTarget webTarget = ClientBuilder.newClient().target("https://translation.googleapis.com/language/translate/v2");

        Form form = new Form();
        form.param("q", input);
        form.param("key", API_KEY);
        form.param("source", "en");
        form.param("target", "pl");


        Response response = webTarget.request().post(Entity.form(form));

        String value = response.readEntity(String.class);
        response.close();
        return value;
    }
}


