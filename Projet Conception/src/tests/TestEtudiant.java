package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import formation.Etudiant;
import formation.InformationPersonnelle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Cette classe contient les tests unitaires pour la classe Etudiant. Elle
 * vérifie le bon fonctionnement des différentes méthodes de la classe Etudiant.
 */
public class TestEtudiant {
  
  private Etudiant etudiant;
  private Etudiant etudiant2;
  private Etudiant etudiant3;
  
  /**
   * Méthode exécutée avant chaque test pour initialiser les objets.
   */
  @BeforeEach
  public void setUp() {
    InformationPersonnelle infoPersonnelle =
        new InformationPersonnelle("Skywalker", "Luke", "Planète Tatooine", 20);
    etudiant = new Etudiant(infoPersonnelle, "motDePasse");
    etudiant2 = new Etudiant(infoPersonnelle, "motDePasses");
    etudiant3 = etudiant;
  }
  
  /**
   * Teste la méthode getNbOption.
   */
  @Test
  public void testGetNbOption() {
    assertEquals(0, etudiant.getNbOption());
  }
  
  /**
   * Teste la méthode setNbOption.
   */
  @Test
  public void testSetNbOption() {
    etudiant.setNbOption(2);
    assertEquals(2, etudiant.getNbOption());
  }
  
  /**
   * Teste la méthode setMotDePasse.
   */
  @Test
  public void testSetMotDePasse() {
    etudiant.setMotDePasse("nouveauMotDePasse");
    assertTrue(etudiant.getMotDePasse().equals("nouveauMotDePasse"));
    
    // Ajoute un autre test pour vérifier qu'il n'est pas égal à l'ancien mot de
    // passe
    assertFalse(etudiant.getMotDePasse().equals("motDePasse"));
  }
  
  /**
   * Teste la méthode equals.
   */
  @Test
  public void testEquals() {
    Etudiant etudiantIdentique = etudiant;
    assertTrue(etudiant.equals(etudiantIdentique));
  }
  
  /**
   * Teste la méthode hashCode.
   */
  @Test
  public void testHashCode() {
    assertEquals(etudiant.hashCode(), etudiant3.hashCode());
    assertNotEquals(etudiant.hashCode(), etudiant2.hashCode());
  }
  
  /**
   * Teste la méthode getIdEtu.
   */
  @Test
  public void testGetIdEtu() {
    assertEquals(3, etudiant.getIdEtu());
  }
  
  /**
   * Teste la méthode setNumeroGroupeTravauxDiriges.
   */
  @Test
  public void testSetNumeroGroupeTravauxDiriges() {
    etudiant.setNumeroGroupeTravauxDiriges(2);
    assertEquals(2, etudiant.getNumeroGroupeTravauxDiriges());
    
    etudiant.setNumeroGroupeTravauxDiriges(-1);
    assertEquals(0, etudiant.getNumeroGroupeTravauxDiriges());
  }
  
  /**
   * Teste la méthode setNumeroGroupeTravauxPratiques.
   */
  @Test
  public void testSetNumeroGroupeTravauxPratiques() {
    etudiant.setNumeroGroupeTravauxPratiques(3);
    assertEquals(3, etudiant.getNumeroGroupeTravauxPratiques());
    
    etudiant.setNumeroGroupeTravauxPratiques(-2);
    assertEquals(0, etudiant.getNumeroGroupeTravauxPratiques());
  }
  
  /**
   * Teste la méthode ajouterMessage en vérifiant si un message est correctement
   * ajouté à la liste des messages lus et non lus de l'étudiant.
   */
  @Test
  void testAjouterMessagelu() {
    String message = "Test message";
    etudiant.ajouterMessage(message);
    assertEquals(1, etudiant.listMessageLue.size());
    assertEquals(1, etudiant.listMessageNonLue.size());
  }
  
  /**
   * Teste la méthode messagelu en vérifiant si un message est correctement
   * marqué comme lu dans la liste des messages non lus de l'étudiant.
   */
  @Test
  void testMessagelu() {
    String message = "Test message";
    etudiant.ajouterMessage(message);
    etudiant.messagelu(message);
    assertFalse(etudiant.listMessageNonLue.contains(message));
  }
  
}


