package com.example.repairershubofficial;

public class FetchDetails {
    String name;
    String address;
    String email;
    String contact;
    String problem;
    String service;
    public FetchDetails() {
    }

    public FetchDetails(String name, String address, String email, String contact, String problem, String service) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.contact = contact;
        this.problem = problem;
        this.service = service;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getProblem() {
        return problem;
    }

    public String getService() { return service;}
}

