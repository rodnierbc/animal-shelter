import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.Sql2oAnimalDao;
import dao.Sql2oBreedDao;
import dao.Sql2oTypeDao;
import models.Animal;
import models.Type;
import models.Breed;
import models.Animal;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;
import java.time.format.DateTimeFormatter;



public class App {
    public static void main(String[] args){
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/animalShelter.db;INIT=RUNSCRIPT from 'classpath:db/animalShelterDB.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oTypeDao typeDao = new Sql2oTypeDao(sql2o);
        Sql2oBreedDao breedDao = new Sql2oBreedDao(sql2o);
        Sql2oAnimalDao animalDao = new Sql2oAnimalDao(sql2o);
        DateTimeFormatter dTF = DateTimeFormatter.ofPattern("yyyy-mm-dd");

        //Type-------------------------------------------------------------
        get("/type/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "type/type-form.hbs");
        }, new HandlebarsTemplateEngine());
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            ArrayList<Type> types = (ArrayList<Type>) typeDao.getAll();
            model.put("types", types);
            return new ModelAndView(model, "type/index.hbs");
        }, new HandlebarsTemplateEngine());
        //post: new type form
        post("/type/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            String description = request.queryParams("description");
            Type type = new Type(name, description);
            typeDao.add(type);
            ArrayList<Type> types = (ArrayList<Type>) typeDao.getAll();
            model.put("types", types);
            return new ModelAndView(model, "type/index.hbs");
        }, new HandlebarsTemplateEngine());
        get("/type/:id/update", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idType = Integer.parseInt(request.params("id"));
            Type editType = typeDao.findById(idType);
            model.put("editType", editType);
            return new ModelAndView(model, "type/type-form.hbs");
        }, new HandlebarsTemplateEngine());
        post("/type/:id/update", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String nameUpdate = request.queryParams("nameUpdate");
            String descriptionUpdate = request.queryParams("descriptionUpdate");
            int idType = Integer.parseInt(request.params("id"));
            ArrayList<Breed> breeds = (ArrayList<Breed>) breedDao.getAllByTypeId(idType);
            typeDao.update(idType,nameUpdate, descriptionUpdate);
            model.put("type", typeDao.findById(idType));
            model.put("breeds", breeds);
            return new ModelAndView(model, "type/type-detail.hbs");
        }, new HandlebarsTemplateEngine());
        get("/type/:id/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idType= Integer.parseInt(request.params("id"));
            typeDao.deleteById(idType);
            ArrayList<Type> types = (ArrayList<Type>) typeDao.getAll();
            model.put("delete","delete");
            model.put("types", types);
            return new ModelAndView(model, "type/index.hbs");
        }, new HandlebarsTemplateEngine());
        get("/type/:id/view", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idType = Integer.parseInt(request.params("id"));
            Type type = typeDao.findById(idType);
            ArrayList<Breed> breeds = (ArrayList<Breed>) breedDao.getAllByTypeId(idType);
            model.put("type", type);
            model.put("breeds",breeds);
            return new ModelAndView(model, "type/type-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //breed-------------------------------------------------------------------
        get("/type/:id/breed/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idType = Integer.parseInt(request.params("id"));
            model.put("idType",idType);
            return new ModelAndView(model, "breed/breed-form.hbs");
        }, new HandlebarsTemplateEngine());
        get("/type/:id/breed", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idType = Integer.parseInt(request.params("id"));
            ArrayList<Breed> breeds = (ArrayList<Breed>) breedDao.getAllByTypeId(idType);
            model.put("breeds", breeds);
            return new ModelAndView(model, "breed/index.hbs");
        }, new HandlebarsTemplateEngine());
        //post: new type form
        post("/type/:id/breed/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            String description = request.queryParams("description");
            int typeId = Integer.parseInt(request.params("id"));
            Breed breed = new Breed(name, description, typeId);
            breedDao.add(breed);
            Type type = typeDao.findById(typeId);
            ArrayList<Breed> breeds = (ArrayList<Breed>) breedDao.getAllByTypeId(typeId);
            model.put("breeds", breeds);
            model.put("type",type);
            return new ModelAndView(model, "type/type-detail.hbs");
        }, new HandlebarsTemplateEngine());
        get("/type/:typeId/breed/:id/update", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idBreed = Integer.parseInt(request.params("id"));
            int idType = Integer.parseInt(request.params("typeId"));
            Breed editBreed = breedDao.findById(idBreed);
            model.put("editBreed", editBreed);
            model.put("idType",idType);
            return new ModelAndView(model, "breed/breed-form.hbs");
        }, new HandlebarsTemplateEngine());
        post("/type/:typeId/breed/:id/update", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String nameUpdate = request.queryParams("nameUpdate");
            String descriptionUpdate = request.queryParams("descriptionUpdate");
            int idBreed = Integer.parseInt(request.params("id"));
            int idType = Integer.parseInt(request.params("typeId"));
            breedDao.update(idBreed,nameUpdate, descriptionUpdate);
            model.put("breed", breedDao.findById(idBreed));
            model.put("idType",idType);
            return new ModelAndView(model, "breed/breed-detail.hbs");
        }, new HandlebarsTemplateEngine());
        get("/type/:typeId/breed/:breedId/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idType = Integer.parseInt(request.params("typeId"));
            int idBreed= Integer.parseInt(request.params("breedId"));
            Type type = typeDao.findById(idType);
            breedDao.deleteById(idBreed);
            ArrayList<Breed> breeds = (ArrayList<Breed>) breedDao.getAllByTypeId(idType);
            model.put("type",type);
            model.put("delete","delete");
            model.put("breeds", breeds);
            return new ModelAndView(model, "type/type-detail.hbs");
        }, new HandlebarsTemplateEngine());
        get("type/:typeId/breed/:breedId/view", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idType = Integer.parseInt(request.params("typeId"));
            int idBreed = Integer.parseInt(request.params("breedId"));
            Breed breed = breedDao.findById(idBreed);
            model.put("idType",idType);
            model.put("breed", breed);
            return new ModelAndView(model, "breed/breed-detail.hbs");
        }, new HandlebarsTemplateEngine());
        //animal ---------------------------------------------
        get("/animal/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            ArrayList<Type> types = (ArrayList<Type>) typeDao.getAll();
            ArrayList<Breed> breeds = (ArrayList<Breed>) breedDao.getAll();//need to change in the future
            model.put("types", types);
            model.put("breeds", breeds);
            return new ModelAndView(model, "animal/animal-form.hbs");
        }, new HandlebarsTemplateEngine());
        post("/animal/new", (Request request, Response response) -> {
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            //int gender = Integer.parseInt(request.queryParams("gender"));
            int gender = 1;
            LocalDate admittanceDate = LocalDate.parse(request.queryParams("admittanceDate"));
            int typeId = Integer.parseInt(request.queryParams("typeId"));
            int breedId = Integer.parseInt(request.queryParams("breedId"));
            Animal animal = new Animal(name, gender, admittanceDate, typeId, breedId);
            animalDao.add(animal);
            ArrayList<Animal> animals = (ArrayList<Animal>) animalDao.getAll();
            model.put("animals", animals);
            return new ModelAndView(model, "animal/index.hbs");
        }, new HandlebarsTemplateEngine());
//        get("/", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//            ArrayList<Animal> animals = (ArrayList<Animal>) animalDao.getAll();
//            model.put("animals", animals);
//            return new ModelAndView(model, "animal/index.hbs");
//        }, new HandlebarsTemplateEngine());
//        get("/animal", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//            ArrayList<Animal> animals = (ArrayList<Animal>) animalDao.getAll();
//            model.put("animals", animals);
//            return new ModelAndView(model, "animal/index.hbs");
//        }, new HandlebarsTemplateEngine());

    }
}
