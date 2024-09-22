package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import formation.Etudiant;
import formation.GestionEtudiant;
import formation.GestionFormation;
import formation.InformationPersonnelle;
import formation.NonConnecteException;
import formation.UniteEnseignement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Cette classe contient les tests unitaires pour la classe GestionEtudiant.
 * Elle vérifie le bon fonctionnement des différentes méthodes de la classe
 * GestionEtudiant.
 */
class TestGestionEtudiant {
  
  
  private Etudiant test;
  private InformationPersonnelle info;
  private GestionEtudiant tests;
  private int nb;
  
  /**
   * Méthode exécutée avant chaque test pour initialiser les objets.
   * 
   * @throws Exception si une exception se produit lors de la configuration.
   * 
   */
  @BeforeEach
  void setUp() throws Exception {
    info =
        new InformationPersonnelle("Skywalker", "Luke", "Plan�te Tatooine", 20);
    test = new Etudiant(info, "motDePasse");
    tests = new GestionEtudiant();
    tests.listeEtudiants.add(test);
    tests.etu = test;
    tests.gestionForm = new GestionFormation();
    tests.gestionForm.definirNombreOptions(2);
    nb = tests.inscription(info, "motDePasse");
    
    
  }
  
  /**
   * Teste la méthode d'inscription d'un nouvel étudiant.
   */
  @Test
  public void testInscription() {
    InformationPersonnelle infoPersonnelle =
        new InformationPersonnelle("test", "test", " Tatooine", 22);
    int idEtu = tests.inscription(infoPersonnelle, "motDePasse");
    assertTrue(idEtu > 0);
  }
  
  /**
   * Teste la méthode de connexion d'un étudiant.
   */
  @Test
  public void testConnexion() {
    InformationPersonnelle infoPersonnelle = info;
    int idEtu = tests.inscription(infoPersonnelle, "motDePasse");
    assertTrue(tests.connexion(idEtu, "motDePasse"));
    assertFalse(tests.connexion(idEtu, "mauvaisMotDePasse"));
  }
  
  /**
   * Teste la méthode de déconnexion d'un étudiant.
   * 
   * @throws NonConnecteException si la déconnexion est appelée alors que
   *         l'étudiant n'est pas connecté.
   */
  @Test
  public void testDeconnexion() throws NonConnecteException {
    InformationPersonnelle infoPersonnelle = info;
    int idEtu = tests.inscription(infoPersonnelle, "motDePasse");
    tests.connexion(idEtu, "motDePasse");
    tests.deconnexion();
    assertFalse(tests.etudiantConnecte);
    assertNull(tests.etu);
  }
  
  /**
   * V�rifie qu'on ne peut pas positionner un nombre de places n�gatif sur une
   * information basique.
   */
  @Test
  void testGroupetdneg() throws Exception {
    test.setNumeroGroupeTravauxDiriges(-20);
    assertTrue(tests.getNumeroGroupeTravauxDiriges() != -20);
  }
  
  /**
   * Teste la méthode de gestion des groupes de travaux pratiques avec un nombre
   * positif.
   * 
   * @throws Exception si une exception se produit lors de la configuration.
   */
  @Test
  void testGroupetp() throws Exception {
    tests.etu.setNumeroGroupeTravauxPratiques(25);
    assertEquals(tests.getNumeroGroupeTravauxPratiques(), 25);
  }
  
  /**
   * Teste la méthode de gestion des groupes de travaux pratiques avec un nombre
   * positif.
   * 
   * @throws Exception si une exception se produit lors de la configuration.
   */
  @Test
  void testGroupetd() throws Exception {
    tests.etu.setNumeroGroupeTravauxDiriges(20);
    assertEquals(tests.getNumeroGroupeTravauxDiriges(), 20);
  }
  
  /**
   * Teste la méthode de récupération du nombre d'options pour un étudiant non
   * connecté.
   * 
   * @throws NonConnecteException si la méthode est appelée alors que l'étudiant
   *         n'est pas connecté.
   */
  @Test
  void testNombreOptionsEtudiantNonConnecte() throws NonConnecteException {
    assertEquals(-1, tests.nombreOptions());
  }
  
  /**
   * Teste la méthode de récupération du nombre d'options pour un étudiant
   * connecté.
   * 
   * @throws NonConnecteException si la méthode est appelée alors que l'étudiant
   *         n'est pas connecté.
   */
  @Test
  void testNombreOptionsEtudiantConnecte() throws NonConnecteException {
    tests.connexion(nb, "motDePasse");
    assertEquals(2, tests.nombreOptions());
  }
  
  /**
   * Teste la méthode de choix d'une option par un étudiant connecté.
   * 
   * @throws NonConnecteException si la méthode est appelée alors que l'étudiant
   *         n'est pas connecté.
   */
  @Test
  void testChoisirOption() throws NonConnecteException {
    tests.connexion(nb, "motDePasse");
    UniteEnseignement ue = new UniteEnseignement("OCR", "Luke Skywalker");
    tests.gestionForm.ajouterEnseignementOptionnel(ue, 30);
    assertTrue(tests.choisirOption(ue));
  }
  
  
  /**
   * V�rifie qu'on ne peut pas positionner un nombre de places n�gatif sur une
   * information basique.
   */
  @Test
  void testGroupetpneg() throws Exception {
    test.setNumeroGroupeTravauxPratiques(-20);
    assertTrue(tests.getNumeroGroupeTravauxPratiques() != -20);
  }
  
