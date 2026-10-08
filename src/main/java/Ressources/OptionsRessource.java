package Ressources;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.List;

@Path("/options")
public class OptionsRessource {
    OptionBusiness optionBusiness = new OptionBusiness();

    //Récupération de la liste de toutes les options
    @GET
    @Path( "/list")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public List<Option> getlist() {
        return optionBusiness.getListeOptions();

    }
    //Récupération d'une option ayant un code donnée
    @GET
    @Path("/{ param}")
    @Produces(MediaType.APPLICATION_JSON)
     public Option getByCode(@PathParam("param") int code){
         return optionBusiness.getOptionByCode(code);

     }
     //Récupération de la liste des options d'un domaine spécifique
    @GET

    @Produces(MediaType.APPLICATION_JSON)
    public List<Option> getOptions(@QueryParam("domaine") String domaine) {

        if (domaine != null) {
            return optionBusiness.getOptionsByDomaine(domaine);
        }

        return optionBusiness.getListeOptions();
    }
    // Création d'une nouvelle option
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOption(Option option) {

        boolean added = optionBusiness.addOption(option);

        if (added) {
            return Response
                    .status(Response.Status.CREATED)
                    .entity(option)
                    .build();
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
                .build();
    }
    //Modification d'une option ayant un code spécifique
    @PUT
    @Path("/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOption(
            @PathParam("code") int code,
            Option option) {

        boolean updated =
                optionBusiness.updateOption(code, option);

        if (updated) {
            return Response.ok(option).build();
        }

        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    }
    //Suppression d'une option ayant un code spécifique
    @DELETE
    @Path("/{code}")
    public Response deleteOption(
            @PathParam("code") int code) {

        boolean deleted =
                optionBusiness.deleteOption(code);

        if (deleted) {
            return Response.ok().build();
        }

        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    }
}
