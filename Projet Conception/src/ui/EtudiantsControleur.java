package ui;

import formation.Etudiant;
import formation.GestionFormation;
import formation.InformationPersonnelle;
import formation.NonConnecteException;
import formation.UniteEnseignement;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Le contr�leur associ� � la fen�tre d�finie dans etudiants.fxml.
 *
 * @author Eric Cariou
 */
public class EtudiantsControleur {
  
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
  private TextField entreeGroupeTD;
  
  @FXML
  private TextField entreeGroupeTP;
  
  @FXML
  private TextField entreeMotDePasseEtudiant;
  
  @FXML
  private TextField entreeNomEtudiant;
  
  @FXML
  private TextField entreeNombreOptions;
  
  @FXML
  private TextField entreeNumeroEtudiant;
  
  @FXML
  private TextField entreePrenomEtudiant;
  
  @FXML
  private ListView<String> listeMessagesNonLus;
  
  @FXML
  private ListView<String> listeTousMessages;
  
  @FXML
  private ListView<UniteEnseignement> listeUEOptionnellesFormation;
  
  @FXML
  private ListView<UniteEnseignement> listeUESuiviesEtudiant;
  
  @FXML
  private TextArea zoneTexteContenuMessage;
  
  public static GestionFormation form;
  
  boolean premiereInscription = true;
  
  @FXML
  void actionBoutonChoisirOption(ActionEvent event) {
    UniteEnseignement ue =
        listeUEOptionnellesFormation.getSelectionModel().getSelectedItem();
    boolean place = false;
    int nbOptionForm = -1;
    
    int curNbOption = 0;
    
    for (Etudiant e : form.gestionEtu.listeEtudiants) {
      if (form.gestionEtu.etu.equals(e)) {
        curNbOption = e.getNbOption();
      }
    }
    
    nbOptionForm = form.getNbOption();
    int nbPersonIn = form.listeEtudiantsOption(ue).size();
    int nbPlaceUe = ue.getPlaces();
    
    if (nbOptionForm != -1) {
      if (nbPersonIn < nbPlaceUe) {
        if (curNbOption < nbOptionForm) {
          try {
            place = form.gestionEtu.choisirOption(ue);
            if (place) {
              listeUESuiviesEtudiant.getItems().add(ue);
              for (Etudiant e : form.gestionEtu.listeEtudiants) {
                if (form.gestionEtu.etu.equals(e)) {
                  e.ueOptionnelle.add(ue);
                  curNbOption++;
                  e.setNbOption(curNbOption);
                  form.gestionEtu.etu = e;
                }
              }
              listeUEOptionnellesFormation.getItems().remove(ue);
            }
          } catch (NonConnecteException e) {
            e.printStackTrace();
          }
        } else {
          afficherPopupErreur("Vous ne pouvez pas avoir plus de " + nbOptionForm
              + " UE optionnelle(s)");
        }
      } else {
        afficherPopupErreur("L'UE Optionnelle est pleine");
      }
    } else {
      afficherPopupErreur("Le nombre UE optionnelle n'a pas été défini");
    }
  }
  
