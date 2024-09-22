package formation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Gestion des services liés à une année de formation. Implémentation de
 * l'interface InterGestionFormation. Cette classe est sérialisable.
 * 
 * @author Liam Frit, Sully Millet, Andrea Le dortz.
 *
 */
public class GestionEtudiant implements InterEtudiant, Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 4169764065360571366L;
  
  public GestionFormation gestionForm;
  public Set<Etudiant> listeEtudiants = new HashSet<Etudiant>();
  
  public Etudiant etu = null;
  public boolean etudiantConnecte = false;
  
  
  @Override
  public int inscription(InformationPersonnelle infos, String motDePasse) {
    if (motDePasse != null) {
      Etudiant e = new Etudiant(infos, motDePasse);
      this.listeEtudiants.add(e);
      return e.getIdEtu();
    }
    return -1;
  }
  
  @Override
  public boolean connexion(int numero, String motDePasse) {
    for (Etudiant e : this.listeEtudiants) {
      if (numero == e.getIdEtu() && motDePasse.equals(e.getMotDePasse())) {
        etudiantConnecte = true;
        this.etu = e;
        return true;
      }
    }
    return false;
  }
  
  @Override
  public void deconnexion() throws NonConnecteException {
    if (etudiantConnecte) {
      etudiantConnecte = false;
      this.etu = null;
    }
  }
  
  /**
   * Retourne le nombre d'options que l'�tudiant doit choisir au total.
   *
   * @return le nombre d'options que l'�tudiant doit choisir ou -1 si ce nombre
   *         n'a pas �t� encore d�fini.
   * @throws NonConnecteException si la m�thode est appel�e alors que l'�tudiant
   *         n'est pas connect�
   */
  @Override
  public int nombreOptions() throws NonConnecteException {
    if (etudiantConnecte) {
      if (this.gestionForm.getNbOption() == -1) {
        return -1;
      } else {
        return this.gestionForm.getNbOption();
      }
    }
    return -1;
  }
  
  @Override
  public boolean choisirOption(UniteEnseignement ue)
      throws NonConnecteException {
    //Set<Etudiant> setEtu = new HashSet<Etudiant>();
   // setEtu = gestionForm.listeEtudiantsOption(ue);
    int nbPersoUeOption = gestionForm.listeEtudiantsOption(ue).size();
    if (gestionForm.listUeOption.contains(ue)) {
      if (this.etu.ueOptionnelle.contains(ue)) {
        return false;
      } else {
        if (nbPersoUeOption < ue.getPlaces()) {
          for (Etudiant e : this.listeEtudiants) {
            if (etu.equals(e)) {
              int nbOpt = e.getNbOption();
              e.ueOptionnelle.add(ue);
              e.setNbOption(nbOpt++);
              this.etu = e;
            }
          }
          return true;
        } else {
          return false;
        }
      }
    } else {
      return false;
    }
  }
  
  @Override
  public Set<UniteEnseignement> enseignementsObligatoires() {
    // TODO Auto-generated method stub
    return gestionForm.listUeObligatoire;
  }
  
  @Override
  public Set<UniteEnseignement> enseignementsOptionnels() {
    // TODO Auto-generated method stub
    return gestionForm.listUeOption;
  }
  
  @Override
  public int getNumeroGroupeTravauxDiriges() throws NonConnecteException {
    for (Etudiant e : this.listeEtudiants) {
      if (this.etu.equals(e)) {
        int nb = e.getNumeroGroupeTravauxDiriges();
        this.etu = e;
        return nb;
      }
    }
    return -1;
  }
  
  @Override
  public int getNumeroGroupeTravauxPratiques() throws NonConnecteException {
    for (Etudiant e : this.listeEtudiants) {
      if (this.etu.equals(e)) {
        int nb = e.getNumeroGroupeTravauxPratiques();
        this.etu = e;
        return nb;
      }
    }
    return -1;
  }
  
  @Override
  public Set<UniteEnseignement> enseignementsSuivis()
      throws NonConnecteException {
    Set<UniteEnseignement> ueSuivis = new HashSet<UniteEnseignement>();
    for (Etudiant e : this.listeEtudiants) {
      if (this.etu.equals(e)) {
        ueSuivis.addAll(e.ueSuivies);
        ueSuivis.addAll(e.ueOptionnelle);
        this.etu = e;
      }
    }
    return ueSuivis;
  }
  
  @Override
  public List<String> listeTousMessages() throws NonConnecteException {
    List<String> msg = new ArrayList<String>();
    for (Etudiant e : this.listeEtudiants) {
      if (etu.equals(e)) {
        msg = e.listMessageLue;
      }
    }
    return msg;
  }
  
  @Override
  public List<String> listeMessageNonLus() throws NonConnecteException {
    List<String> msg = new ArrayList<String>();
    for (Etudiant e : this.listeEtudiants) {
      if (etu.equals(e)) {
        msg = e.listMessageNonLue;
      }
    }
    return msg;
  }
  
  
  @Override
  public boolean inscriptionFinalisee() throws NonConnecteException {
    
    boolean grptd = (this.etu.groupeTd != -1);
    boolean grptp = (this.etu.groupeTp != -1);
    int nbOptions = nombreOptions();
    boolean optionRequise = (this.etu.ueOptionnelle.size() == nbOptions);
    return grptd && grptp && optionRequise;
  }
}
