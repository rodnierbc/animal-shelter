package dao;

import models.Type;
import java.util.List;

public interface TypeDao {
    //create
    void add (Type type);

    //read
    List<Type> getAll();

    Type findById(int id);

    //update
    void update(int id, String name, String description);

    //delete
    void deleteById(int id);
    void clearAllTypes();
}
