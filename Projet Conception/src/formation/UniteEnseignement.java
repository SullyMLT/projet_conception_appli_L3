package formation;

import java.io.Serializable;
import java.util.Objects;

/**
 * Cette classe représente une unité d'enseignement.
 */
public class UniteEnseignement implements Serializable {
  
  private static final long serialVersionUID = -1671756719863869255L;
  /* _______Attribut_______ */
  private String nomUe;
  private String nomResp;
  private int places;
  
  /**
   * Constructeur de la classe UniteEnseignement.
   * 
   * @param nomUe Le nom de l'unité d'enseignement.
   * @param nomResp Le nom du responsable de l'unité d'enseignement.
   */
  public UniteEnseignement(String nomUe, String nomResp) {
    super();
    this.nomUe = nomUe;
    this.nomResp = nomResp;
    this.places = 0;
  }
  
  /* _______Méthode_______ */
  
  /**
   * Retourne le nom du responsable de l'unité d'enseignement.
   * 
   * @return Le nom du responsable.
   */
  public String getNomResp() {
    return nomResp;
  }
  
  /**
   * Modifie le nom du responsable de l'unité d'enseignement.
   * 
   * @param nomResp Le nouveau nom du responsable.
   */
  public void setNomResp(String nomResp) {
    if (nomResp != null) {
      this.nomResp = nomResp;
    }
  }
  
  /**
   * Retourne le nombre de places disponibles pour l'unité d'enseignement.
   * 
   * @return Le nombre de places disponibles.
   */
  public int getPlaces() {
    return places;
  }
  
  /**
   * Modifie le nombre de places disponibles pour l'unité d'enseignement.
   * 
   * @param places Le nouveau nombre de places disponibles.
   */
  public void setPlaces(int places) {
    if (places >= 0) {
      this.places = places;
    }
  }
  
  /**
   * Retourne le nom de l'unité d'enseignement.
   * 
   * @return Le nom de l'unité d'enseignement.
   */
  public String getNomUe() {
    return nomUe;
  }
  
  /**
   * Modifie le nom de l'unité d'enseignement.
   * 
   * @param nomUe Le nouveau nom de l'unité d'enseignement.
   */
  public void setNomUe(String nomUe) {
    if (nomUe != null) {
      this.nomUe = nomUe;
    }
  }
  
  /**
   * Retourne le code de hachage de l'objet.
   * 
   * @return Le code de hachage.
   */
  @Override
  public int hashCode() {
    return Objects.hash(nomResp, nomUe, places);
  }
  
  /**
   * Compare l'objet courant avec un autre objet pour l'égalité.
   * 
   * @param obj L'objet à comparer.
   * @return true si les objets sont égaux, false sinon.
   */
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
    UniteEnseignement other = (UniteEnseignement) obj;
    return Objects.equals(nomResp, other.nomResp)
        && Objects.equals(nomUe, other.nomUe) && places == other.places;
  }
  
  /**
   * Retourne une représentation sous forme de chaîne de caractères de l'objet.
   * 
   * @return Une représentation sous forme de chaîne de caractères.
   */
  @Override
  public String toString() {
    return "UE  " + nomUe;
  }
}
