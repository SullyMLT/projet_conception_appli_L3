package tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import formation.Etudiant;
import formation.GestionFormation;
import formation.InformationPersonnelle;
import formation.NonConnecteException;
import formation.UniteEnseignement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Classe de tests pour la classe GestionFormation.
 */
class TestGestionFormation2 {
  
  
  private GestionFormation testGestion;
  private Etudiant etu1;
  private InformationPersonnelle info1;
  private Etudiant etu2;
  private InformationPersonnelle info2;
  private Etudiant etu3;
  private InformationPersonnelle info3;
  private Etudiant etu4;
  private InformationPersonnelle info4;
  private UniteEnseignement option1;
  private UniteEnseignement option2;
  private UniteEnseignement option3;
  
  /**
   * Méthode exécutée avant chaque test pour initialiser les objets nécessaires.
   *
   * @throws Exception en cas d'erreur lors de l'initialisation
   */
  @BeforeEach
  void setUp() throws Exception {
    testGestion = new GestionFormation();
    testGestion.creerFormation("nomFormation", "nomResponsable", "email");
    info1 = new InformationPersonnelle("Skywalker1", "Luke1",
        "Plan�te Tatooine", 20);
    etu1 = new Etudiant(info1, "motDePasse");
    info2 = new InformationPersonnelle("Skywalker2", "Luke2",
        "Plan�te Tatooine", 20);
    etu2 = new Etudiant(info2, "motDePasse");
    info3 = new InformationPersonnelle("Skywalker3", "Luke3",
        "Plan�te Tatooine", 20);
    etu3 = new Etudiant(info3, "motDePasse");
    testGestion.gestionEtu.etu = etu2;
    option1 = new UniteEnseignement("OCR",
        "Luke Skywalker "/* ,"lukeskywalker@univ-brest.fr" */);
    option2 = new UniteEnseignement("Administration",
        "Luke Skywalker "/* ,"lukeskywalker@univ-brest.fr" */);
    option3 = new UniteEnseignement("JAVA",
        "Luke Skywalker "/* ,"lukeskywalker@univ-brest.fr" */);
    option1.setPlaces(2);
    option2.setPlaces(2);
    option3.setPlaces(2);
    
    info4 = new InformationPersonnelle("Skywalker4", "Luke4",
        "Planète Tatooine", 20);
    etu4 = new Etudiant(info4, "motDePasse");
  }
  
  /**
   * Vérifie si le nom du responsable de formation n'est pas null.
   */
  @Test
  void testAdressemailNonNull() {
    assertTrue(testGestion.getNomResponsableFormation() != null);
  }
  
  /**
   * Vérifie si le nom de la formation n'est pas null.
   */
  @Test
  void testnomUeNonNull() {
    assertTrue(testGestion.getNomFormation() != null);
  }
  
  /**
   * Vérifie si l'adresse email du responsable de formation n'est pas null.
   */
  @Test
  void testNomRespNonNull() {
    assertTrue(testGestion.getEmailResponsableFormation() != null);
  }
  
  /**
   * Vérifie si la taille du groupe dirigé est correctement définie.
   */
  @Test
  void testtaillegroupetd() {
    testGestion.setTailleGroupeDirige(25);
    assertEquals(testGestion.getTailleGroupeDirige(), 25);
  }
  
  
  /**
   * Vérifie si la taille du groupe dirigé ne peut pas être définie avec une
   * valeur négative.
   */
  @Test
  void testtaillegroupetdneg() {
    testGestion.setTailleGroupeDirige(-20);
    assertTrue(testGestion.getTailleGroupeDirige() != -20);
  }
  
  /**
   * Vérifie si la taille du groupe pratique est correctement définie.
   */
  void testtaillegroupetp() {
    testGestion.setTailleGroupePratique(25);
    assertEquals(testGestion.getTailleGroupePratique(), 25);
  }
  
  /**
   * Vérifie si la taille du groupe pratique ne peut pas être définie avec une
   * valeur négative.
   */
  @Test
  void testtaillegroupetpneg() {
    testGestion.setTailleGroupePratique(-20);
    assertTrue(testGestion.getTailleGroupePratique() != -20);
  }
  
  /**
   * Vérifie si le nombre de groupes de travaux dirigés est correctement défini.
   */
  @Test
  public void testSetNombreGroupesTravauxDiriges() {
    testGestion.setNombreGroupesTravauxDiriges(2);
    assertEquals(2, testGestion.nombreGroupesTravauxDiriges());
  }
  
  
  /**
   * Vérifie si le nombre de groupes de travaux pratiques est correctement
   * défini.
   */
  @Test
  public void testSetNombreGroupesTravauxPratiques() {
    testGestion.setNombreGroupesTravauxPratiques(3);
    assertEquals(3, testGestion.nombreGroupesTravauxPratiques());
  }
  
