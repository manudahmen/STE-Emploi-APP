-- phpMyAdmin SQL Dump
-- version 2.10.1
-- http://www.phpmyadmin.net
-- 
-- Serveur: localhost
-- Généré le : Jeu 28 Mai 2009 à 09:32
-- Version du serveur: 5.0.41
-- Version de PHP: 5.2.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

-- 
-- Base de données: `steemploi`
-- 

-- --------------------------------------------------------

-- 
-- Structure de la table `attendee`
-- 

CREATE TABLE `attendee` (
  `id` int(11) NOT NULL auto_increment,
  `evt_id` int(11) NOT NULL,
  `table_name` varchar(100) default NULL,
  `role` int(11) default NULL,
  `type` int(11) default NULL,
  `value` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;

-- 
-- Contenu de la table `attendee`
-- 


-- --------------------------------------------------------

-- 
-- Structure de la table `codes_categories_taches`
-- 

CREATE TABLE `codes_categories_taches` (
  `id` int(11) unsigned NOT NULL auto_increment,
  `code` varchar(3) default NULL,
  `title` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=38 ;

-- 
-- Contenu de la table `codes_categories_taches`
-- 

INSERT INTO `codes_categories_taches` (`id`, `code`, `title`) VALUES 
(22, '1a', 'envoi de mail'),
(23, '1b', 'envoi de courier'),
(24, '1c', 'remplissage d''un formulaire'),
(25, '1c1', 'cv en ligne'),
(26, '1d', 'envoi de mail pour un stage'),
(27, '1e', 'envoi de courier pour un stage'),
(28, '2', 'r&eacute;ponse positive'),
(29, '3', 'r&eacute;ponse n&eacute;gative'),
(30, '4', 'relance t&eacute;l&eacute;phonqiue'),
(31, '5', 'entretien technique'),
(32, '5b', 'deuxi&egrave;me entretien'),
(33, '5c', 'n&eacute;gociation'),
(34, '6', 'autres d&eacute;marches'),
(35, '7', 'proposition de contrat'),
(36, '8', 'signature du contrat'),
(37, '9', 'envoie preuve de contrat');

-- --------------------------------------------------------

-- 
-- Structure de la table `commentaires`
-- 

CREATE TABLE `commentaires` (
  `id` int(11) NOT NULL,
  `owner` int(11) default NULL,
  `texte` varchar(1024) default NULL,
  `table_ref` varchar(128) NOT NULL default 'entreprise',
  PRIMARY KEY  (`id`),
  KEY `commentaires_owner_idx` (`owner`)
) ENGINE=InnoDB ;

-- 
-- Contenu de la table `commentaires`
-- 


-- --------------------------------------------------------

-- 
-- Structure de la table `conge`
-- 

CREATE TABLE `conge` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) default NULL,
  `date_debut` datetime default NULL,
  `date_fin` datetime default NULL,
  `sessions_formations_id` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;

-- 
-- Contenu de la table `conge`
-- 


-- --------------------------------------------------------

-- 
-- Structure de la table `contacts_entreprises`
-- 

CREATE TABLE `contacts_entreprises` (
  `id` int(11) NOT NULL auto_increment,
  `nom` varchar(50) default NULL,
  `prenom` varchar(50) default NULL,
  `tel` varchar(10) default NULL,
  `gsm` varchar(10) default NULL,
  `rue` varchar(200) default NULL,
  `numero` varchar(200) default NULL,
  `boite` varchar(5) default NULL,
  `ville` varchar(100) default NULL,
  `codepostal` varchar(5) default NULL,
  `pays` varchar(50) default NULL,
  `url` varchar(100) default NULL,
  `email` varchar(100) default NULL,
  `entreprise_id` int(11) default NULL,
  `owner` int(11) default NULL,
  `infocomplementaires` varchar(1024) default NULL,
  `commentaires` varchar(1024) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;

-- 
-- Contenu de la table `contacts_entreprises`
-- 


-- --------------------------------------------------------

-- 
-- Structure de la table `coordinatrice`
-- 

CREATE TABLE `coordinatrice` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` int(11) default NULL,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;

-- 
-- Contenu de la table `coordinatrice`
-- 


-- --------------------------------------------------------

-- 
-- Structure de la table `dates`
-- 

CREATE TABLE `dates` (
  `id` int(11) NOT NULL auto_increment,
  `evt_id` int(11) NOT NULL,
  `name` varchar(255) default NULL,
  `has2values` tinyint(1) default NULL,
  `date1` datetime default NULL,
  `date2` datetime default NULL,
  `parent` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;

-- 
-- Contenu de la table `dates`
-- 


-- --------------------------------------------------------

-- 
-- Structure de la table `echeances`
-- 

CREATE TABLE `echeances` (
  `id` int(11) NOT NULL auto_increment,
  `description` varchar(1024) default NULL,
  `title` varchar(256) default NULL,
  `date` datetime default NULL,
  `code` varchar(5) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=95 ;

-- 
-- Contenu de la table `echeances`
-- 

INSERT INTO `echeances` (`id`, `description`, `title`, `date`, `code`) VALUES 
(48, 'DESCRIPTION<br>Texte<br>Texte', 'Title', '2009-04-30 13:09:11', 'OCE'),
(49, 'DESCRIPTION<br>Texte<br>Texte', 'Title', '2009-05-08 13:24:48', ''),
(50, 'DESCRIPTION<br>Texte<br>Texte', 'Title', '2009-05-09 13:24:48', 'OCE'),
(51, 'DESCRIPTION<br>Texte<br>Texte', 'Title', '2009-05-09 13:24:48', 'OCE'),
(52, 'DESCRIPTION<br>Texte<br>Texte', 'Title', '2009-05-02 14:12:52', 'OCE'),
(53, 'DESCRIPTION<br>Texte<br>Texte', 'Title', '2009-05-02 14:14:54', 'OCE'),
(54, 'DESCRIPTION<br>Texte<br>Texte', 'Title', '2009-05-01 14:17:20', 'OCE'),
(55, 'DESCRIPTION<br>Texte<br>Texte', 'Title', '2009-05-01 14:24:45', 'OCE'),
(56, 'DESCRIPTION<br>Texte<br>Texte', 'Title', '2009-05-03 14:27:34', ''),
(57, 'DESCRIPTION<br>Texte<br>Texte', 'Title', '2009-05-03 14:30:18', ''),
(58, 'DESCRIPTION<br>Texte<br>Texte', 'Title', '2009-05-15 14:30:18', ''),
(59, 'DESCRIPTION<br>Texte<br>Texte', 'Title', '2009-05-22 14:30:18', ''),
(60, 'DESCRIPTION<br>Texte<br>Texte', 'Title', '2009-05-07 14:33:21', ''),
(61, 'DESCRIPTION<br>Texte<br>Texte', 'Title', '2009-05-12 14:35:06', ''),
(62, 'DESCRIPTION<br>Texte<br>Texte', 'Title', '2009-04-27 00:37:08', 'CO'),
(63, 'DESCRIPTION<br>Texte<br>Texte', 'Title', '2009-04-12 23:25:00', 'CO'),
(64, 'DESCRIPTION<br>Texte<br>Texte', 'TEST INSERT', '2009-04-30 05:28:26', 'CO'),
(65, 'DESCRIPTION<br>Texte<br>Texte', 'k', '2009-04-30 05:30:38', 'k'),
(66, 'DESCRIPTION<br>Texte<br>Texte', 'k', '2009-04-30 05:30:38', 'k'),
(67, 'DESCRIPTION<br>Texte<br>Texte', 'k', '2009-04-30 05:35:14', 'k'),
(68, 'DESCRIPTION<br>Texte<br>Texte', 'k', '2009-04-30 05:35:14', 'k'),
(69, 'DESCRIPTION<br>Texte<br>Texte', 'k', '2009-04-30 05:35:14', 'k'),
(70, 'DESCRIPTION<br>Texte<br>Texte', 'Titre Titre Titre', '2009-04-30 05:35:14', 'k'),
(71, 'DESCRIPTION<br>Texte<br>Texte', 'k', '2009-04-30 05:44:45', 'k'),
(72, 'DESCRIPTION<br>Texte<br>Texte', 'k', '2009-04-30 05:44:45', 'k'),
(73, 'DESCRIPTION<br>Texte<br>Texte', 'k', '2009-04-30 05:48:20', 'k'),
(74, 'DESCRIPTION<br>Texte<br>Texte', 'k', '2009-04-30 05:50:44', 'k'),
(75, 'DESCRIPTION<br>Texte<br>Texte', 'k', '2009-04-30 05:50:44', 'k'),
(76, 'DESCRIPTION<br>Texte<br>Texte', 'k', '2009-04-30 05:50:44', 'k'),
(77, 'DESCRIPTION<br>Texte<br>Texte', 'k', '2009-04-30 05:50:44', 'k'),
(78, 'DESCRIPTION<br>Texte<br>Texte', 'k', '2009-04-30 05:58:36', 'k'),
(79, 'DESCRIPTION<br>Texte<br>Texte', 'k', '2009-04-30 06:03:35', 'k'),
(80, 'DESCRIPTION<br>Texte<br>Texte', 'k', '2009-05-01 06:05:35', 'k'),
(81, 'DESCRIPTION<br>Texte<br>Texte', 'k', '2009-05-01 06:05:35', 'k'),
(82, 'DESCRIPTION<br>Texte<br>Texte', 'kkkkkk kkkkk', '2009-05-15 06:45:50', 'kk'),
(83, 'null', 'TEst Patrick Java 2009', '2009-05-02 17:52:27', 'CO'),
(84, 'null', 'TEst Patrick Java 2009', '2009-05-02 21:16:47', 'cd'),
(85, 'null', 'TITRE', '2009-04-30 10:12:25', 'CO'),
(86, 'null', 'ddd', '2009-10-25 10:37:22', 'cc'),
(87, 'null', 'title', '2009-06-21 11:44:40', 'cc'),
(88, 'null', 'title', '2009-05-29 11:47:00', 'cd'),
(89, 'null', 'ddjdkkd', '2009-06-02 12:06:19', 'cd'),
(90, 'null', 'ttilrre', '2009-06-02 12:17:45', 'cd'),
(91, 'null', 'tt', '2009-06-04 16:20:10', 'dd'),
(92, 'null', 'tt', '2009-06-03 16:31:13', 'xx'),
(93, 'fmsgvmdsnfml', 'ccc', '2009-12-17 09:35:06', 'cc'),
(94, 'Description', 'Titre MD', '2009-05-30 00:00:00', 'CC');

-- --------------------------------------------------------

-- 
-- Structure de la table `echeances_etudiants`
-- 

CREATE TABLE `echeances_etudiants` (
  `etudiant_id` int(11) default NULL,
  `echeance_id` int(11) default NULL,
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`),
  KEY `echeances_etudiants_etudiant_id_idx` (`etudiant_id`,`echeance_id`)
) ENGINE=InnoDB  AUTO_INCREMENT=142 ;

