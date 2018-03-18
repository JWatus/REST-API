package ws.client;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RestCountries {
    public static void main(String[] args) {

        Client client = ClientBuilder.newClient();

        WebTarget target = client.target("http://services.groupkt.com/state/get/USA/all");

        Response response = target.request().accept(MediaType.APPLICATION_JSON_TYPE).get();
        StateObject restResponse = response.readEntity(StateObject.class);

        // wypisuje wszystkie miasta
        response.close();

        restResponse.getRestResponse().getResult().forEach(s -> System.out.println(s.id + " " + s.country + " " + s.name));
    }
}