  /**
   * Vérifie si le nombre d'options est correctement défini.
   */
  @Test
  public void testDefinirNombreOptions() {
    testGestion.definirNombreOptions(3);
    assertEquals(3, testGestion.getNbOption());
  }
  
  /**
   * Vérifie si un enseignement obligatoire peut être ajouté avec succès.
   */
  @Test
  public void testAjouterEnseignementObligatoire() {
    UniteEnseignement ue1 = new UniteEnseignement("UE1", "Description UE1");
    UniteEnseignement ue2 = new UniteEnseignement("UE2", "Description UE2");
    
    assertTrue(testGestion.ajouterEnseignementObligatoire(ue1));
    assertFalse(testGestion.ajouterEnseignementObligatoire(ue1));
    assertTrue(testGestion.ajouterEnseignementObligatoire(ue2));
  }
  
  /**
   * Vérifie si un enseignement optionnel peut être ajouté avec succès.
   */
  
  @Test
  public void testAjouterEnseignementOptionnel() {
    UniteEnseignement ue1 = new UniteEnseignement("UE1", "Description UE1");
    UniteEnseignement ue2 = new UniteEnseignement("UE2", "Description UE2");
    
    assertTrue(testGestion.ajouterEnseignementOptionnel(ue1, 20));
    assertFalse(testGestion.ajouterEnseignementOptionnel(ue1, 10));
    assertTrue(testGestion.ajouterEnseignementOptionnel(ue2, 15));
  }
  
  /*
   * @Test public void testEquilibreGroupeTd() throws NonConnecteException {
   * testGestion.gestionEtu.listeEtudiants.add(etu1);
   * testGestion.gestionEtu.listeEtudiants.add(etu2);
   * testGestion.gestionEtu.listeEtudiants.add(etu3);
   * 
   * 
   * testGestion.setTailleGroupeDirige(2);
   * testGestion.setNombreGroupesTravauxDiriges(3);
   * testGestion.setTailleGroupePratique(2);
   * testGestion.setNombreGroupesTravauxPratiques(3);
   * testGestion.attribuerAutomatiquementGroupes();
   * testGestion.equilibreGroupeTd(); testGestion.gestionEtu.etu = etu1;
   * assertEquals(1, testGestion.gestionEtu.getNumeroGroupeTravauxDiriges());
   * testGestion.gestionEtu.etu = etu2; assertEquals(2,
   * testGestion.gestionEtu.getNumeroGroupeTravauxDiriges());
   * testGestion.gestionEtu.etu = etu3; assertEquals(2,
   * testGestion.gestionEtu.getNumeroGroupeTravauxDiriges()); }
   */
  /**
   * Vérifie l'attribution et la récupération du nombre de personnes pour les
   * travaux dirigés et pratiques.
   */
  
  @Test
  public void testattributionetgetnombre() throws NonConnecteException {
    testGestion.gestionEtu.listeEtudiants.add(etu1);
    testGestion.gestionEtu.listeEtudiants.add(etu2);
    testGestion.gestionEtu.listeEtudiants.add(etu3);
    testGestion.gestionEtu.listeEtudiants.add(etu4);
    
    
    testGestion.setTailleGroupeDirige(3);
    testGestion.setTailleGroupePratique(3);
    
    testGestion.attribuerAutomatiquementGroupes();
    
    assertEquals(2, testGestion.getNombrePersonneTravauxDiriges(1));
    assertEquals(2, testGestion.getNombrePersonneTravauxDiriges(2));
    
    assertEquals(2, testGestion.getNombrePersonneTravauxPratiques(1));
    assertEquals(2, testGestion.getNombrePersonneTravauxPratiques(2));
  }
  
  /*
   * @Test public void testEquilibreGroupeTp() throws NonConnecteException {
   * testGestion.gestionEtu.listeEtudiants.add(etu1);
   * testGestion.gestionEtu.listeEtudiants.add(etu2);
   * testGestion.gestionEtu.listeEtudiants.add(etu3);
   * 
   * 
   * testGestion.setTailleGroupeDirige(2);
   * testGestion.setNombreGroupesTravauxDiriges(3);
   * testGestion.setTailleGroupePratique(2);
   * testGestion.setNombreGroupesTravauxPratiques(3);
   * testGestion.attribuerAutomatiquementGroupes();
   * testGestion.equilibreGroupeTd(); testGestion.gestionEtu.etu = etu1;
   * assertEquals(2, testGestion.gestionEtu.getNumeroGroupeTravauxPratiques());
   * testGestion.gestionEtu.etu = etu2; assertEquals(1,
   * testGestion.gestionEtu.getNumeroGroupeTravauxPratiques());
   * testGestion.gestionEtu.etu = etu3; assertEquals(2,
   * testGestion.gestionEtu.getNumeroGroupeTravauxPratiques());
   * 
   * }
   */
  
