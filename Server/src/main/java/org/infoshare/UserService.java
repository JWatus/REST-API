package org.infoshare;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.org.apache.xerces.internal.util.Status;
import org.infoshare.model.User;
import org.infoshare.model.UserStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.io.InvalidObjectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Enum.valueOf;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

@Path("/users")
public class UserService {

    private Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Inject
    private UserStore us;

    @Context
    UriInfo uriInfo;

    public UserService() {
    }

    // Ex.1
    @GET
    @Path("/hello")             // musi byc hello zeby pozniej rozroznial adres - jak dam tylko
                                // stringa i pozniej tez to nie bedzie wiedzial ktorego brac
    @Produces(MediaType.APPLICATION_JSON)
    public Response sayHello(@QueryParam("name") String name,
                             @QueryParam("surname") String surname) {

        LOG.info("Hello "+name+" "+surname+"!");

        return Response.ok("Hello "+name+" "+surname+"!").build();
    }

// bez parametru w zadaniu
// http://localhost:8080/sampleRESTserver-1.0-SNAPSHOT/users/hello?name=Jakub&surname=Watus

// z parametrem w zadaniu
// http://localhost:8080/sampleRESTserver-1.0-SNAPSHOT/users/hello/Czesc?name=Jakub&surname=Watus

    // Ex.2
    @GET
    @Path("/abs/{xyz}")

    @Produces(MediaType.APPLICATION_JSON)
    public Response sayHelloAndGetPath(@QueryParam("name") String name,
                             @QueryParam("surname") String surname,
                             @PathParam("xyz") String welcome) {

        LOG.info(welcome + " " + name + " " + surname + "!");

        return Response.ok(welcome + " " + name + " " + surname + "!"
                + "\n" + "AbsolutePath is: " + uriInfo.getAbsolutePath()
                + "\n" + "PathParameters are : " + uriInfo.getPathParameters()
        ).build();
    }

    // Ex.3
    @GET
    @Path("/header")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkMethod(@HeaderParam("user-agent") String userAgent) {

        LOG.info(userAgent);

        return Response.ok("User agent is: " + userAgent).build();
    }

    //Ex.4
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON) // automatycznie wie ze ma wsadzic wynik w obiekt json
    public Response usersList() {         // zeby nie bral Credenatials - @JsonIgnore przy polu

        if (us.getBase().isEmpty())
            return Response.status(204).build();

        return Response.ok(us.getBase()).build();
    }

//     if (users.isEmpty()) {
//        return Response.noContent().build();
//    } else {
//        return Response.ok(users).build();
//    }

    // Ex.5
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getData(@PathParam("id") int id) {

        if (us.getBase().containsKey(id)) {
            return Response.ok(us.getBase().get(id)).build();
        } else {
            return Response.status(404).build();
        }
    }

//    if (user != null) {
//        return Response.ok(user).build();
//    } else {
//        return Response.noContent().build();
//    }

    //Ex.6
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/add")
    @Produces(MediaType.TEXT_PLAIN)
    public Response addUser(User user) {

        if (user == null) {
            return Response.status(404).build();
        } else {
            us.add(user);
            return Response.ok(us.getBase()).build();
        }
    }

/*    @PUT
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User incomingUser) {
        if (incomingUser.getName() == "" || incomingUser.getSurname() == "") {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        User user = new User(incomingUser.getName(), incomingUser.getSurname(), null);
        userStore.add(user);
        return Response.ok(userStore.getBase().values()).build();
    }*/

    //Ex.7
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response addUser(@QueryParam("id") Integer id) throws InvalidObjectException {

        if (us.getBase().containsKey(id)) {
            us.remove(id);
            return Response.ok(us.getBase()).build();
        } else {
            return Response.status(404).build();
        }
    }

/*    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response removeUser(@PathParam("id") Integer userId) {
        try {
            userStore.remove(userId);
            return Response.ok("User [" + userId + "] deleted").build();
        } catch (InvalidObjectException e) {
            LOG.error(e.getMessage());
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
    */

    // Ex.8
    @POST
    @Path("/login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(@FormParam("username") String username,
                          @FormParam("password") String password) throws InvalidObjectException, URISyntaxException {

        Optional<User> user = us.getBase().values().stream()
                .filter(u -> u.getCredentials() != null)
                .filter(u -> u.getCredentials().getUsername().equals(username))
                .findFirst();

        if (user.isPresent()) {
            if(user.get().getCredentials().getPassword().equals(password)) {
                return Response.ok().build();
            } else {
                return Response.temporaryRedirect(new URI("/users/hello/")).build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}

/*
    @POST
    @Path("/login")
    @Produces({MediaType.TEXT_PLAIN})
    public Response login(@FormParam("username") String username, @FormParam("pass") String password) throws URISyntaxException {
        LOG.info("Login attempt : [" + username + "]");

        Map<String, User> usersByUsername = userStore
                .getBase()
                .values()
                .stream()
                .filter(x -> x.getCredentials() != null)
                .collect(Collectors.toMap(x -> x.getCredentials().getUsername(), x -> x));

        if (usersByUsername.keySet().contains(username)) {
            if (usersByUsername.get(username).getCredentials().getPassword().equals(password)) {
                LOG.info("Login ok");
                return Response.ok("Wszystko poszło ok " + username).build();
            } else {
                LOG.info("Login ok, haslo nie ok. " + usersByUsername.get(username).getCredentials().getPassword() + ":" + password);
                return Response.temporaryRedirect(new URI("/users/hello/")).build();
            }
        } else {
            LOG.info("nie ma takiego ");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
*/
