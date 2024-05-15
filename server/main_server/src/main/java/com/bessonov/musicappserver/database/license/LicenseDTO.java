package com.bessonov.musicappserver.database.license;

public class LicenseDTO {
    private int id;
    private String name;

    public LicenseDTO () {
        this.id = -1;
        this.name = "";
    }

    public LicenseDTO (License license) {
        if (license == null) {
            this.id = -1;
            this.name = "";
        }
        else {
            this.id = license.getId();
            this.name = license.getName();
        }
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

    @Override
    public String toString() {
        return "LicenseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
