package developer.ezandro.fipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle {
    @JsonAlias("codigo")
    private String code;

    @JsonAlias("nome")
    private String name;

    protected Vehicle() {
    }

    protected Vehicle(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return String.format("Code: %3s - Description: %s",
                this.getCode(),
                this.getName());
    }
}