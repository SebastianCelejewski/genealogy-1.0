package pl.sebcel.genealogy.dto;

public class ClanEditData {

    private Long id;
    private String name;
    private String description;
    private ReferenceListElement root;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ReferenceListElement getRoot() {
        return root;
    }

    public void setRoot(ReferenceListElement root) {
        this.root = root;
    }
}