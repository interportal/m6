package md.curs.rest;

import md.curs.model.User;
import md.curs.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by MG
 */
@Component
@Path("/user")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GET
    public Iterable<User> find(@QueryParam("q") String query) {
        if (query == null) {
            return userRepo.findAll();
        }
        return userRepo.find(query);
    }

    @GET
    @Path("{id: \\d+}")
    public User getOne(@PathParam("id") Long id) {
        return userRepo.findOne(id);
    }

    @POST
    @Transactional
    public User add(User user) {
        return userRepo.save(user);
    }

    @PUT
    @Path("{id: \\d+}")
    public User update(@PathParam("id") Long id, User user) {
        if (user.getId() == null) {
            user.setId(id);
        }

        if (!id.equals(user.getId())) {
            throw new BadRequestException("User ID does not match");
        }
        return userRepo.save(user);
    }

    @DELETE
    @Path("{id: \\d+}")
    public void delete(@PathParam("id") Long id) {
        userRepo.delete(id);
    }
}
