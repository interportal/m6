package md.curs.soap;

import md.curs.model.User;
import md.curs.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.*;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MG
 */
@Component
@WebService
public class SoapUserService {

    private UserRepo userRepo;

    public SoapUserService() {}

    @Autowired
    public SoapUserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @WebMethod
    @WebResult(name = "User")
    public List<User> find(@WebParam(name = "query") String query) {
        if (query == null) {
            query = "";
        }
        return userRepo.find(query);
    }

    @WebMethod
    public User getOne(@WebParam(name="id") Long id) {
        return userRepo.findOne(id);
    }

    @WebMethod
    @PreAuthorize("hasAuthority('GLOBAL_ADMIN')")
    public User save(@WebParam(name = "user") User user) {
        return userRepo.save(user);
    }

    @Oneway
    @WebMethod
    @PreAuthorize("hasAuthority('GLOBAL_ADMIN')")
    public void delete(@WebParam(name="id") Long id) {
        userRepo.delete(id);
    }
}
