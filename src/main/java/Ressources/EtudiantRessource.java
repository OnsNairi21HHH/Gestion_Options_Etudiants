package Ressources;
import entities.Etudiant;
import entities.EtudiantList;
import metiers.EtudiantBusiness;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import entities.Option;
import metiers.OptionBusiness;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.List;

@Path("/etudiants")

public class EtudiantRessource {
    EtudiantBusiness etudiantBusiness =
            new EtudiantBusiness();

    OptionBusiness optionBusiness =
            new OptionBusiness();


    //  Création d'un étudiant
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEtudiant(
            Etudiant etudiant,
            @QueryParam("codeOption") int codeOption) {

        Option option = optionBusiness.getOptionByCode(codeOption);

        if (option == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Option inexistante")
                    .build();
        }

        etudiant.setOption(option);

        boolean added =
                etudiantBusiness.addEtudiant(etudiant);

        if (added) {
            return Response
                    .status(Response.Status.CREATED)
                    .entity(etudiant)
                    .build();
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
                .build();
    }


    //  Récupérer tous les étudiants
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Etudiant> getAllEtudiants() {

        return etudiantBusiness.getAllEtudiants();
    }


    //  Récupérer un étudiant par identifiant
    @GET
    @Path("/{identifiant}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiant(
            @PathParam("identifiant")
            String identifiant) {

        Etudiant etudiant =
                etudiantBusiness.getEtudiantByIdentifiant(identifiant);

        if (etudiant != null) {

            return Response
                    .ok(etudiant)
                    .build();
        }

        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    }


    // Supprimer un étudiant
    @DELETE
    @Path("/{identifiant}")
    public Response deleteEtudiant(
            @PathParam("identifiant")
            String identifiant) {

        boolean deleted =
                etudiantBusiness.deleteEtudiant(identifiant);

        if (deleted) {

            return Response
                    .ok()
                    .build();
        }

        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    }


    //  Modifier un étudiant
    @PUT
    @Path("/{identifiant}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(
            @PathParam("identifiant")
            String identifiant,
            Etudiant etudiant) {

        boolean updated =
                etudiantBusiness.updateEtudiant(
                        identifiant,
                        etudiant);

        if (updated) {

            return Response
                    .ok(etudiant)
                    .build();
        }

        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    }


    //  Récupérer les étudiants d'une option
    @GET
    @Path("/option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(
            @QueryParam("codeOption") int codeOption) {


        Option option = optionBusiness.getOptionByCode(codeOption);


        if (option == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }


        List<Etudiant> liste =
                etudiantBusiness.getEtudiantsByOption(option);


        if (liste.isEmpty()) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }


        EtudiantList result =
                new EtudiantList(liste);

        return Response
                .ok(result)
                .build();
    }}