package dao;

import models.Breed;

import java.util.List;

public interface BreedDao {
    //create
    void add (Breed breed);

    //read
    List<Breed> getAllByTypeId(int typeId);

    List<Breed> getAll();

    Breed findById(int id);

    //update
    void update(int id, String name, String description);

    //delete
    void deleteById(int id);
    void clearAllBreedsByTypeId(int typeId);
}
