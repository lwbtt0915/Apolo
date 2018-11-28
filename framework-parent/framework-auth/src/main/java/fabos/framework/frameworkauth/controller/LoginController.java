package fabos.framework.frameworkauth.controller;

import fabos.framework.frameworkauth.common.ServiceResult;
import fabos.framework.frameworkauth.model.User;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import static javax.ws.rs.core.Response.status;

/**
 * @Author: lwb
 * @Description:
 * @Date: Created in  15:22 2018/11/21
 * @modified By:
 */
@RestController
public class LoginController {


    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginAuthentica(User user) {
        ServiceResult result=new ServiceResult();
        result.setData("DDDD");
     return  Response.status(Status.OK).entity(result).build();
    }
}
