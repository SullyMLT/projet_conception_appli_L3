package formation;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * Gestion des services liés à une année de formation. Implémentation de
 * l'interface InterGestionFormation. Cette classe est sérialisable.
 * 
 * @author Sully Millet
 *
 */
public class GestionFormation implements InterGestionFormation, Serializable {
  
  private static final long serialVersionUID = -5215969600110382415L;
  /* Attribut */
  private String nomFormation;
  public GestionEtudiant gestionEtu = new GestionEtudiant();
  
  private String nomRespForm;
  private String emailRespForm;
  
  private int tailleGroupetd = 0;
  private int tailleGroupetp = 0;
  
  private int nbOption = -1;
  private int nbGroupeTd = 0;
  private int nbGroupeTp = 0;
  
  public Set<UniteEnseignement> listUeObligatoire =
      new HashSet<UniteEnseignement>();
  public Set<UniteEnseignement> listUeOption = new HashSet<UniteEnseignement>();
  
  /* ____Methodes____ */
  
  @Override
  public void creerFormation(String nomFormation, String nomResponsable,
      String email) {
    if (nomFormation != "") {
      this.nomFormation = nomFormation;
    }
    if (nomResponsable != "") {
      this.nomRespForm = nomResponsable;
    }
    if (email != null) {
      this.emailRespForm = email;
    }
    gestionEtu.gestionForm = new GestionFormation();
    gestionEtu.gestionForm = this;
  }
  
  @Override
  public String getNomResponsableFormation() {
    return this.nomRespForm;
  }
  
  @Override
  public String getEmailResponsableFormation() {
    return this.emailRespForm;
  }
  
  @Override
  public String getNomFormation() {
    return nomFormation;
  }
  
  /* ajout d'une UE obligatoire dans la liste des ue Obligatoire */
  @Override
  public boolean ajouterEnseignementObligatoire(UniteEnseignement ue) {
    if (ue == null) {
      return false;
    }
    for (UniteEnseignement present : this.listUeObligatoire) {
      if (present.equals(ue)) {
        return false;
      }
    }
    for (UniteEnseignement present : this.listUeOption) {
      if (present.equals(ue)) {
        return false;
      }
    }
    listUeObligatoire.add(ue);
    return true;
  }
  
  /* ajout d'une ue Optionnel dans la liste des UE Optinnel */
  @Override
  public boolean ajouterEnseignementOptionnel(UniteEnseignement ue,
      int nbPlaces) {
    if (ue == null) {
      return false;
    }
    
    for (UniteEnseignement present : this.listUeObligatoire) {
      if (present.equals(ue)) {
        return false;
      }
    }
    for (UniteEnseignement present : this.listUeOption) {
      if (present.equals(ue)) {
        return false;
      }
    }
    ue.setPlaces(nbPlaces);
    this.listUeOption.add(ue);
    return true;
  }
  
  @Override
  public void setTailleGroupeDirige(int taille) {
    if (taille > 1) {
      this.tailleGroupetd = taille;
    }
  }
  
  @Override
  public void setTailleGroupePratique(int taille) {
    if (taille > 1) {
      this.tailleGroupetp = taille;
    }
  }
  
  
  @Override
  public int getTailleGroupeDirige() {
    if (this.tailleGroupetd != 0) {
      return this.tailleGroupetd;
    } else {
      return -1;
    }
    
  }
  
  @Override
  public int getTailleGroupePratique() {
    if (this.tailleGroupetp != 0) {
      return this.tailleGroupetp;
    } else {
      return -1;
    }
  }
  
  @Override
  public void definirNombreOptions(int nombre) {
    if (nombre >= 1) {
      this.nbOption = nombre;
    }
  }
  
  public int getNbOption() {
    return nbOption;
  }
  
  @Override
  public void attribuerAutomatiquementGroupes() {
    // TODO Auto-generated method stub
    
    int nbEtu = gestionEtu.listeEtudiants.size();
    
    double realBesoinGroupeTd = (double) nbEtu / this.getTailleGroupeDirige();
    int entierBesoinGroupeTd = nbEtu / this.getTailleGroupeDirige();
    if (realBesoinGroupeTd > entierBesoinGroupeTd) {
      entierBesoinGroupeTd++;
    }
    
    double realBesoinGroupeTp = (double) nbEtu / this.getTailleGroupePratique();
    int entierBesoinGroupeTp = nbEtu / this.getTailleGroupePratique();
    if (realBesoinGroupeTp > entierBesoinGroupeTp) {
      entierBesoinGroupeTp++;
    }
    
    this.setNombreGroupesTravauxDiriges(entierBesoinGroupeTd);
    this.setNombreGroupesTravauxPratiques(entierBesoinGroupeTp);
    
    
    equilibreGroupeTd();
    equilibreGroupeTp();
  }
  
  /**
   * Equilibre le nombre de personne dans les groupes travaux dirigés par
   * rapport au nombre de groupes de travaux dirigés qui est utilisé dans la
   * méthode attribuerAutomatiquementGroupes().
   */
  public void equilibreGroupeTd() {
    
    int nbGroupTd = nombreGroupesTravauxDiriges();
    int curGroupe = 1;
    
    
    for (Etudiant e : gestionEtu.listeEtudiants) {
      if (curGroupe > nbGroupTd) {
        curGroupe = 1;
      }
      e.setNumeroGroupeTravauxDiriges(curGroupe);
      curGroupe++;
    }
  }
  
