package babouscorp.entretienskis;

public class metaRow4Skis_PagePrincipale {
    private String id;
    private String nom;
    private String nbSortieSaison;
    private String nbSortieAffutage;
    private String nbSortieFartage;
    private String nbReparation;
    private String nbReparationSaison;

    public metaRow4Skis_PagePrincipale(String id, String nom, int nbSortieSaison, int nbSortieAffutage,
                                       int nbSortieFartage, int nbReparation, int nbReparationSaison) {
        super();
        this.id = id;
        this.nom = nom;
        this.nbSortieSaison = Integer.toString(nbSortieSaison);
        this.nbSortieAffutage = Integer.toString(nbSortieAffutage);
        this.nbSortieFartage = Integer.toString(nbSortieFartage);
        this.nbReparation = Integer.toString(nbReparation);
        this.nbReparationSaison = Integer.toString(nbReparationSaison);



    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getNbSortieSaison() {
        return nbSortieSaison;
    }

    public String getNbSortieAffutage() {
        return nbSortieAffutage;
    }

    public String getNbSortieFartage() {
        return nbSortieFartage;
    }

    public String getNbReparation() { return nbReparation; }

    public String getNbReparationSaison() { return nbReparationSaison; }

}
