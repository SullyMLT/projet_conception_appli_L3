package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import formation.UniteEnseignement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestUniteEnseignement {
  
  private UniteEnseignement infoBasique;
  private UniteEnseignement ue2;
  private UniteEnseignement ue3;
  
  @BeforeEach
  void setUp() throws Exception {
    infoBasique = new UniteEnseignement("OCR", "Luke Skywalker ");
    ue2 = new UniteEnseignement("ue2", "Skywalker ");
    ue3 = new UniteEnseignement("OCR", "Luke Skywalker ");
  }
  
  @AfterEach
  void tearDown() throws Exception {}
  
  /**
   * Teste la méthode de modification du nombre de places d'une unité
   * d'enseignement.
   */
  @Test
  void testplaces() {
    infoBasique.setPlaces(25);
    assertEquals(infoBasique.getPlaces(), 25);
  }
  
  /**
   * Teste la méthode de modification du nombre de places avec une valeur
   * négative. La valeur ne doit pas être modifiée si elle est négative.
   */
  @Test
  void testPlacesNegatif() {
    infoBasique.setPlaces(-20);
    assertTrue(infoBasique.getPlaces() != -20);
  }
  
  /**
   * Teste si le nom de l'unité d'enseignement est non null.
   */
  @Test
  void testnomUENonNull() {
    assertTrue(infoBasique.getNomUe() != null);
  }
  
  /**
   * Teste la méthode de modification du nom de l'unité d'enseignement avec une
   * valeur null. Le nom ne doit pas être modifié si la valeur est null.
   */
  @Test
  void testSetterNomUENull() {
    infoBasique.setNomUe(null);
    assertTrue(infoBasique.getNomUe() != null);
  }
  
  /**
   * Teste si le nom du responsable de l'unité d'enseignement est non null.
   */
  @Test
  void testNomRespNonNull() {
    assertTrue(infoBasique.getNomResp() != null);
  }
  
  /**
   * Teste la méthode de modification du nom du responsable de l'unité
   * d'enseignement avec une valeur null. Le nom ne doit pas être modifié si la
   * valeur est null.
   */
  @Test
  void testSetterNomrespNonNull() {
    infoBasique.setNomResp(null);
    assertTrue(infoBasique.getNomResp() != null);
  }
  
  /**
   * Teste la méthode d'égalité entre deux unités d'enseignement. Deux unités
   * d'enseignement sont considérées égales si elles ont le même nom.
   */
  @Test
  public void testEquals() {
    assertTrue(infoBasique.equals(ue3));
    assertFalse(infoBasique.equals(ue2));
  }
  
  /**
   * Teste la méthode de calcul du code de hachage d'une unité d'enseignement.
   * Deux unités d'enseignement égales doivent avoir le même code de hachage.
   */
  @Test
  public void testHashCode() {
    assertEquals(infoBasique.hashCode(), ue3.hashCode());
    assertNotEquals(infoBasique.hashCode(), ue2.hashCode());
  }
  
  /**
   * Teste le constructeur de la classe UniteEnseignement. Vérifie si le nom de
   * l'unité d'enseignement et le nom du responsable sont correctement
   * initialisés.
   */
  @Test
  void testConstructeur() {
    UniteEnseignement inf = new UniteEnseignement("Vador", "Dark");
    assertEquals(inf.getNomUe(), "Vador");
    assertEquals(inf.getNomResp(), "Dark");
    assertTrue(inf.getPlaces() >= 0);
  }
  
}