  @FXML
  void actionBoutonConnexion(ActionEvent event) {
    
    int id = 0;
    try {
      id = Integer.valueOf(entreeNumeroEtudiant.getText());
      
    } catch (NumberFormatException e) {
      afficherPopupErreur("L'identifiant n'est pas valide");
    }
    
    String mdp = entreeMotDePasseEtudiant.getText();
    
    boolean connect = form.gestionEtu.connexion(id, mdp);
    
    if (connect) {
      afficherPopupInformation("Vous êtes bien connecté");
      
      int age = form.gestionEtu.etu.etu.getAge();
      entreeAgeEtudiant.setText(String.valueOf(age));
      String nom = form.gestionEtu.etu.etu.getNom();
      entreeNomEtudiant.setText(nom);
      String prenom = form.gestionEtu.etu.etu.getPrenom();
      entreePrenomEtudiant.setText(prenom);
      String adresse = form.gestionEtu.etu.etu.getAdresse();
      entreeAdresseEtudiant.setText(adresse);
      int option = 0;
      
      int tp = 0;
      
      option = form.getNbOption();
      try {
        tp = form.gestionEtu.getNumeroGroupeTravauxPratiques();
        if (tp == 0) {
          entreeGroupeTP.setText("Non Défini");
        } else {
          entreeGroupeTP.setText(String.valueOf(tp));
        }
      } catch (NonConnecteException e) {
        e.printStackTrace();
      }
      int td = 0;
      try {
        td = form.gestionEtu.getNumeroGroupeTravauxDiriges();
        if (td == 0) {
          entreeGroupeTD.setText("Non Défini");
        } else {
          entreeGroupeTD.setText(String.valueOf(td));
        }
      } catch (NonConnecteException e) {
        e.printStackTrace();
      }
      
      if (option != -1) {
        entreeNombreOptions.setText(String.valueOf(option));
      } else {
        entreeNombreOptions.setText("Non Défini");
      }
      
      Set<UniteEnseignement> listeUe = new HashSet<UniteEnseignement>();
      listeUESuiviesEtudiant.getItems().clear();
      
      if (form.gestionEtu.listeEtudiants.size() != 0) {
        listeUe.addAll(form.gestionEtu.enseignementsObligatoires());
        
        for (Etudiant e : form.gestionEtu.listeEtudiants) {
          if (form.gestionEtu.etu.equals(e)) {
            e.ueSuivies.addAll(listeUe);
            e.ueSuivies.addAll(e.ueOptionnelle);
            form.gestionEtu.etu = e;
          }
        }
        listeUESuiviesEtudiant.getItems().addAll(form.gestionEtu.etu.ueSuivies);
      } else {
        afficherPopupErreur("Aucune(s) ue(s) !");
      }
      Set<UniteEnseignement> listeUeOption = new HashSet<UniteEnseignement>();
      listeUEOptionnellesFormation.getItems().clear();
      
      if (form.gestionEtu.listeEtudiants.size() != 0) {
        listeUeOption.addAll(form.gestionEtu.enseignementsOptionnels());
        
        for (UniteEnseignement ue : listeUeOption) {
          if (!form.gestionEtu.etu.ueOptionnelle.contains(ue)) {
            listeUEOptionnellesFormation.getItems().add(ue);
          }
        }
      } else {
        afficherPopupErreur("Aucune(s) ue(s) !");
      }
      
      Set<String> listemessage = new HashSet<String>();
      listeTousMessages.getItems().clear();
      
      form.gestionEtu.etu
          .ajouterMessage("Bienvenue ! Vous venez de vous inscrire.");
      
      if (form.gestionEtu.etu.listMessageLue.size() != 0) {
        try {
          listemessage.addAll(form.gestionEtu.listeTousMessages());
        } catch (NonConnecteException e1) {
          e1.printStackTrace();
        }
        
        for (Etudiant e : form.gestionEtu.listeEtudiants) {
          if (form.gestionEtu.etu.equals(e)) {
            form.gestionEtu.etu = e;
          }
        }
        listeTousMessages.getItems().addAll(form.gestionEtu.etu.listMessageLue);
      } else {
        afficherPopupErreur("Aucune(s) ue(s) !");
      }
      if (premiereInscription == true) {
        
        Set<String> listemessagenonlu = new HashSet<String>();
        listeMessagesNonLus.getItems().clear();
        
        form.gestionEtu.etu
            .ajouterMessage("Bienvenue ! Vous venez de vous inscrire.");
        
        if (form.gestionEtu.etu.listMessageNonLue.size() != 0) {
          try {
            listemessagenonlu.addAll(form.gestionEtu.listeMessageNonLus());
          } catch (NonConnecteException e1) {
            e1.printStackTrace();
          }
          
          for (Etudiant e : form.gestionEtu.listeEtudiants) {
            if (form.gestionEtu.etu.equals(e)) {
              form.gestionEtu.etu = e;
            }
          }
          
          form.gestionEtu.etu.listMessageNonLue.remove(
              "Bienvenue ! Vous venez de vous inscrire. Veuillez choisir vos option pour cette formation");
          
          listeMessagesNonLus.getItems()
              .addAll(form.gestionEtu.etu.listMessageNonLue);
        } else {
          afficherPopupErreur("Probleme lors de la création !");
        }
        premiereInscription = false;
        
      }
      
      int grptd;
      
      try {
        grptd = form.gestionEtu.getNumeroGroupeTravauxDiriges();
        if (grptd != 0) {
          Set<String> listemessagetd = new HashSet<String>();
          listeTousMessages.getItems().clear();
          
          form.gestionEtu.etu.ajouterMessage(
              "Vous appartenez au groupe de td numero " + grptd);
          
          if (form.gestionEtu.etu.listMessageLue.size() != 0) {
            try {
              listemessagetd.addAll(form.gestionEtu.listeTousMessages());
            } catch (NonConnecteException e1) {
              e1.printStackTrace();
            }
            
            for (Etudiant e : form.gestionEtu.listeEtudiants) {
              if (form.gestionEtu.etu.equals(e)) {
                form.gestionEtu.etu = e;
              }
            }
            listeTousMessages.getItems()
                .addAll(form.gestionEtu.etu.listMessageLue);
          } else {
            afficherPopupErreur("Aucune(s) ue(s) !");
          }
          
          Set<String> listemessagenonlu = new HashSet<String>();
          listeMessagesNonLus.getItems().clear();
          
          form.gestionEtu.etu.ajouterMessage(
              "Vous appartenez au groupe de td numero " + grptd);
          
          if (form.gestionEtu.etu.listMessageNonLue.size() != 0) {
            try {
              listemessagenonlu.addAll(form.gestionEtu.listeMessageNonLus());
            } catch (NonConnecteException e1) {
              e1.printStackTrace();
            }
            
            for (Etudiant e : form.gestionEtu.listeEtudiants) {
              if (form.gestionEtu.etu.equals(e)) {
                form.gestionEtu.etu = e;
              }
            }
            
            form.gestionEtu.etu.listMessageNonLue.remove(
                "Bienvenue ! Vous venez de vous inscrire. Veuillez choisir vos option pour cette formation");
            
            listeMessagesNonLus.getItems()
                .addAll(form.gestionEtu.etu.listMessageNonLue);
          } else {
            afficherPopupErreur("Probleme lors de la création !");
          }
        }
      } catch (NonConnecteException e) {
        e.printStackTrace();
      }
      
      int grptp;
      
      try {
        grptp = form.gestionEtu.getNumeroGroupeTravauxDiriges();
        if (grptp != 0) {
          Set<String> listemessagetp = new HashSet<String>();
          listeTousMessages.getItems().clear();
          
          form.gestionEtu.etu.ajouterMessage(
              "Vous appartenez au groupe de tp numero " + grptp);
          
          if (form.gestionEtu.etu.listMessageLue.size() != 0) {
            try {
              listemessagetp.addAll(form.gestionEtu.listeTousMessages());
            } catch (NonConnecteException e1) {
              e1.printStackTrace();
            }
            
            for (Etudiant e : form.gestionEtu.listeEtudiants) {
              if (form.gestionEtu.etu.equals(e)) {
                form.gestionEtu.etu = e;
              }
            }
            listeTousMessages.getItems()
                .addAll(form.gestionEtu.etu.listMessageLue);
          } else {
            afficherPopupErreur("Aucune(s) ue(s) !");
          }
          
          Set<String> listemessagenonlu = new HashSet<String>();
          listeMessagesNonLus.getItems().clear();
          
          form.gestionEtu.etu.ajouterMessage(
              "Vous appartenez au groupe de tp numero " + grptp);
          
          if (form.gestionEtu.etu.listMessageNonLue.size() != 0) {
            try {
              listemessagenonlu.addAll(form.gestionEtu.listeMessageNonLus());
            } catch (NonConnecteException e1) {
              e1.printStackTrace();
            }
            
            for (Etudiant e : form.gestionEtu.listeEtudiants) {
              if (form.gestionEtu.etu.equals(e)) {
                form.gestionEtu.etu = e;
              }
            }
            
            form.gestionEtu.etu.listMessageNonLue.remove(
                "Bienvenue ! Vous venez de vous inscrire. Veuillez choisir vos option pour cette formation");
            
            listeMessagesNonLus.getItems()
                .addAll(form.gestionEtu.etu.listMessageNonLue);
          } else {
            afficherPopupErreur("Probleme lors de la création !");
          }
        }
      } catch (NonConnecteException e) {
        e.printStackTrace();
      }
      
    } else {
      afficherPopupInformation("Veuillez vérifier vos identifiants");
    }
  }
  
