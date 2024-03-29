import java.util.ArrayList;
import java.util.List;


public class grilleHumain {
    // Attributs
    private ArrayList<Humain>[][] grille;
    public static MTRandom random = new MTRandom();

    // Constructeur

    /* grilleHumain, méthode de contruction de la classe grilleHumain qui créer un tableau à deux dimensions,
     * et qui place un tableau ou peuvent être placer des objets de classe Humain dans chaque case. */

    @SuppressWarnings("unchecked")
    public grilleHumain(int lignes, int colonnes) {
        this.grille = new ArrayList[lignes][colonnes];
        for (int i = 0; i < lignes; i++) {
            initialiserLigne(i, colonnes);
        }
    }

    /* InitialiserLigne, méthode utiliser dans le constructeur pour initialiser les lignes du tableau. */

    private void initialiserLigne(int ligne, int colonnes) {
        for (int j = 0; j < colonnes; j++) {
            grille[ligne][j] = new ArrayList<Humain>();
        }
    }

    //Getteurs et Setteurs

    /* addHumain,  méthode qui prend en paramètres un entier ligne, un entier colonne et un humain, et qui
     * l'ajoute à la case donnée.*/

    public void addHumain(Humain humain) {
        int ligne = (int) random.negExp(grille.length);
        int colonne = (int) random.negExp(grille.length);
        grille[this.HorsTab(ligne,grille.length)][this.HorsTab(colonne, grille.length)].add(humain);
    }

    public void addAllHumains(int n, int infected){
        for(int i = 0 ; i < infected ; i++){
            Humain infect = new Humain('I');
            this.addHumain(infect);
        }
        for(int i = 0 ; i < (n - infected) ; i++){
            Humain h = new Humain('S');
            this.addHumain(h);
        }
    }

    /* getHumain, méthode qui prend en paramètre une ligne et une colonne et qui renvoie le tableau d'Humain
     * situé dans cette case. */

    public List<Humain> getHumains(int ligne, int colonne) {
        validerPosition(ligne, colonne);
        return grille[ligne][colonne];
    }
    
    /* afficherGrille, méthod0e d'affichage d'un êtreligne humain. Il affiche les statuts des être humains ou 0 si
     * il n'y a aucun être humain dans une case. */

    public void afficherGrille() {
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[i].length; j++) {
                System.out.print("| ");
                if (grille[i][j].isEmpty()) {
                    System.out.print("0 ");
                } else {
                    for (Humain humain : grille[i][j]) {
                        System.out.print(humain.GetStatut() + " ");
                    }
                }
            }
            System.out.println("|");
            System.out.println(String.format(String.format("%%0%dd", (grille[i].length * 4) + 1), 0).replace("0","-"));
        }
    }

    /* validerPosition, méthode de validation qui vérifie qu'une position est bien situé dans le tableau. */

    private void validerPosition(int ligne, int colonne) {
        if (ligne < 0 || ligne >= grille.length || colonne < 0 || colonne >= grille.length) {
            throw new IllegalArgumentException("Position hors limites");
        }
    }

    private int HorsTab (int index, int tailleTab){
        if (index<0){
            return index + tailleTab;
        }
        else if (index>=tailleTab){
            return index % tailleTab;
        }
        return index;
    }

    /* calculInfected, méthode qui prend en paramètre un entier ligne et un entier colonne, et qui renvoie le nombre
     * d'humain infectés à cette case.*/
    public int calculInfected(int ligne, int colonne){
        List<Humain> list = getHumains(ligne,colonne);
        int infected = 0;
        for (int i = 0; i < list.size(); i++){
            Humain humain = list.get(i);
            if (humain.GetStatut() == 'I'){
                infected+=1;
            }
        }
        return infected;
    }
    
    /* infectedAround, méthode qui prend en paramètre un entier ligne et un entier colonne, et qui renvoie le nombre
     * d'humain infectés à cette case, et les 8 cases autour.*/
    public int infectedAround(int ligne, int colonne){
        int infected = 0;
        
        for (int i = ligne-1; i <= ligne+1; i++){
            for (int j = colonne-1; j <= colonne+1; j++){
                int ni = HorsTab(i, grille.length);
                int nj = HorsTab(j, grille.length);
                System.out.println("i= "+ni+"| j= "+nj);
                infected += calculInfected(HorsTab(i, grille.length),HorsTab(j, grille.length));


                //infected += calculInfected(i%this.grille.length, j%this.grille[0].length);
            }
        }
        return infected;
    }

    public double probability(Humain h, int ligne, int colonne){
        return 1-Math.exp(-0.5*infectedAround(ligne, colonne));
    }

    public void checkEtat(Humain h, int ligne, int colonne){
        switch (h.GetStatut()) {
            case 'S':
                if(random.negExp(1)<this.probability(h, ligne, colonne)){
                    h.SetStatut('E');
                    h.SetTemps(0);
                }
                break;
            case 'E':
                if(h.GetTemps()>h.GetdE()){
                    h.SetStatut('I');
                    h.SetTemps(0);
                }
                else{
                    h.SetTemps(h.GetTemps()+1);
                }
                break;
            case 'I':
                if(h.GetTemps()>h.GetdI()){
                    h.SetStatut('R');
                    h.SetTemps(0);
                }
                else{
                    h.SetTemps(h.GetTemps()+1);
                }
                break;
            case 'R':
                if(h.GetTemps()>h.GetdR()){
                    h.SetStatut('S');
                    h.SetTemps(0);
                }
                else{
                    h.SetTemps(h.GetTemps()+1);
                }
                break;
        }
    }

    public void simulation(int nbTest){
        for (int i = 0 ; i < nbTest; i++){

            for (int j = 0 ; j < grille.length; j++){
                this.checkEtat(humain, j, k);





        }


    }




}