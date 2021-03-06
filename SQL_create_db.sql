#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------


#------------------------------------------------------------
# Table: Professeur
#------------------------------------------------------------

CREATE TABLE Professeur(
        id_prof     Int NOT NULL ,
        nom         Varchar (25) NOT NULL ,
        prenom      Varchar (25) NOT NULL ,
        specialite  Varchar (25) NOT NULL ,
        departement Varchar (25) NOT NULL ,
        email       Varchar (25) ,
        mdp         Varchar (25) ,
        PRIMARY KEY (id_prof )
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Filiere
#------------------------------------------------------------

CREATE TABLE Filiere(
        id_filiere        Int NOT NULL ,
        intitule          Varchar (255) NOT NULL ,
        date_acreditation Datetime NOT NULL ,
        id_prof           Int NOT NULL ,
        PRIMARY KEY (id_filiere )
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Module
#------------------------------------------------------------

CREATE TABLE Module(
        id_module            Int NOT NULL ,
        intitule             Varchar (255) NOT NULL ,
        volume_horaire_cours Int ,
        volume_horaire_td    Int ,
        volume_horaire_tp    Int ,
        volume_horaire_ap    Int ,
        nb_module            Int NOT NULL ,
        id_semestre          Int ,
        id_prof              Int NOT NULL ,
        PRIMARY KEY (id_module )
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Semaine
#------------------------------------------------------------

CREATE TABLE Semaine(
        num_semaine Int NOT NULL ,
        debut       Datetime NOT NULL ,
        fin         Datetime NOT NULL ,
        id_planing  Int NOT NULL ,
        nb_semaine  Int ,
        id_semestre Int ,
        PRIMARY KEY (num_semaine )
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Planing
#------------------------------------------------------------

CREATE TABLE Planing(
        id_planing         Int NOT NULL ,
        anne_universitaire Int NOT NULL ,
        num_semaine        Int NOT NULL ,
        PRIMARY KEY (id_planing )
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Seance
#------------------------------------------------------------

CREATE TABLE Seance(
        id_seance          Int NOT NULL ,
        type               Varchar (25) NOT NULL ,
        heure_debut        Time NOT NULL ,
        statut             Bool ,
        id_prof            Int NOT NULL ,
        nb_seance          Int ,
        id_planing         Int NOT NULL ,
        nb_seance_repartir Int ,
        id_elem            Int NOT NULL ,
        PRIMARY KEY (id_seance )
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: element_module
#------------------------------------------------------------

CREATE TABLE element_module(
        id_elem              Int NOT NULL ,
        intitule             Varchar (255) NOT NULL ,
        nb_element           Int ,
        id_module            Int NOT NULL ,
        volume_horaire_cours Int ,
        volume_horaire_td    Int ,
        volume_horaire_tp    Int ,
        volume_horaire_ap    Int ,
        id_prof              Int NOT NULL ,
        PRIMARY KEY (id_elem )
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: semestre
#------------------------------------------------------------

CREATE TABLE semestre(
        id_semestre Int NOT NULL ,
        nb_semestre Int ,
        id_filiere  Int NOT NULL ,
        PRIMARY KEY (id_semestre )
)ENGINE=InnoDB;

ALTER TABLE Filiere ADD CONSTRAINT FK_Filiere_id_prof FOREIGN KEY (id_prof) REFERENCES Professeur(id_prof);
ALTER TABLE Module ADD CONSTRAINT FK_Module_id_semestre FOREIGN KEY (id_semestre) REFERENCES semestre(id_semestre);
ALTER TABLE Module ADD CONSTRAINT FK_Module_id_prof FOREIGN KEY (id_prof) REFERENCES Professeur(id_prof);
ALTER TABLE Semaine ADD CONSTRAINT FK_Semaine_id_planing FOREIGN KEY (id_planing) REFERENCES Planing(id_planing);
ALTER TABLE Semaine ADD CONSTRAINT FK_Semaine_id_semestre FOREIGN KEY (id_semestre) REFERENCES semestre(id_semestre);
ALTER TABLE Planing ADD CONSTRAINT FK_Planing_num_semaine FOREIGN KEY (num_semaine) REFERENCES Semaine(num_semaine);
ALTER TABLE Seance ADD CONSTRAINT FK_Seance_id_prof FOREIGN KEY (id_prof) REFERENCES Professeur(id_prof);
ALTER TABLE Seance ADD CONSTRAINT FK_Seance_id_planing FOREIGN KEY (id_planing) REFERENCES Planing(id_planing);
ALTER TABLE Seance ADD CONSTRAINT FK_Seance_id_elem FOREIGN KEY (id_elem) REFERENCES element_module(id_elem);
ALTER TABLE element_module ADD CONSTRAINT FK_element_module_id_module FOREIGN KEY (id_module) REFERENCES Module(id_module);
ALTER TABLE element_module ADD CONSTRAINT FK_element_module_id_prof FOREIGN KEY (id_prof) REFERENCES Professeur(id_prof);
ALTER TABLE semestre ADD CONSTRAINT FK_semestre_id_filiere FOREIGN KEY (id_filiere) REFERENCES Filiere(id_filiere);



insert into Professeur Values (1,'rouimyate','ismail','genie logiciel','developpement','ismail.95r@gmail.com','test');

#-----------------requete1---------------------
select id_prof from Professeur where email LIKE "ismail.95r@gmail.com" and mdp="test"; 
#-----------------requete2---------------------
select * from Professeur where id_prof=(select id_prof from Professeur where email LIKE "ismail.95r@gmail.com" and mdp="test");
select intitule from module where id_prof=(select id_prof from Professeur where email LIKE "ismail.95r@gmail.com" and mdp="test");
select intitule from filiere where id_prof=(select id_prof from Professeur where email LIKE "ismail.95r@gmail.com" and mdp="test"); #-- erreur possible pour celle ci
#----------------requete3----------------------
select * from seance where id_planing=(select id_planing from planing where num_semaine=1) and id_prof=(select id_prof from Professeur where email LIKE "ismail.95r@gmail.com" and mdp="test");

