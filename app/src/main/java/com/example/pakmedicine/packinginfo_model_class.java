package com.example.pakmedicine;

public class packinginfo_model_class {
    String gram,name,price;

    public packinginfo_model_class(String gram, String price) {
        this.gram = gram;
        this.price = price;
    }

    public String getGram() {
        return gram;
    }

    public void setGram(String gram) {
        this.gram = gram;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
