package dao;

import models.Type;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;


public class Sql2oTypeDao implements TypeDao {

    private final Sql2o sql2o;

    public Sql2oTypeDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Type type) {
        String sql = "INSERT INTO types (name, description) VALUES (:name, :description)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql)
                    .bind(type)
                    .executeUpdate()
                    .getKey();
            type.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Type> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM types")
                    .executeAndFetch(Type.class);
        }
    }

    @Override
    public Type findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM types WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Type.class);
        }
    }

    @Override
    public void update(int id, String newName, String newDescription) {
        String sql = "UPDATE types SET (name, description) = (:name, :description) WHERE id=:id";
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
        String sql = "DELETE from types WHERE id = :id";
        String deleteBreeds = "DELETE from breeds WHERE typeId = :typeId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteBreeds)
                    .addParameter("typeId", id)
                    .executeUpdate();

        } catch (Sql2oException ex){
            System.out.println(ex);
        }

    }

    @Override
    public void clearAllTypes() {
        String sql = "DELETE from types";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}
