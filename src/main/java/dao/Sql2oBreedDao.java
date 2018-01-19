package dao;

import models.Breed;

import models.Type;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oBreedDao implements BreedDao {

    private final Sql2o sql2o;

    public Sql2oBreedDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Breed breed) {
        String sql = "INSERT INTO breeds (name, description, typeId) VALUES (:name, :description, :typeId)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql)
                    .addParameter("name", breed.getName())
                    .addParameter("description", breed.getDescription())
                    .addParameter("typeId", breed.getTypeId())
                    .addColumnMapping("NAME", "name")
                    .addColumnMapping("DESCRIPTION", "description")
                    .addColumnMapping("TYPEID", "typeId")
                    .executeUpdate()
                    .getKey();
            breed.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Breed> getAllByTypeId(int typeId) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM breeds WHERE typeId = :typeId")
                    .addParameter("typeId", typeId)
                    .executeAndFetch(Breed.class);
        }
    }

    @Override
    public Breed findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM breeds WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Breed.class);
        }
    }
    @Override
    public void update(int id, String newName, String newDescription) {
        String sql = "UPDATE breeds SET (name, description) = (:name, :description) WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name", newName)
                    .addParameter("description", newDescription)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from breeds WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllBreedsByTypeId(int typeId) {
        String sql = "DELETE from breeds WHERE typeId = :typeId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).addParameter("typeId", typeId).executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Breed> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM breeds")
                    .executeAndFetch(Breed.class);
        }
    }
}