-- 
-- Contenu de la table `echeances_etudiants`
-- 

INSERT INTO `echeances_etudiants` (`etudiant_id`, `echeance_id`, `id`) VALUES 
(1, 0, 21),
(1, 0, 26),
(1, 0, 33),
(1, 0, 43),
(1, 0, 51),
(1, 61, 57),
(1, 64, 88),
(1, 67, 96),
(1, 70, 106),
(1, 79, 118),
(1, 80, 126),
(1, 81, 134),
(2, 0, 23),
(2, 0, 35),
(2, 0, 45),
(2, 0, 53),
(2, 61, 59),
(2, 64, 90),
(2, 67, 98),
(2, 70, 108),
(2, 79, 120),
(2, 80, 128),
(2, 81, 136),
(3, 0, 19),
(3, 0, 24),
(3, 0, 31),
(3, 0, 41),
(3, 0, 49),
(3, 61, 55),
(3, 64, 92),
(3, 67, 100),
(3, 70, 110),
(3, 79, 122),
(3, 80, 130),
(3, 81, 138),
(4, 0, 20),
(4, 0, 25),
(4, 0, 32),
(4, 0, 42),
(4, 0, 50),
(4, 61, 56),
(4, 64, 87),
(4, 67, 95),
(4, 70, 105),
(4, 79, 117),
(4, 80, 125),
(4, 81, 133),
(5, 0, 22),
(5, 0, 27),
(5, 0, 34),
(5, 0, 44),
(5, 0, 52),
(5, 61, 58),
(5, 62, 80),
(5, 64, 89),
(5, 67, 97),
(5, 70, 107),
(5, 79, 119),
(5, 80, 127),
(5, 81, 135),
(6, 0, 18),
(6, 0, 30),
(6, 0, 40),
(6, 0, 48),
(6, 61, 54),
(6, 64, 91),
(6, 67, 99),
(6, 70, 109),
(6, 79, 121),
(6, 80, 129),
(6, 81, 137),
(7, 0, 28),
(7, 0, 36),
(7, 0, 38),
(7, 0, 46),
(7, 62, 78),
(7, 63, 82),
(7, 64, 84),
(7, 64, 86),
(7, 66, 94),
(7, 67, 102),
(7, 70, 104),
(7, 71, 112),
(7, 75, 114),
(7, 79, 116),
(7, 80, 124),
(7, 81, 132),
(7, 82, 140),
(7, 83, 141),
(8, 0, 29),
(8, 0, 37),
(8, 0, 39),
(8, 0, 47),
(8, 62, 79),
(8, 63, 81),
(8, 64, 83),
(8, 64, 85),
(8, 66, 93),
(8, 67, 101),
(8, 70, 103),
(8, 71, 111),
(8, 75, 113),
(8, 79, 115),
(8, 80, 123),
(8, 81, 131),
(8, 82, 139);

