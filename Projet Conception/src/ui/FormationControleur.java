package ui;

import formation.Etudiant;
import formation.GestionFormation;
import formation.UniteEnseignement;
import io.GestionSauvegarde;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;



/**
 * Le contr�leur associ� � la fen�tre d�finie dans formation.fxml.
 *
 * @author Eric Cariou
 */
public class FormationControleur {
  
  @FXML
  private ResourceBundle resources;
  
  @FXML
  private URL location;
  
  @FXML
  private CheckBox checkInscriptionFinalisee;
  
  @FXML
  private TextField entreeAdresseEtudiant;
  
  @FXML
  private TextField entreeAgeEtudiant;
  
  @FXML
  private TextField entreeCapaciteAccueil;
  
  @FXML
  private TextField entreeEmailResponsableFormation;
  
  @FXML
  private TextField entreeGroupeTDEtudiant;
  
  @FXML
  private TextField entreeGroupeTPEtudiant;
  
  @FXML
  private TextField entreeNomEtudiant;
  
  @FXML
  private TextField entreeNomFormation;
  
  @FXML
  private TextField entreeNomResponsableFormation;
  
  @FXML
  private TextField entreeNomResponsableUE;
  
  @FXML
  private TextField entreeNomUE;
  
  @FXML
  private TextField entreeNombreChoixOptions;
  
  @FXML
  private TextField entreePrenomEtudiant;
  
  @FXML
  private TextField entreeTailleGroupeTD;
  
  @FXML
  private TextField entreeTailleGroupeTP;
  
  @FXML
  private Label labelListeEtudiants;
  
  @FXML
  private Label labelNbGroupesTD;
  
  @FXML
  private Label labelNbGroupesTP;
  
  @FXML
  private ListView<Etudiant> listeEtudiants;
  
  @FXML
  private ListView<UniteEnseignement> listeUEObligatoires;
  
  @FXML
  private ListView<UniteEnseignement> listeUEOptionnelles;
  
  @FXML
  private ToggleGroup obligation;
  
  @FXML
  private RadioButton radioBoutonObligatoire;
  
  @FXML
  private RadioButton radioBoutonOptionnelle;
  
  GestionFormation form;
  
  GestionSauvegarde save;
  
  @FXML
  void actionBoutonAffectationAutomatique(ActionEvent event) {
    form.attribuerAutomatiquementGroupes();
    
    int sizeGroupeTd = form.nombreGroupesTravauxDiriges();
    int sizeGroupeTp = form.nombreGroupesTravauxPratiques();
    
    labelNbGroupesTD.setText(Integer.toString(sizeGroupeTd));
    labelNbGroupesTP.setText(Integer.toString(sizeGroupeTp));
  }
  
  @FXML
  void actionBoutonAffectationManuelleGroupes(ActionEvent event) {
    
    
    Etudiant etu = listeEtudiants.getSelectionModel().getSelectedItem();
    
    int inGroupeTd = Integer.parseInt(entreeGroupeTDEtudiant.getText());
    int inGroupeTp = Integer.parseInt(entreeGroupeTPEtudiant.getText());
    
    listeEtudiants.getItems().remove(etu);
    
    int res = form.changerGroupe(etu, inGroupeTd, inGroupeTp);
    
    if (res == -3) {
      listeEtudiants.getItems().add(etu);
      afficherPopupErreur("Auncun changement de groupe n'a pu être fait !");
    } else if (res == -1) {
      listeEtudiants.getItems().add(etu);
      afficherPopupInformation("Le groupe de tp a bien été changé");
    } else if (res == -2) {
      listeEtudiants.getItems().add(etu);
      afficherPopupInformation("Le groupe de td a bien été changé");
    } else if (res == 0) {
      listeEtudiants.getItems().add(etu);
      afficherPopupInformation(
          "Vous avez bien modifié les groupes de tp et de td");
    }
    listeEtudiants.getItems().clear();
    listeEtudiants.getItems().addAll(form.gestionEtu.listeEtudiants);
    
  }
  
  @FXML
  void actionBoutonAfficherEtudiantsGroupeTD(ActionEvent event) {
    Set<Etudiant> listeEtu = new HashSet<Etudiant>();
    listeEtudiants.getItems().clear();
    int groupeTd = Integer.parseInt(entreeGroupeTDEtudiant.getText());
    
    listeEtu = form.listeEtudiantsGroupeDirige(groupeTd);
    if (listeEtu != null) {
      for (Etudiant e : listeEtu) {
        listeEtudiants.getItems().add(e);
      }
    } else {
      afficherPopupErreur(
          "La liste de groupe de TD : " + groupeTd + " est vide");
    }
  }
  