  /**
   * Vérifie le changement de groupe pour les travaux dirigés et pratiques.
   */
  @Test
  void testChangerGroupe() throws NonConnecteException {
    testGestion.gestionEtu.listeEtudiants.add(etu1);
    testGestion.gestionEtu.listeEtudiants.add(etu2);
    testGestion.setTailleGroupeDirige(2);
    testGestion.setNombreGroupesTravauxDiriges(3);
    testGestion.setTailleGroupePratique(2);
    testGestion.setNombreGroupesTravauxPratiques(3);
    
    etu1.setNumeroGroupeTravauxDiriges(1);
    etu1.setNumeroGroupeTravauxPratiques(2);
    
    etu2.setNumeroGroupeTravauxDiriges(3);
    etu2.setNumeroGroupeTravauxPratiques(2);
    
    assertEquals(0, testGestion.changerGroupe(etu1, 3, 1));
    assertEquals(0, testGestion.changerGroupe(etu2, 2, 3));
    assertEquals(-3, testGestion.changerGroupe(etu2, 0, 5));
  }
  
  /**
   * Vérifie la liste des étudiants dans un groupe de travaux dirigés.
   */
  @Test
  void testListeEtudiantsGroupeDirige() {
    testGestion.gestionEtu.listeEtudiants.add(etu1);
    testGestion.gestionEtu.listeEtudiants.add(etu2);
    testGestion.gestionEtu.listeEtudiants.add(etu3);
    
    testGestion.setTailleGroupeDirige(2);
    testGestion.setNombreGroupesTravauxDiriges(2);
    testGestion.setTailleGroupePratique(2);
    testGestion.setNombreGroupesTravauxPratiques(2);
    testGestion.attribuerAutomatiquementGroupes();
    
    assertEquals(2, testGestion.listeEtudiantsGroupeDirige(1).size());
    assertEquals(1, testGestion.listeEtudiantsGroupeDirige(2).size());
    assertNull(testGestion.listeEtudiantsGroupeDirige(3));
  }
  
  
  /**
   * Vérifie la liste des étudiants dans un groupe de travaux pratiques.
   */
  @Test
  void testListeEtudiantsGroupePratiques() {
    testGestion.gestionEtu.listeEtudiants.add(etu1);
    testGestion.gestionEtu.listeEtudiants.add(etu2);
    testGestion.gestionEtu.listeEtudiants.add(etu3);
    
    testGestion.setTailleGroupeDirige(2);
    testGestion.setNombreGroupesTravauxDiriges(2);
    testGestion.setTailleGroupePratique(2);
    testGestion.setNombreGroupesTravauxPratiques(2);
    testGestion.attribuerAutomatiquementGroupes();
    
    assertEquals(2, testGestion.listeEtudiantsGroupePratique(1).size());
    assertEquals(1, testGestion.listeEtudiantsGroupePratique(2).size());
    assertNull(testGestion.listeEtudiantsGroupePratique(3));
  }
  
  /**
   * Vérifie la liste des étudiants dans une option.
   */
  @Test
  void testListeEtudiantsOption() throws NonConnecteException {
    testGestion.gestionEtu.listeEtudiants.add(etu1);
    testGestion.gestionEtu.listeEtudiants.add(etu2);
    testGestion.gestionEtu.listeEtudiants.add(etu3);
    testGestion.definirNombreOptions(2);
    testGestion.ajouterEnseignementOptionnel(option1, 2);
    testGestion.ajouterEnseignementOptionnel(option2, 2);
    testGestion.ajouterEnseignementOptionnel(option3, 2);
    
    testGestion.gestionEtu.etu = etu3;
    testGestion.gestionEtu.choisirOption(option1);
    testGestion.gestionEtu.choisirOption(option2);
    testGestion.gestionEtu.etu = etu1;
    testGestion.gestionEtu.choisirOption(option3);
    
    assertEquals(1, testGestion.listeEtudiantsOption(option1).size());
    assertEquals(1, testGestion.listeEtudiantsOption(option2).size());
  }
  
  /**
   * Vérifie la création d'une formation.
   */
  @Test
  public void testCreerFormation() {
    testGestion.creerFormation("nomFormation", "nomResponsable",
        "emailResponsable");
    assertEquals("nomFormation", testGestion.getNomFormation());
    assertEquals("nomResponsable", testGestion.getNomResponsableFormation());
    assertEquals("emailResponsable",
        testGestion.getEmailResponsableFormation());
  }
  
}