-- --------------------------------------------------------

-- 
-- Structure de la table `echeances_formations`
-- 

CREATE TABLE `echeances_formations` (
  `echeance_id` int(11) default NULL,
  `formation_id` int(11) default NULL,
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`),
  KEY `echeances_formations_echeance_id_idx` (`echeance_id`),
  KEY `echeances_formations_formation_id_idx` (`formation_id`)
) ENGINE=InnoDB  AUTO_INCREMENT=205 ;

-- 
-- Contenu de la table `echeances_formations`
-- 

INSERT INTO `echeances_formations` (`echeance_id`, `formation_id`, `id`) VALUES 
(65, 2, 1),
(68, 2, 2),
(68, 1, 3),
(69, 1, 4),
(69, 2, 5),
(72, 1, 6),
(73, 1, 7),
(73, 2, 8),
(74, 1, 9),
(74, 2, 10),
(76, 1, 11),
(76, 2, 12),
(77, 1, 13),
(77, 2, 14),
(78, 1, 15),
(78, 2, 16),
(84, 1, 17),
(85, 2, 18),
(86, 1, 169),
(86, 2, 170),
(86, 3, 171),
(86, 4, 172),
(87, 1, 181),
(87, 2, 182),
(87, 3, 183),
(87, 4, 184),
(91, 1, 195),
(91, 2, 196),
(91, 3, 197),
(91, 4, 198),
(91, 5, 199),
(92, 1, 200),
(92, 2, 201),
(92, 3, 202),
(93, 7, 203),
(94, 10, 204);

-- --------------------------------------------------------

-- 
-- Structure de la table `echeance_attendee`
-- 

CREATE TABLE `echeance_attendee` (
  `id` int(11) NOT NULL,
  `echeance_id` int(11) default NULL,
  `etudiant_id` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `echeance_attendee_echeance_id_idx` (`echeance_id`),
  KEY `echeance_attendee_etudiant_id_idx` (`etudiant_id`)
) ENGINE=InnoDB ;

-- 
-- Contenu de la table `echeance_attendee`
-- 


-- --------------------------------------------------------

-- 
-- Structure de la table `entreprise`
-- 

CREATE TABLE `entreprise` (
  `id` int(11) NOT NULL auto_increment,
  `nom` varchar(255) NOT NULL,
  `adresse2` varchar(255) default NULL,
  `owner` int(11) default NULL,
  `url` varchar(255) default 'http://',
  `rue` varchar(100) default NULL,
  `numero` varchar(5) default NULL,
  `boite` varchar(5) default NULL,
  `codepostal` varchar(5) default NULL,
  `ville` varchar(100) default NULL,
  `pays` varchar(100) default NULL,
  `commentaires` varchar(1024) default NULL,
  `infocomplementaires` varchar(1024) default NULL,
  `email` varchar(100) default NULL,
  `secteur` varchar(512) default NULL,
  `tel` varchar(12) default NULL,
  `mobile` varchar(12) default NULL,
  PRIMARY KEY  (`id`),
  KEY `entreprise_owner_idx` (`owner`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;

-- 
-- Contenu de la table `entreprise`
-- 


-- --------------------------------------------------------

-- 
-- Structure de la table `etudiants`
-- 

CREATE TABLE `etudiants` (
  `nom` varchar(100) default NULL,
  `prenom` varchar(100) default NULL,
  `email` varchar(150) default NULL,
  `user_id` int(11) default NULL,
  `id` int(11) NOT NULL auto_increment,
  `sessions_formations_id` int(11) default NULL,
  `formation_id` int(11) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  KEY `etudiants_email_idx` (`email`),
  KEY `etudiants_nom_idx` (`nom`)
) ENGINE=InnoDB  AUTO_INCREMENT=22 ;

-- 
-- Contenu de la table `etudiants`
-- 

INSERT INTO `etudiants` (`nom`, `prenom`, `email`, `user_id`, `id`, `sessions_formations_id`, `formation_id`) VALUES 
('Tnom', 'tpr‚nom', 'email@example.com', NULL, 1, 1, NULL),
('Tnom', 'tpr‚nom', 'email@example.com', NULL, 2, 2, NULL),
('Tnom', 'tpr‚nom', 'email@example.com', NULL, 3, 3, NULL),
('Tnom', 'tpr‚nom', 'email@example.com', NULL, 4, 4, NULL),
('Tnom', 'tpr‚nom', 'email@example.com', NULL, 5, 5, NULL),
('Tnom', 'tpr‚nom', 'email@example.com', NULL, 6, 6, NULL),
('Tnom', 'tpr‚nom', 'email@example.com', NULL, 7, 7, NULL),
('Tnom', 'tpr‚nom', 'email@example.com', NULL, 8, 8, NULL),
('Tnom', 'tpr‚nom', 'email@example.com', NULL, 9, 9, NULL),
('Tnom', 'tpr‚nom', 'email@example.com', NULL, 10, 10, NULL),
('Tnom', 'tpr‚nom', 'email@example.com', NULL, 11, 11, NULL),
('Tnom', 'tpr‚nom', 'email@example.com', NULL, 12, 12, NULL),
('Tnom', 'tpr‚nom', 'email@example.com', NULL, 13, 13, NULL),
('Tnom', 'tpr‚nom', 'email@example.com', NULL, 14, 14, NULL),
('Tnom', 'tpr‚nom', 'email@example.com', NULL, 15, 15, NULL),
('Tnom', 'tpr‚nom', 'email@example.com', NULL, 16, 16, NULL),
('Tnom', 'tpr‚nom', 'email@example.com', NULL, 17, 17, NULL),
('Tnom', 'tpr‚nom', 'email@example.com', NULL, 18, 18, NULL),
('Tnom', 'tpr‚nom', 'email@example.com', NULL, 19, 19, NULL),
('Tnom', 'tpr‚nom', 'email@example.com', NULL, 20, 20, NULL),
('Tnom', 'tpr‚nom', 'email@example.com', NULL, 21, 21, NULL);

-- --------------------------------------------------------

-- 
-- Structure de la table `etudiants_formations`
-- 

CREATE TABLE `etudiants_formations` (
  `id` int(11) default NULL,
  `etudiant_id` int(11) default NULL,
  `formation_id` int(11) default NULL
) ENGINE=InnoDB ;

-- 
-- Contenu de la table `etudiants_formations`
-- 


-- --------------------------------------------------------

-- 
-- Structure de la table `evenement`
-- 

CREATE TABLE `evenement` (
  `id` int(11) NOT NULL auto_increment,
  `dateDebut` date NOT NULL,
  `dateFin` date NOT NULL,
  `nom` varchar(255) NOT NULL,
  `infossupp` varchar(2048) NOT NULL,
  `description` varchar(2048) NOT NULL,
  `link` varchar(255) default NULL,
  `type` int(11) NOT NULL,
  `ref` int(11) default NULL,
  `createur` varchar(255) NOT NULL,
  `destinataire` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;

-- 
-- Contenu de la table `evenement`
-- 


-- --------------------------------------------------------

-- 
-- Structure de la table `evenement_attendee`
-- 

CREATE TABLE `evenement_attendee` (
  `id` int(11) NOT NULL auto_increment,
  `evenement_id` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `etudiant_id` int(11) default NULL,
  `formateur_id` int(11) default NULL,
  `coordinatrice_id` int(11) default NULL,
  `session_formation_id` int(11) default NULL,
  `formation_id` int(11) default NULL,
  `entreprise_id` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;

-- 
-- Contenu de la table `evenement_attendee`
-- 


-- --------------------------------------------------------

-- 
-- Structure de la table `evenement_t_all_commentaire`
-- 

CREATE TABLE `evenement_t_all_commentaire` (
  `id` int(11) NOT NULL auto_increment,
  `evt_id` int(11) NOT NULL,
  `commentaire_id` int(11) NOT NULL,
  `etudiant_id` int(11) NOT NULL,
  `commentaire` varchar(2048) NOT NULL,
  `destinataire` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;

-- 
-- Contenu de la table `evenement_t_all_commentaire`
-- 


-- --------------------------------------------------------

-- 
-- Structure de la table `evenement_t_entreprise_langage`
-- 

CREATE TABLE `evenement_t_entreprise_langage` (
  `id` int(11) NOT NULL auto_increment,
  `evt_id` int(11) NOT NULL,
  `langage_id` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;

-- 
-- Contenu de la table `evenement_t_entreprise_langage`
-- 


-- --------------------------------------------------------

-- 
-- Structure de la table `evts`
-- 

CREATE TABLE `evts` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `description` varchar(2048) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;

-- 
-- Contenu de la table `evts`
-- 


-- --------------------------------------------------------

-- 
-- Structure de la table `evts_table_conge`
-- 

CREATE TABLE `evts_table_conge` (
  `id` int(11) NOT NULL auto_increment,
  `id_reference` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;

-- 
-- Contenu de la table `evts_table_conge`
-- 


-- --------------------------------------------------------

-- 
-- Structure de la table `evts_table_jpo`
-- 

CREATE TABLE `evts_table_jpo` (
  `id` int(11) NOT NULL auto_increment,
  `id_reference` int(11) default NULL,
  `infos` varchar(1024) default NULL,
  `link` varchar(1024) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;

-- 
-- Contenu de la table `evts_table_jpo`
-- 


-- --------------------------------------------------------

-- 
-- Structure de la table `formateur`
-- 

CREATE TABLE `formateur` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` int(11) default NULL,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;

-- 
-- Contenu de la table `formateur`
-- 


-- --------------------------------------------------------

-- 
-- Structure de la table `formations`
-- 

CREATE TABLE `formations` (
  `nom` varchar(255) default NULL,
  `annee` int(11) default NULL,
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=7 ;

-- 
-- Contenu de la table `formations`
-- 

INSERT INTO `formations` (`nom`, `annee`, `id`) VALUES 
('PowerBuilder', NULL, 1),
('Internet', NULL, 2),
('Java', NULL, 3),
('Web Components sous Linux', NULL, 4),
('Introduction … la programmation', NULL, 5),
('Optimiser sa recherche d''emploi grƒce … internet', NULL, 6);

-- --------------------------------------------------------

-- 
-- Structure de la table `jpo`
-- 

CREATE TABLE `jpo` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(100) default NULL,
  `date_debut` datetime default NULL,
  `date_fin` datetime default NULL,
  `description` varchar(2048) default NULL,
  `link` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=3 ;

-- 
-- Contenu de la table `jpo`
-- 

INSERT INTO `jpo` (`id`, `name`, `date_debut`, `date_fin`, `description`, `link`) VALUES 
(1, 'JPO 2009', '2009-05-31 00:00:00', NULL, 'JPO 2009 STE-Formations Informatiques', NULL),
(2, 'JPO 2010', '2009-05-25 00:00:00', '2009-05-25 00:00:00', '<p>KBBVJB? KBJKJB JK BKJBK BJKB</p>', NULL);

-- --------------------------------------------------------

-- 
-- Structure de la table `presentation_entreprise`
-- 

CREATE TABLE `presentation_entreprise` (
  `id` int(11) NOT NULL auto_increment,
  `nom` varchar(100) NOT NULL,
  `date` date NOT NULL,
  `telephone` varchar(12) NOT NULL,
  `email` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `description` varchar(1024) NOT NULL,
  `infoscomplementaires` varchar(1024) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;

-- 
-- Contenu de la table `presentation_entreprise`
-- 


-- --------------------------------------------------------

-- 
-- Structure de la table `sessions`
-- 

CREATE TABLE `sessions` (
  `user_id` int(11) default NULL,
  `dateStart` bigint(20) default NULL,
  `id` int(11) NOT NULL auto_increment,
  `session` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `session` (`session`)
) ENGINE=InnoDB  AUTO_INCREMENT=11 ;

-- 
-- Contenu de la table `sessions`
-- 

INSERT INTO `sessions` (`user_id`, `dateStart`, `id`, `session`) VALUES 
(2, 1243347200533, 1, 4014513251184189903),
(2, 1243347262008, 2, 2757955314366313619),
(2, 1243347338309, 3, 3737047833213352973),
(2, 1243347492167, 4, 6155336189476573491),
(1, 1243453092393, 5, 4178194045853398439),
(1, 1243453437760, 6, 7744572977394846510),
(1, 1243454147334, 7, 7658421440341152520),
(1, 1243454196624, 8, 6693123989920632579),
(1, 1243454641216, 9, 8349589953567070082),
(1, 1243487333917, 10, 4292661343863051528);

-- --------------------------------------------------------

-- 
-- Structure de la table `sessions_formations`
-- 

CREATE TABLE `sessions_formations` (
  `id` int(11) NOT NULL auto_increment,
  `formation_id` int(11) default NULL,
  `dateStart` date default NULL,
  `dateEnd` date default NULL,
  `name` varchar(100) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=22 ;

-- 
-- Contenu de la table `sessions_formations`
-- 

INSERT INTO `sessions_formations` (`id`, `formation_id`, `dateStart`, `dateEnd`, `name`) VALUES 
(1, 1, '2008-09-01', '2009-03-21', '2008'),
(2, 1, '2009-09-01', '2010-03-21', '2009'),
(3, 2, '2008-03-10', '2008-10-21', '2008'),
(4, 2, '2009-03-10', '2009-10-21', '2009'),
(5, 2, '2010-03-10', '2010-10-21', '2010'),
(6, 1, '2010-09-01', '2011-03-21', '2010'),
(7, 3, '2008-04-10', '2008-06-21', '2008'),
(8, 3, '2009-04-10', '2009-06-21', '2009'),
(9, 3, '2010-04-10', '2010-06-21', '2010'),
(10, 4, '2008-01-29', '2008-07-29', '2008'),
(11, 4, '2009-01-29', '2009-07-29', '2009'),
(12, 4, '2010-01-29', '2010-07-29', '2010'),
(13, 5, '2008-01-29', '0000-00-00', '2008-1'),
(14, 5, '2008-07-29', '2010-08-29', '2008-2'),
(15, 5, '2009-01-29', '0000-00-00', '2009-1'),
(16, 5, '2009-06-29', '2009-07-29', '2009-2'),
(17, 6, '2009-04-29', '2009-05-10', '2009-1'),
(18, 6, '2009-05-29', '2009-06-10', '2009-2'),
(19, 6, '2009-06-29', '2009-07-10', '2009-3'),
(20, 6, '2009-08-29', '2009-09-10', '2009-4'),
(21, 6, '2009-11-29', '2009-11-10', '2009-5');

-- --------------------------------------------------------

-- 
-- Structure de la table `taches`
-- 

CREATE TABLE `taches` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `title` varchar(255) NOT NULL default 'Candidature',
  `code` int(11) NOT NULL default '1',
  `entreprise_id` int(11) NOT NULL default '-1',
  `contact_id` int(11) NOT NULL default '-1',
  `dateCompleted` datetime default NULL,
  `description` varchar(1024) default NULL,
  `etudiant_id` int(11) default NULL,
  `owner` int(11) default '-1',
  `percent` tinyint(4) default NULL,
  `infocomplementaires` varchar(1024) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 ;

-- 
-- Contenu de la table `taches`
-- 


-- --------------------------------------------------------

-- 
-- Structure de la table `utilisateurs`
-- 

CREATE TABLE `utilisateurs` (
  `usertype` varchar(20) default NULL,
  `username` varchar(30) default NULL,
  `password` varchar(50) default NULL,
  `email` varchar(100) default NULL,
  `id` int(11) NOT NULL auto_increment,
  `gsm` varchar(12) default NULL,
  `tel` varchar(12) default NULL,
  `rue` varchar(100) default NULL,
  `numero` varchar(6) default NULL,
  `boite` varchar(6) default NULL,
  `codepostal` varchar(4) default NULL,
  `ville` varchar(50) default NULL,
  `commentaires` varchar(1024) default NULL,
  `pays` varchar(3) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=3 ;

-- 
-- Contenu de la table `utilisateurs`
-- 

INSERT INTO `utilisateurs` (`usertype`, `username`, `password`, `email`, `id`, `gsm`, `tel`, `rue`, `numero`, `boite`, `codepostal`, `ville`, `commentaires`, `pays`) VALUES 
('formateur', 'isabelle', '*BDF28397A9637014CD3DE51A5543E11CAB348335', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('etudiant', 'manu', '*18245C5EA7E8A33ACB97A18A68A38E9BBD653E85', NULL, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

-- 
-- Doublure de structure pour la vue `view_formation`
-- 
CREATE TABLE `view_formation` (
`echeance_id` int(11)
,`title` varchar(256)
,`description` varchar(1024)
,`code` varchar(5)
,`echeance_date` datetime
,`formation_id` int(11)
);
-- --------------------------------------------------------

-- 
-- Structure de la vue `view_formation`
-- 
DROP TABLE IF EXISTS `view_formation`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `steemploi`.`view_formation` AS (select `e`.`id` AS `echeance_id`,`e`.`title` AS `title`,`e`.`description` AS `description`,`e`.`code` AS `code`,`e`.`date` AS `echeance_date`,`ef`.`id` AS `formation_id` from (`steemploi`.`echeances` `e` join `steemploi`.`echeances_formations` `ef` on((`ef`.`echeance_id` = `e`.`id`))));
