package dao;

import models.Animal;
import models.Type;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.time.LocalDateTime;
import java.util.List;

public class Sql2oAnimalDao implements AnimalDao {
    private final Sql2o sql2o;

    public Sql2oAnimalDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Animal animal) {
        String sql = "INSERT INTO animals (name, gender, admittanceDate, typeId, breedId) VALUES (:name, :gender, :admittanceDate, :typeId, :breedId)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql)
                    .addParameter("name", animal.getName())
                    .addParameter("gender", animal.getGender())
                    .addParameter("admittanceDate",animal.getAdmittanceDate())
                    .addParameter("typeId", animal.getTypeId())
                    .addParameter("breedId", animal.getBreedId())
                    .executeUpdate()
                    .getKey();
            animal.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Animal> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM animals")
                    .executeAndFetch(Animal.class);
        }
    }

    @Override
    public Animal findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM animals WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Animal.class);
        }
    }

    @Override
    public void update(int id, String name, boolean gender, String admittanceDate, int typeId, int breedId) {
        String sql = "UPDATE animals SET (name, gender, admittanceDate, typeId, breedId) = (:name, :gender, :admittanceDate, :typeId, :breedId) WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("gender", gender)
                    .addParameter("admittanceDate", admittanceDate)
                    .addParameter("typeId", typeId)
                    .addParameter("breedId", breedId)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from animals WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllAnimals() {
        String sql = "DELETE from animals";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}
