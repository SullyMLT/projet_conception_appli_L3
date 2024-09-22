package formation;


import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * Représente un étudiant dans le système de gestion de formation. La classe
 * implémente l'interface Serializable pour permettre la sérialisation. Chaque
 * étudiant est identifié par un identifiant unique (IDETU). Les attributs
 * comprennent des informations personnelles, des unités d'enseignement suivies,
 * des groupes de travaux dirigés (TD) et de travaux pratiques (TP), un mot de
 * passe, le nombre d'options, et des listes de messages lus et non lus.
 * 
 * @author Liam Frit, Sully Millet, Andrea Le dortz
 *
 */
public class Etudiant implements Serializable {
  
  private static final long serialVersionUID = 5558681023426658278L;
  
  /* ____Attributs_____ */
  public InformationPersonnelle etu;
  public Set<UniteEnseignement> ueSuivies = new HashSet<UniteEnseignement>();
  public Set<UniteEnseignement> ueOptionnelle =
      new HashSet<UniteEnseignement>();
  
  public List<String> listMessageNonLue = new LinkedList<String>();
  public List<String> listMessageLue = new LinkedList<String>();
  
  static int id = 1;
  private final int IDETU;
  
  protected int groupeTp = 0;
  protected int groupeTd = 0;
  protected String motDePasse;
  protected int nbOption = 0;
  
  /**
   * Constructeur de la classe Etudiant.
   * 
   * @param etu Informations personnelles de l'étudiant.
   * @param motDePasse Mot de passe de l'étudiant.
   */
  public Etudiant(InformationPersonnelle etu, String motDePasse) {
    super();
    this.etu = etu;
    this.IDETU = id;
    id++;
    this.motDePasse = motDePasse;
  }
  
  /* _____M�thodes_____ */
  
  /**
   * Renvoie le nombre d'options de l'étudiant.
   * 
   * @return Le nombre d'options de l'étudiant.
   */
  public int getNbOption() {
    return nbOption;
  }
  
  /**
   * Définit le nombre d'options de l'étudiant.
   * 
   * @param nbO Le nouveau nombre d'options.
   */
  public void setNbOption(int nbO) {
    this.nbOption = nbO;
  }
  
  /**
   * Renvoie le mot de passe de l'étudiant.
   * 
   * @return Le mot de passe de l'étudiant.
   */
  public String getMotDePasse() {
    return motDePasse;
  }
  
  /**
   * Définit le mot de passe de l'étudiant.
   * 
   * @param motDePasse Le nouveau mot de passe.
   */
  public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
  }
  
  /**
   * Renvoie l'identifiant unique de l'étudiant.
   * 
   * @return L'identifiant unique de l'étudiant.
   */
  public int getIdEtu() {
    return IDETU;
  }
  
  /**
   * Définit le numéro du groupe de travaux dirigés de l'étudiant.
   * 
   * @param nouveauGroupetd Le nouveau numéro de groupe de travaux dirigés.
   */
  public void setNumeroGroupeTravauxDiriges(int nouveauGroupetd) {
    if (nouveauGroupetd < 0) {
      nouveauGroupetd = 0;
    }
    this.groupeTd = nouveauGroupetd;
  }
  
  /**
   * Définit le numéro du groupe de travaux pratiques de l'étudiant.
   * 
   * @param nouveauGroupetp Le nouveau numéro de groupe de travaux pratiques.
   */
  public void setNumeroGroupeTravauxPratiques(int nouveauGroupetp) {
    if (nouveauGroupetp < 0) {
      nouveauGroupetp = 0;
    }
    this.groupeTp = nouveauGroupetp;
  }
  
  /**
   * Renvoie le numéro du groupe de travaux dirigés de l'étudiant.
   * 
   * @return Le numéro du groupe de travaux dirigés de l'étudiant.
   */
  public int getNumeroGroupeTravauxDiriges() {
    return groupeTd;
    
  }
  
  /**
   * Renvoie le numéro du groupe de travaux pratiques de l'étudiant.
   * 
   * @return Le numéro du groupe de travaux pratiques de l'étudiant.
   */
  public int getNumeroGroupeTravauxPratiques() {
    return groupeTp;
    
  }
  
  
  
  @Override
  public int hashCode() {
    return Objects.hash(IDETU, etu, groupeTd, groupeTp, listMessageLue,
        listMessageNonLue, motDePasse, nbOption, ueOptionnelle, ueSuivies);
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Etudiant other = (Etudiant) obj;
    return IDETU == other.IDETU && Objects.equals(etu, other.etu)
        && groupeTd == other.groupeTd && groupeTp == other.groupeTp
        && Objects.equals(listMessageLue, other.listMessageLue)
        && Objects.equals(listMessageNonLue, other.listMessageNonLue)
        && Objects.equals(motDePasse, other.motDePasse)
        && nbOption == other.nbOption
        && Objects.equals(ueOptionnelle, other.ueOptionnelle)
        && Objects.equals(ueSuivies, other.ueSuivies);
  }
  
  @Override
  public String toString() {
    return "ID - " + this.getIdEtu() + " | Prenom : " + this.etu.getPrenom()
        + " | Nom : " + this.etu.getNom();
  }
  
  /**
   * Ajoute le message dans la liste des messages lus et non lus.
   * 
   * @param message Chaine de caractère du message à ajouter.
   */
  public void ajouterMessage(String message) {
    listMessageLue.add(message);
    listMessageNonLue.add(message);
    
    
  }
  
  /**
   * Supprime le message rentré en paramètre de la liste des messages non lus.
   * 
   * @param message Chaine de caractère du message à ajouter.
   */
  public void messagelu(String message) {
    listMessageNonLue.remove(message);
  }
  
  
}
