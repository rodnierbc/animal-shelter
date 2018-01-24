package dao;

import models.Animal;
import models.Type;

import java.time.LocalDateTime;
import java.util.List;

public interface AnimalDao {
    //create
    void add (Animal animal);

    //read
    List<Animal> getAll();

    Animal findById(int id);

    //update
    void update(int id, String name, boolean gender, String admittanceDate, int typeId, int breedId);

    //delete
    void deleteById(int id);
    void clearAllAnimals();
}