  /**
   * Equilibre le nombre de personne dans les groupes de travaux pratiques par
   * rapport au nombre de groupes de travaux pratiques qui est utilisé dans la
   * méthode attribuerAutomatiquementGroupes().
   */
  public void equilibreGroupeTp() {
    
    int nbGroupTp = nombreGroupesTravauxPratiques();
    int curGroupe = 1;
    
    
    for (Etudiant e : gestionEtu.listeEtudiants) {
      if (curGroupe > nbGroupTp) {
        curGroupe = 1;
      }
      e.setNumeroGroupeTravauxPratiques(curGroupe);
      curGroupe++;
    }
  }
  
  @Override
  public int changerGroupe(Etudiant etudiant, int groupeDirige,
      int groupePratique) {
    
    boolean changertp = false;
    boolean changertd = false;
    
    for (Etudiant e : this.gestionEtu.listeEtudiants) {
      if (etudiant.equals(e)) {
        if (groupeDirige != 0
            && groupeDirige <= nombreGroupesTravauxDiriges()) {
          if (getNombrePersonneTravauxDiriges(
              groupeDirige) < getTailleGroupeDirige()) {
            
            etudiant.setNumeroGroupeTravauxDiriges(groupeDirige);
            changertd = true;
          }
        }
        if (groupePratique != 0
            && groupePratique <= nombreGroupesTravauxPratiques()) {
          if (getNombrePersonneTravauxPratiques(
              groupePratique) < getTailleGroupePratique()) {
            
            etudiant.setNumeroGroupeTravauxPratiques(groupePratique);
            changertp = true;
          }
        }
      }
    }
    if (changertd == false && changertp == false) {
      return -3;
    } else if (changertd == false) {
      return -1;
    } else if (changertp == false) {
      return -2;
    }
    return 0;
  }
  
  /**
   * Renvoie le nombre de personne dans un groupe de td renseign� en param�tre.
   *
   * @param groupeTd compte le nombre de personne �tant dans ce groupe de td
   * @return Le nombre d'�tudiant dans un groupe de td
   */
  public int getNombrePersonneTravauxDiriges(int groupeTd) {
    int cpt = 0;
    
    for (Etudiant e : gestionEtu.listeEtudiants) {
      if (e.groupeTd == groupeTd) {
        cpt = cpt + 1;
      }
    }
    return cpt;
  }
  
  /**
   * Renvoie le nombre de personne dans un groupe de tp renseign� en param�tre.
   *
   * @param groupeTp compte le nombre de personne �tant dans ce groupe de tp
   * @return Le nombre d'�tudiant dans un groupe de tp
   */
  public int getNombrePersonneTravauxPratiques(int groupeTp) {
    int cpt = 0;
    
    for (Etudiant e : gestionEtu.listeEtudiants) {
      if (e.groupeTp == groupeTp) {
        cpt = cpt + 1;
      }
    }
    return cpt;
  }
  
  @Override
  public int nombreGroupesTravauxDiriges() {
    // TODO Auto-generated method stub
    return this.nbGroupeTd;
  }
  
  /**
   * Défini le nombre de groupe de travaux dirigé renseigné en paramètre.
   *
   * @param nb est la valeur en entier pour définir le nombre de groupe de
   *        travaux dirigés
   */
  public void setNombreGroupesTravauxDiriges(int nb) {
    this.nbGroupeTd = nb;
  }
  
  @Override
  public int nombreGroupesTravauxPratiques() {
    // TODO Auto-generated method stub
    return this.nbGroupeTp;
  }
  
  /**
   * Défini le nombre de groupe de travaux pratiques renseigné en paramètre.
   *
   * @param nb est la valeur en entier pour définir le nombre de groupe de
   *        travaux dirigés
   */
  public void setNombreGroupesTravauxPratiques(int nb) {
    this.nbGroupeTp = nb;
  }
  
  
  @Override
  public Set<Etudiant> listeEtudiantsGroupeDirige(int groupeTd) {
    // TODO Auto-generated method stub
    Set<Etudiant> newGroupeTd = new HashSet<>();
    
    for (Etudiant e : gestionEtu.listeEtudiants) {
      if (e.groupeTd == groupeTd) {
        newGroupeTd.add(e);
      }
    }
    
    if (newGroupeTd.isEmpty()) {
      return null;
    } else {
      return newGroupeTd;
    }
    
  }
  
  @Override
  public Set<Etudiant> listeEtudiantsGroupePratique(int groupeTp) {
    // TODO Auto-generated method stub
    Set<Etudiant> newGroupeTp = new HashSet<>();
    
    for (Etudiant e : gestionEtu.listeEtudiants) {
      if (e.groupeTp == groupeTp) {
        newGroupeTp.add(e);
      }
    }
    
    if (newGroupeTp.isEmpty()) {
      return null;
    } else {
      return newGroupeTp;
    }
    
  }
  
  
  @Override
  public Set<Etudiant> listeEtudiantsOption(UniteEnseignement option) {
    // TODO Auto-generated method stub
    boolean estPresent;
    boolean ajouter = false;
    Set<Etudiant> listeEtuUeOption = new HashSet<>();
    
    estPresent = this.listUeOption.contains(option);
    if (estPresent) {
      for (Etudiant e : gestionEtu.listeEtudiants) {
        ajouter = false;
        Set<UniteEnseignement> ueOption = new HashSet<>();
        ueOption.addAll(e.ueSuivies);
        ueOption.addAll(e.ueOptionnelle);
        for (UniteEnseignement ue : ueOption) {
          if (ajouter == false) {
            if (option.equals(ue)) {
              ajouter = listeEtuUeOption.add(e);
            }
          }
        }
      }
      return listeEtuUeOption;
    } else {
      return null;
    }
  }
}