  @FXML
  void actionBoutonAfficherEtudiantsGroupeTP(ActionEvent event) {
    Set<Etudiant> listeEtu = new HashSet<Etudiant>();
    listeEtudiants.getItems().clear();
    int groupeTp = Integer.parseInt(entreeGroupeTPEtudiant.getText());
    
    listeEtu = form.listeEtudiantsGroupePratique(groupeTp);
    if (listeEtu != null) {
      for (Etudiant e : listeEtu) {
        listeEtudiants.getItems().add(e);
      }
    } else {
      afficherPopupErreur(
          "La liste de groupe de TP : " + groupeTp + " est vide");
    }
  }
  
  @FXML
  void actionBoutonAfficherEtudiantsUEOptionnelle(ActionEvent event) {
    Set<Etudiant> listeEtu = new HashSet<Etudiant>();
    listeEtudiants.getItems().clear();
    
    UniteEnseignement ue =
        listeUEOptionnelles.getSelectionModel().getSelectedItem();
    if (ue != null) {
      listeEtu = form.listeEtudiantsOption(ue);
      for (Etudiant e : listeEtu) {
        listeEtudiants.getItems().add(e);
      }
    } else {
      afficherPopupErreur("Veuillez sélectionner une UE optionnelle");
    }
  }
  
  @FXML
  void actionBoutonAfficherTousEtudiants(ActionEvent event) {
    Set<Etudiant> listeEtu = new HashSet<Etudiant>();
    listeEtudiants.getItems().clear();
    
    if (form.gestionEtu.listeEtudiants.size() != 0) {
      listeEtu.addAll(form.gestionEtu.listeEtudiants);
      
      for (Etudiant e : listeEtu) {
        listeEtudiants.getItems().add(e);
      }
    } else {
      afficherPopupErreur("Aucun étudiant(s) inscrit(s) !");
    }
  }
  
  @FXML
  void actionBoutonCreerFormation(ActionEvent event) {
    
    String nomForm = entreeNomFormation.getText();
    String nomResp = entreeNomResponsableFormation.getText();
    String emailForm = entreeEmailResponsableFormation.getText();
    int cptErr = 0;
    String error = "";
    if (nomForm == "") {
      error = error + "- Veuillez rentrez le nom de la formation\n";
      cptErr++;
    }
    if (nomResp == "") {
      error = error + "- Veuillez rentrez le nom du responsable\n";
      cptErr++;
    }
    if (emailForm == "") {
      error = error + "- Veuillez rentrez l'email\n";
      cptErr++;
    }
    if (cptErr > 0) {
      afficherPopupErreur(error);
    }
    if (form == null) {
      if (cptErr == 0) {
        form = new GestionFormation();
        EtudiantsControleur.form = form;
        form.creerFormation(nomForm, nomResp, emailForm);
        
        String nomF = form.getNomFormation();
        String nomR = form.getNomResponsableFormation();
        String emailF = form.getEmailResponsableFormation();
        if (nomF != null && nomR != null && emailF != null && emailF != "") {
          afficherPopupInformation(
              "Vous avez bien créé la formation : " + form.getNomFormation());
        }
      }
    } else {
      afficherPopupErreur("La formation a déjà été créé !");
    }
  }
  
  @FXML
  void actionBoutonCreerNouvelleUE(ActionEvent event) {
    if (form != null) {
      String nomUe = entreeNomUE.getText();
      String nomResp = entreeNomResponsableUE.getText();
      String nbPlaceStr = entreeCapaciteAccueil.getText();
      if (radioBoutonObligatoire.isSelected()) {
        
        UniteEnseignement ue = new UniteEnseignement(nomUe, nomResp);
        boolean ajout = form.ajouterEnseignementObligatoire(ue);
        if (ajout) {
          afficherPopupInformation(
              "L'UE obligatoire " + nomUe + " a bien été rajouté");
          listeUEObligatoires.getItems().add(ue);
          entreeNomUE.setText("");
          entreeNomResponsableUE.setText("");
          entreeCapaciteAccueil.setText("");
        } else {
          afficherPopupErreur("L'UE Obligatoire non rajouté");
        }
      } else if (radioBoutonOptionnelle.isSelected()) {
        if (!nbPlaceStr.equals("")) {
          int nbPlace = Integer.parseInt(nbPlaceStr);
          UniteEnseignement ue = new UniteEnseignement(nomUe, nomResp);
          boolean ajout = form.ajouterEnseignementOptionnel(ue, nbPlace);
          
          if (ajout) {
            afficherPopupInformation(
                "L'UE optionnelle " + nomUe + " a bien été rajouté");
            listeUEOptionnelles.getItems().add(ue);
            entreeNomUE.setText("");
            entreeNomResponsableUE.setText("");
            entreeCapaciteAccueil.setText("");
          } else {
            afficherPopupErreur("L'UE Optionnelle non rajouté");
          }
        } else {
          afficherPopupErreur(
              "Veuillez rentrer la capacité d'accueil de l'UE optionnelle");
        }
      }
    } else {
      afficherPopupErreur(
          "Veuillez définir une formation avant de créer une UE");
    }
  }
  
