package models;

import java.util.Objects;

public class Customer {
    private int id;
    private String name;
    private String phone;
    private int typeId;
    private int breedId;

    public Customer(String name, String phone, int typeId, int breedId){
        this.name = name;
        this.phone = phone;
        this.typeId = typeId;
        this.breedId = breedId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getBreedId() {
        return breedId;
    }

    public void setBreedId(int breedId) {
        this.breedId = breedId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        if (id != customer.id) return false;
        if (typeId != customer.typeId) return false;
        if (breedId != customer.breedId) return false;
        return name.equals(customer.name) &&
                phone.equals(customer.phone);
    }

    @Override
    public int hashCode() {

        int result = name.hashCode();
        result = phone.hashCode();
        result = 31 * result + id;
        result = 31 * result + typeId;
        result = 31 * result + breedId;
        return result;
    }
}
