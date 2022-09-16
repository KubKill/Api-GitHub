package com.atipera.api.git_data.DTOs;

import java.util.ArrayList;
import java.util.List;

public class RepositoryDTO {

    private String name;
    private String owner;

    private List<BranchDTO> branches = new ArrayList<>();

    public RepositoryDTO(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    public void addBranch(BranchDTO branch) {
        this.branches.add(branch);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<BranchDTO> getBranches() {
        return branches;
    }

    public void setBranches(List<BranchDTO> branches) {
        this.branches = branches;
    }
}
