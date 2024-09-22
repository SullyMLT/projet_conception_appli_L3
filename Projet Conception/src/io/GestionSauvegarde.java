package io;

import formation.GestionFormation;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * La classe GestionSauvegarde implémente l'interface InterSauvegarde pour
 * sauvegarder et charger des données de GestionFormation. Les données sont
 * sérialisées en utilisant ObjectInputStream et ObjectOutputStream.
 */
public class GestionSauvegarde implements InterSauvegarde {
  
  private GestionFormation form;
  
  public GestionSauvegarde() {
    
  }
  
  /**
   * Constructeur avec paramètre de la classe GestionSauvegarde.
   *
   * @param maFormation La GestionFormation à sauvegarder.
   * @throws IllegalArgumentException Si la GestionFormation est nulle.
   */
  public GestionSauvegarde(GestionFormation maFormation) {
    if (maFormation == null) {
      throw new IllegalArgumentException(
          "La Gestion Formation ne doit pas être vide");
    }
    this.form = maFormation;
  }
  
  @Override
  public void sauvegarderDonnees(String nomFichier) throws IOException {
    try (ObjectOutputStream outputStream =
        new ObjectOutputStream(new FileOutputStream(nomFichier))) {
      // Sauvegarder d'autres données si nécessaire
      outputStream.writeObject(this.form);
    }
  }
  
  @Override
  public void chargerDonnees(String nomFichier) throws IOException {
    try (ObjectInputStream inputStream =
        new ObjectInputStream(new FileInputStream(nomFichier))) {
      try {
        form = (GestionFormation) inputStream.readObject();
      } catch (ClassNotFoundException e) {
        
      }
    }
  }
  
  /**
   * Obtient l'objet GestionFormation actuel.
   *
   * @return L'objet GestionFormation.
   */
  public GestionFormation getGestionFormation() {
    return this.form;
  }
}