  @FXML
  void actionBoutonDeconnexion(ActionEvent event) {
    try {
      form.gestionEtu.deconnexion();
    } catch (NonConnecteException e) {
      e.printStackTrace();
    }
    entreeNombreOptions.setText(String.valueOf(""));
    entreeGroupeTD.setText(String.valueOf(""));
    entreeGroupeTP.setText(String.valueOf(""));
    entreeNomEtudiant.setText("");
    entreePrenomEtudiant.setText("");
    entreeAdresseEtudiant.setText("");
    entreeAgeEtudiant.setText(String.valueOf(""));
    entreeMotDePasseEtudiant.setText("");
    entreeNumeroEtudiant.setText(String.valueOf(""));
    listeUESuiviesEtudiant.getItems().clear();
    listeMessagesNonLus.getItems().clear();
    listeTousMessages.getItems().clear();
  }
  
  @FXML
  void actionBoutonInscription(ActionEvent event) {
    int cpt = 0;
    String error = "";
    String nom = entreeNomEtudiant.getText();
    if (nom == "") {
      error += " Veuillez entr�e un nom \n";
      cpt++;
    }
    String prenom = entreePrenomEtudiant.getText();
    if (prenom == "") {
      error += " Veuillez entr�e un prenom \n";
      cpt++;
    }
    String adresse = entreeAdresseEtudiant.getText();
    if (adresse == "") {
      error += " Veuillez entr�e une adresse \n";
      cpt++;
    }
    String mdp = entreeMotDePasseEtudiant.getText();
    if (mdp == "") {
      error += " Veuillez entr�e un mot de passe \n";
      cpt++;
    }
    int age = 0;
    try {
      age = Integer.valueOf(entreeAgeEtudiant.getText());
      if (age == 0) {
        error += "Veuillez entrer un age correcte";
        cpt++;
      }
    } catch (NumberFormatException e) {
      error += " Veuillez mettre un age valide";
      cpt++;
    }
    
    if (cpt == 0) {
      InformationPersonnelle infos =
          new InformationPersonnelle(nom, prenom, adresse, age);
      int numeroEtudiant = form.gestionEtu.inscription(infos, mdp);
      
      entreeNumeroEtudiant.setText(String.valueOf(numeroEtudiant));
      entreeNomEtudiant.setText("");
      entreePrenomEtudiant.setText("");
      entreeAdresseEtudiant.setText("");
      entreeAgeEtudiant.setText(String.valueOf(""));
    } else {
      afficherPopupErreur(error);
    }
    
  }
  