  /**
   * Teste la méthode de récupération des enseignements obligatoires pour un
   * étudiant connecté.
   * 
   * @throws NonConnecteException si la méthode est appelée alors que l'étudiant
   *         n'est pas connecté.
   */
  @Test
  void testEnseignementsObligatoires() {
    tests.connexion(nb, "motDePasse");
    UniteEnseignement ueObligatoire =
        new UniteEnseignement("JAVA", "Luke Skywalker");
    tests.gestionForm.listUeObligatoire.add(ueObligatoire);
    assertEquals(tests.gestionForm.listUeObligatoire,
        tests.enseignementsObligatoires());
  }
  
  /**
   * Teste la méthode de récupération des enseignements optionnels pour un
   * étudiant connecté.
   * 
   * @throws NonConnecteException si la méthode est appelée alors que l'étudiant
   *         n'est pas connecté.
   */
  @Test
  void testEnseignementsOptionnels() {
    tests.connexion(nb, "motDePasse");
    UniteEnseignement ueOptionnelle =
        new UniteEnseignement("administration", "Luke Skywalker");
    tests.gestionForm.listUeOption.add(ueOptionnelle);
    assertEquals(tests.gestionForm.listUeOption,
        tests.enseignementsOptionnels());
  }
  
  /**
   * Teste la méthode de récupération du numéro du groupe de travaux dirigés
   * pour un étudiant connecté.
   * 
   * @throws NonConnecteException si la méthode est appelée alors que l'étudiant
   *         n'est pas connecté.
   */
  @Test
  void testGetNumeroGroupeTravauxDiriges() throws NonConnecteException {
    tests.connexion(nb, "motDePasse");
    tests.etu.setNumeroGroupeTravauxDiriges(3);
    assertEquals(3, tests.getNumeroGroupeTravauxDiriges());
  }
  
  
  /**
   * Teste la méthode de récupération du numéro du groupe de travaux pratiques
   * pour un étudiant connecté.
   * 
   * @throws NonConnecteException si la méthode est appelée alors que l'étudiant
   *         n'est pas connecté.
   */
  @Test
  public void testGetNumeroGroupeTravauxPratiques()
      throws NonConnecteException {
    tests.connexion(nb, "motDePasse");
    tests.etu.setNumeroGroupeTravauxPratiques(3);
    assertEquals(3, tests.getNumeroGroupeTravauxPratiques());
  }
  
  /**
   * Teste la méthode de récupération des enseignements suivis par un étudiant
   * connecté.
   * 
   * @throws NonConnecteException si la méthode est appelée alors que l'étudiant
   *         n'est pas connecté.
   */
  @Test
  public void testEnseignementsSuivis() throws NonConnecteException {
    tests.connexion(nb, "motDePasse");
    UniteEnseignement ueOptionnelle =
        new UniteEnseignement("administration", "Luke Skywalker");
    tests.gestionForm.ajouterEnseignementOptionnel(ueOptionnelle, 5);
    tests.choisirOption(ueOptionnelle);
    assertEquals(1, tests.enseignementsSuivis().size());
    // assertTrue(tests.gestionForm.listUeOption.contains(ueOptionnelle));
  }
  
  /**
   * Teste la méthode de récupération de la liste de tous les messages lus par
   * un étudiant connecté.
   * 
   * @throws NonConnecteException si la méthode est appelée alors que l'étudiant
   *         n'est pas connecté.
   */
  @Test
  public void testListeTousMessages() throws NonConnecteException {
    tests.connexion(nb, "motDePasse");
    tests.etu.listMessageLue.add("Message lu 1");
    tests.etu.listMessageLue.add("Message lu 2");
    assertEquals(2, tests.listeTousMessages().size());
  }
  
  /**
   * Teste la méthode de récupération de la liste de tous les messages non lus
   * par un étudiant connecté.
   * 
   * @throws NonConnecteException si la méthode est appelée alors que l'étudiant
   *         n'est pas connecté.
   */
  @Test
  public void testListeMessageNonLus() throws NonConnecteException {
    tests.connexion(nb, "motDePasse");
    tests.etu.listMessageNonLue.add("Message non lu 1");
    tests.etu.listMessageNonLue.add("Message non lu 2");
    assertEquals(2, tests.listeMessageNonLus().size());
  }
  
  /**
   * Teste la méthode de vérification de l'inscription finalisée pour un
   * étudiant connecté.
   * 
   * @throws NonConnecteException si la méthode est appelée alors que l'étudiant
   *         n'est pas connecté.
   */
  @Test
  public void testInscriptionFinalisee() throws NonConnecteException {
    tests.connexion(nb, "motDePasse");
    tests.etu.setNumeroGroupeTravauxDiriges(1);
    tests.etu.setNumeroGroupeTravauxPratiques(2);
    
    UniteEnseignement ueOption1 =
        new UniteEnseignement("JAVA", "Luke Skywalker");
    UniteEnseignement ueOption2 =
        new UniteEnseignement("OCR", "Luke Skywalker");
    tests.etu.ueOptionnelle.add(ueOption1);
    tests.etu.ueOptionnelle.add(ueOption2);
    
    assertTrue(tests.inscriptionFinalisee());
  }
  
  /**
   * Teste le constructeur de la classe Etudiant.
   */
  @Test
  void testConstructeur() {
    InformationPersonnelle inf =
        new InformationPersonnelle("Vador", "Dark", "adresse", 20);
    Etudiant etu = new Etudiant(inf, "mdp");
    
    assertEquals(inf.getNom(), "Vador");
    assertEquals(inf.getPrenom(), "Dark");
    assertEquals(etu.getMotDePasse(), "mdp");
    assertTrue(inf.getAdresse() != null);
    assertTrue(inf.getAge() >= 0);
    
    assertTrue(etu.getIdEtu() >= 0);
    assertTrue(etu.getNbOption() > -2);
  }
  
  
}