  @FXML
  void actionBoutonNombreChoixOptions(ActionEvent event) {
    if (form != null) {
      if (form.getNbOption() == -1) {
        String nbOptionStr = entreeNombreChoixOptions.getText();
        if (nbOptionStr != "") {
          int nbOption = Integer.parseInt(nbOptionStr);
          form.definirNombreOptions(nbOption);
          afficherPopupInformation(
              "Le nombre d'option a bien été rentré : " + form.getNbOption());
        } else {
          afficherPopupErreur("Veuillez Remplir le champs du nombre d'option");
        }
      } else {
        afficherPopupErreur("Le nombre d'option a déjà été défini");
      }
    } else {
      afficherPopupErreur(
          "Veuillez définir une formation avant de rentrer le nombre d'option");
    }
  }
  
  @FXML
  void actionBoutonSetTailleGroupeTD(ActionEvent event) {
    if (form != null) {
      if (form.getTailleGroupeDirige() == -1) {
        String sizeGroupeTdStr = entreeTailleGroupeTD.getText();
        if (sizeGroupeTdStr != "") {
          int sizeGroupeTd = Integer.parseInt(sizeGroupeTdStr);
          
          form.setTailleGroupeDirige(sizeGroupeTd);
          afficherPopupInformation(
              "Le nombre de groupe de td : " + form.getTailleGroupeDirige());
        } else {
          afficherPopupErreur(
              "Veuillez Rentrer uniquement des chiffres ou nombres");
        }
      } else {
        afficherPopupErreur("Le groupe de TD a déjà été défini");
      }
    } else {
      afficherPopupErreur(
          "Veuillez définir une formation avant de rentrer la taille des groupes de td");
    }
  }
  
  @FXML
  void actionBoutonSetTailleGroupeTP(ActionEvent event) {
    if (form != null) {
      if (form.getTailleGroupePratique() == -1) {
        String sizeGroupeTpStr = entreeTailleGroupeTP.getText();
        if (sizeGroupeTpStr != "") {
          int sizeGroupeTp = Integer.parseInt(sizeGroupeTpStr);
          form.setTailleGroupePratique(sizeGroupeTp);
          afficherPopupInformation(
              "Le nombre de groupe de tp : " + form.getTailleGroupePratique());
        } else {
          afficherPopupErreur(
              "Veuillez Remplir le champs de la taille du groupe de tp");
        }
      } else {
        afficherPopupErreur("Le groupe de TP a déjà été défini");
      }
    } else {
      afficherPopupErreur(
          "Veuillez définir une formation avant de rentrer la taille des groupes de td");
    }
  }
  
  @FXML
  void actionMenuApropos(ActionEvent event) {
    afficherPopupInformation(
        "Application fait le 10/01/2024, par Andrea LeDortz, Liam Frit, Sully Millet");
  }
  
  @FXML
  void actionMenuCharger(ActionEvent event) {
    FileChooser exploFichier = new FileChooser();
    exploFichier.setTitle("Charger la sauvegarde");
    exploFichier.getExtensionFilters()
        .addAll(new ExtensionFilter("All Files", "*.*"));
    
    File fichierChoisi = exploFichier.showOpenDialog(null);
    
    if (fichierChoisi != null) {
      try {
        
        GestionSauvegarde chargeSavegarde = new GestionSauvegarde();
        chargeSavegarde.chargerDonnees(fichierChoisi.getAbsolutePath());
        
        form = chargeSavegarde.getGestionFormation();
        save = chargeSavegarde;
        
        EtudiantsControleur.form = form;
        
        
        
        entreeNomFormation.setText(form.getNomFormation());
        entreeNomResponsableFormation
            .setText(form.getNomResponsableFormation());
        entreeEmailResponsableFormation
            .setText(form.getEmailResponsableFormation());
        
        entreeTailleGroupeTD
            .setText(Integer.toString(form.getTailleGroupeDirige()));
        entreeTailleGroupeTP
            .setText(Integer.toString(form.getTailleGroupePratique()));
        entreeNombreChoixOptions.setText(Integer.toString(form.getNbOption()));
        
        labelNbGroupesTD
            .setText(Integer.toString(form.nombreGroupesTravauxDiriges()));
        labelNbGroupesTP
            .setText(Integer.toString(form.nombreGroupesTravauxPratiques()));
        
        listeUEObligatoires.getItems().clear();
        listeUEObligatoires.getItems().addAll(form.listUeObligatoire);
        
        listeUEOptionnelles.getItems().clear();
        listeUEOptionnelles.getItems().addAll(form.listUeOption);
        
        afficherPopupInformation("Les données ont été chargées avec succès.");
        
      } catch (IOException e) {
        afficherPopupErreur(
            "Erreur lors du chargement des données : " + e.getMessage());
        e.printStackTrace();
      }
    }
  }
  