  @FXML
  void actionBoutonRafraichirListesMessages(ActionEvent event) {
    for (Etudiant e : form.gestionEtu.listeEtudiants) {
      if (form.gestionEtu.etu.equals(e)) {
        listeMessagesNonLus.getItems().clear();
        listeMessagesNonLus.getItems()
            .addAll(form.gestionEtu.etu.listMessageNonLue);
        zoneTexteContenuMessage.setText("");
      }
    }
  }
  
  @FXML
  void actionSelectionMessageListeMessagesNonLus(MouseEvent event) {
    String info = listeMessagesNonLus.getSelectionModel().getSelectedItem();
    
    zoneTexteContenuMessage.setText(info);
    form.gestionEtu.etu.messagelu(info);
    
  }
  
  @FXML
  void actionSelectionMessageListeTousMessages(MouseEvent event) {
    String info = listeTousMessages.getSelectionModel().getSelectedItem();
    for (Etudiant e : form.gestionEtu.listeEtudiants) {
      if (form.gestionEtu.etu.equals(e)) {
        for (String s : e.listMessageNonLue) {
          e.messagelu(s);
          form.gestionEtu.etu = e;
        }
      }
    }
    
    zoneTexteContenuMessage.setText(info);
    form.gestionEtu.etu.messagelu(info);
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
    // System.out.print(form.toString());
  }
  
}
