public class Empleat {

    private String nom;
    private String ciutat;

    public Empleat(String nom, String ciutat){
        this.nom=nom;
        this.ciutat=ciutat;
    }

    public Empleat(){
        this.nom=null;
        this.ciutat=null;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getCiutat() {
        return ciutat;
    }
    public void setCiutat(String ciutat) {
        this.ciutat = ciutat;
    }

}