  @FXML
  void actionMenuQuitter(ActionEvent event) {
    Platform.exit();
  }
  
  @FXML
  void actionMenuSauvegarder(ActionEvent event) {
    save = new GestionSauvegarde(form);
    try {
      save.sauvegarderDonnees("Sauvegarde");
      afficherPopupInformation("La sauvegarde a bien été effectué");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      afficherPopupErreur("Erreur lors de la sauvegarde !");
      e.printStackTrace();
    }
  }
  
  @FXML
  void actionSelectionEtudiant(MouseEvent event) {
    if (form != null) {
      Etudiant etu = listeEtudiants.getSelectionModel().getSelectedItem();
      if (etu != null) {
        entreeNomEtudiant.setText(etu.etu.getNom());
        entreePrenomEtudiant.setText(etu.etu.getPrenom());
        entreeAdresseEtudiant.setText(etu.etu.getAdresse());
        entreeAgeEtudiant.setText(Integer.toString(etu.etu.getAge()));
        if (etu.getNumeroGroupeTravauxDiriges() != 0) {
          entreeGroupeTDEtudiant
              .setText(Integer.toString(etu.getNumeroGroupeTravauxDiriges()));
        } else {
          entreeGroupeTDEtudiant.setText("Aucun");
        }
        if (etu.getNumeroGroupeTravauxPratiques() != 0) {
          entreeGroupeTPEtudiant
              .setText(Integer.toString(etu.getNumeroGroupeTravauxPratiques()));
        } else {
          entreeGroupeTPEtudiant.setText("Aucun");
        }
      } else {
        afficherPopupErreur("Selectionner un étudiant ou ");
      }
    } else {
      afficherPopupErreur("Veuillez créer une formation");
    }
  }
  
  @FXML
  void actionSelectionUEObligatoire(MouseEvent event) {
    if (form != null) {
      UniteEnseignement ue =
          listeUEObligatoires.getSelectionModel().getSelectedItem();
      if (ue != null) {
        entreeNomUE.setText("");
        entreeNomResponsableUE.setText("");
        entreeCapaciteAccueil.setText("");
        
        entreeNomUE.setText(ue.getNomUe());
        entreeNomResponsableUE.setText(ue.getNomResp());
      } else {
        afficherPopupErreur(
            "Sélectionner un UE obligatoire ou veuillez en créer une");
      }
    } else {
      afficherPopupErreur("Veuillez créer une formation");
    }
  }
  
  @FXML
  void actionSelectionUEOptionnelle(MouseEvent event) {
    if (form != null) {
      UniteEnseignement ue =
          listeUEOptionnelles.getSelectionModel().getSelectedItem();
      if (ue != null) {
        entreeNomUE.setText("");
        entreeNomResponsableUE.setText("");
        entreeCapaciteAccueil.setText("");
        
        entreeNomUE.setText(ue.getNomUe());
        entreeNomResponsableUE.setText(ue.getNomResp());
        entreeCapaciteAccueil.setText(Integer.toString(ue.getPlaces()));
        
        
      } else {
        afficherPopupErreur(
            "Sélectionner un UE optionnelle ou veuillez en créer une");
      }
    } else {
      afficherPopupErreur("Veuillez créer une formation");
    }
  }
  
  private void afficherPopup(String message, AlertType type) {
    Alert alert = new Alert(type);
    if (type == AlertType.ERROR) {
      alert.setTitle("Erreur");
    } else {
      alert.setTitle("Information");
    }
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.setResizable(true);
    alert.showAndWait();
  }
  
  private void afficherPopupErreur(String message) {
    this.afficherPopup(message, AlertType.ERROR);
  }
  
  private void afficherPopupInformation(String message) {
    this.afficherPopup(message, AlertType.INFORMATION);
  }
  
  @FXML
  void initialize() {
    
  }
}
