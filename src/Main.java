import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.io.File;

public class Main {
    final static String BBDDper = "/home/user/Escriptori/DBpersona.yap";

    public static void main(String[] args) {

        new File(BBDDper).delete();
        //S'obre la base de dades
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BBDDper);

        //Dades per introduir
        Empleat pers1 = new Empleat("Anna", "Reus");
        Empleat pers2 = new Empleat("Antonio", "Valls");
        Empleat pers3 = new Empleat("Jordi", "Tarragona");
        Empleat pers4 = new Empleat("Elena", "Tortosa");

        //S'emmagatzema les dades
        db.store(pers1);
        db.store(pers2);
        db.store(pers3);
        db.store(pers4);

        //Cercar persones que no existeixen
        Empleat pers5 = new Empleat("x", null);
        CercarEmpelat(db, pers5);

        //Cercar les persones de nom Anna
        pers5 = new Empleat("Anna", null);
        CercarEmpelat(db, pers5);

        //Cercar les persones que viuen a Valls
        pers5 = new Empleat(null, "Valls");
        CercarEmpelat(db, pers5);

        //Llistar totes les persones
        CercarEmpelat(db, new Empleat());

        //Modificar el registre de les persones de nom Antonio
        pers5 = new Empleat("Antonio", null);
        Empleat pers6 = new Empleat("Joan", "Vendrell");
        ModificarEmpelat(db, pers5, pers6);

        //Modificar el registre de les persones que viuen a Tarragona
        pers5 = new Empleat(null, "Tarragona");
        pers6 = new Empleat("Alicia", "Reus");
        ModificarEmpelat(db, pers5, pers6);

        //De nou llistar totes les persones
        CercarEmpelat(db, new Empleat());

        //Modificar el registre de les persones de nom Elena
        EsborrarEmpelat(db, pers4);

        //Modificar el registre de les persones que viuen a Reus
        pers5 = new Empleat(null, "Reus");
        EsborrarEmpelat(db, pers5);

        //De nou llistar totes les persones
        CercarEmpelat(db, new Empleat());

        //Es tanca la base de dades
        db.close();
    }

    //Cerca una Persona (persona) a la base de dades (dataBase)
    public static void CercarEmpelat(ObjectContainer dataBase, Empleat empleat) {
        ObjectSet<Empleat> resultat = dataBase.queryByExample(empleat);
        if (resultat.size() == 0) {
            System.out.println("No hi ha Registres d'aquestes persones..\n");
        } else {
            System.out.println("Número de registres: " + resultat.size());
            while (resultat.hasNext()) {
                Empleat p = (Empleat) resultat.next();
                System.out.println("Nom: " + p.getNom() + ", Ciutat: " + p.getCiutat());
            }
            System.out.println();
        }

    }

    //Substitueix la Persona (vella) per la Persona(nova) a la base de dades(dataBase)
    public static void ModificarEmpelat(ObjectContainer dataBase, Empleat vella, Empleat nova) {
        ObjectSet<Empleat> resultat = dataBase.queryByExample(vella);
        if (resultat.size() == 0) {
            System.out.println("No existeix la persona de nom: " + vella.getNom() + "que viu a: " + vella.getCiutat() + "\n");
        } else {
            Empleat existeix = (Empleat) resultat.next();
            existeix.setNom(nova.getNom());
            existeix.setCiutat(nova.getCiutat());
            dataBase.store(existeix);
            System.out.println("MODIFICAT Nom: " + existeix.getNom() + ", Ciutat: " + existeix.getCiutat() + "\n");
        }
    }


    //Esborra de la base de dades(dataBase) a la Persona(persona)
    public static void EsborrarEmpelat(ObjectContainer dataBase, Empleat empleat) {
        ObjectSet<Empleat> resultat = dataBase.queryByExample(empleat);
        if (resultat.size() == 0) {
            System.out.println("No existeix la persona de nom: " + empleat.getNom() + "que viu a: " + empleat.getCiutat() + "\n");
        } else {
            Empleat existeix = (Empleat) resultat.next();
            System.out.println("Resgistres per esborrar: " + resultat.size());
            if (resultat.size() > 1) {
                dataBase.delete(existeix);
                System.out.println("Esborrat ...");
                while (resultat.hasNext()) {
                    Empleat emp = resultat.next();
                    dataBase.delete(emp);
                    System.out.println("Esborrat ...");
                }
            } else {
                dataBase.delete(existeix);
                System.out.println("Esborrat ...");
            }
        }
        System.out.println();
    }
}