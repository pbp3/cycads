SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `Cycads` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `Cycads`;

-- -----------------------------------------------------
-- Table `Cycads`.`Organism`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Cycads`.`Organism` (
  `ncbi_taxon_id` INT(11) UNSIGNED NOT NULL ,
  `name` TEXT NULL ,
  `Next_Cyc_Id` INT(11) UNSIGNED NOT NULL DEFAULT 1 ,
  PRIMARY KEY (`ncbi_taxon_id`) )
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `Cycads`.`Sequence`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Cycads`.`Sequence` (
  `sequence_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `ncbi_taxon_id` INT(11) UNSIGNED NOT NULL ,
  `version` VARCHAR(32) NOT NULL ,
  PRIMARY KEY (`sequence_id`) ,
  INDEX `sequence_index2009` (`ncbi_taxon_id` ASC) ,
  CONSTRAINT `fk_9aa8589d-ba9d-11de-b920-0014a45de702`
    FOREIGN KEY (`ncbi_taxon_id` )
    REFERENCES `Cycads`.`Organism` (`ncbi_taxon_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `Cycads`.`Biosequence`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Cycads`.`Biosequence` (
  `biosequence_id` INT(11) UNSIGNED NOT NULL ,
  `seq` LONGTEXT NOT NULL ,
  `length` INT(10) NOT NULL ,
  `alphabet` VARCHAR(10) NULL ,
  PRIMARY KEY (`biosequence_id`) ,
  CONSTRAINT `fk_9aa8582d-ba9d-11de-b920-0014a45de702`
    FOREIGN KEY (`biosequence_id` )
    REFERENCES `Cycads`.`Sequence` (`sequence_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `Cycads`.`Term_type`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Cycads`.`Term_type` (
  `type_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  `description` TEXT NULL ,
  PRIMARY KEY (`type_id`) ,
  UNIQUE INDEX `name` (`name` ASC) ,
  INDEX `term_type_index1775` (`name` ASC) )
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `Cycads`.`External_DB`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Cycads`.`External_DB` (
  `external_db_id` INT(10) UNSIGNED NOT NULL ,
  PRIMARY KEY (`external_db_id`) ,
  INDEX `fk_Dbname_Term_type1` (`external_db_id` ASC) ,
  CONSTRAINT `fk_Dbname_Term_type1`
    FOREIGN KEY (`external_db_id` )
    REFERENCES `Cycads`.`Term_type` (`type_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Cycads`.`Dbxref`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Cycads`.`Dbxref` (
  `dbxref_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `accession` VARCHAR(128) NOT NULL ,
  `external_db_id` INT(10) UNSIGNED NOT NULL ,
  PRIMARY KEY (`dbxref_id`) ,
  UNIQUE INDEX `dbxref_db` (`accession` ASC) ,
  INDEX `fk_Dbxref_Dbname1` (`external_db_id` ASC) ,
  UNIQUE INDEX `external_db_accession` (`accession` ASC, `external_db_id` ASC) ,
  CONSTRAINT `fk_Dbxref_Dbname1`
    FOREIGN KEY (`external_db_id` )
    REFERENCES `Cycads`.`External_DB` (`external_db_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `Cycads`.`Subsequence`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Cycads`.`Subsequence` (
  `subsequence_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `sequence_id` INT(11) UNSIGNED NOT NULL ,
  `start_position` INT(10) UNSIGNED NOT NULL ,
  `end_position` INT(10) UNSIGNED NOT NULL ,
  PRIMARY KEY (`subsequence_id`) ,
  INDEX `location_start` (`start_position` ASC, `end_position` ASC) ,
  UNIQUE INDEX `sequence_start_end` (`sequence_id` ASC, `start_position` ASC, `end_position` ASC) ,
  CONSTRAINT `fk_9aa85839-ba9d-11de-b920-0014a45de702`
    FOREIGN KEY (`sequence_id` )
    REFERENCES `Cycads`.`Sequence` (`sequence_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `Cycads`.`Intron`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Cycads`.`Intron` (
  `subsequence_id` INT(11) UNSIGNED NOT NULL ,
  `start_position` INT(10) UNSIGNED NOT NULL ,
  `end_position` INT(11) UNSIGNED NOT NULL ,
  PRIMARY KEY (`subsequence_id`, `start_position`) ,
  CONSTRAINT `fk_9aa85821-ba9d-11de-b920-0014a45de702`
    FOREIGN KEY (`subsequence_id` )
    REFERENCES `Cycads`.`Subsequence` (`subsequence_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `Cycads`.`Source_target_type`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Cycads`.`Source_target_type` (
  `source_target_type_id` INT UNSIGNED NOT NULL ,
  `source_type_id` INT(10) UNSIGNED NOT NULL ,
  `target_type_id` INT(10) UNSIGNED NOT NULL ,
  PRIMARY KEY (`source_target_type_id`) ,
  INDEX `fk_source_target_type_term_type1` (`source_type_id` ASC) ,
  INDEX `fk_source_target_type_term_type2` (`target_type_id` ASC) ,
  CONSTRAINT `fk_source_target_type_term_type1`
    FOREIGN KEY (`source_type_id` )
    REFERENCES `Cycads`.`Term_type` (`type_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_source_target_type_term_type2`
    FOREIGN KEY (`target_type_id` )
    REFERENCES `Cycads`.`Term_type` (`type_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Cycads`.`Association`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Cycads`.`Association` (
  `association_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `source_id` INT(11) UNSIGNED NOT NULL ,
  `target_id` INT(11) UNSIGNED NOT NULL ,
  `source_target_type_id` INT(11) UNSIGNED NOT NULL ,
  PRIMARY KEY (`association_id`) ,
  INDEX `Unique` (`association_id` ASC, `source_id` ASC, `target_id` ASC) ,
  INDEX `fk_Annotation_source_target_type1` (`source_target_type_id` ASC) ,
  CONSTRAINT `fk_Annotation_source_target_type1`
    FOREIGN KEY (`source_target_type_id` )
    REFERENCES `Cycads`.`Source_target_type` (`source_target_type_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `Cycads`.`Method`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Cycads`.`Method` (
  `method_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  PRIMARY KEY (`method_id`) ,
  INDEX `fk_Method_term_type1` (`method_id` ASC) ,
  CONSTRAINT `fk_Method_term_type1`
    FOREIGN KEY (`method_id` )
    REFERENCES `Cycads`.`Term_type` (`type_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `Cycads`.`Biofunction`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Cycads`.`Biofunction` (
  `function_id` INT(10) UNSIGNED NOT NULL ,
  PRIMARY KEY (`function_id`) ,
  INDEX `fk_biofunction_term_type1` (`function_id` ASC) ,
  CONSTRAINT `fk_biofunction_term_type1`
    FOREIGN KEY (`function_id` )
    REFERENCES `Cycads`.`Term_type` (`type_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `Cycads`.`Association_type`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Cycads`.`Association_type` (
  `association_id` INT(11) UNSIGNED NOT NULL ,
  `type_id` INT(10) UNSIGNED NOT NULL ,
  PRIMARY KEY (`association_id`, `type_id`) ,
  CONSTRAINT `fk_9aa858a1-ba9d-11de-b920-0014a45de702`
    FOREIGN KEY (`association_id` )
    REFERENCES `Cycads`.`Association` (`association_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_9aa858a5-ba9d-11de-b920-0014a45de702`
    FOREIGN KEY (`type_id` )
    REFERENCES `Cycads`.`Term_type` (`type_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `Cycads`.`Pathway`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Cycads`.`Pathway` (
  `pathway_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
  PRIMARY KEY (`pathway_id`) )
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `Cycads`.`Reaction`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Cycads`.`Reaction` (
  `reaction_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `ec` INT(10) UNSIGNED NOT NULL ,
  `reversible` TINYINT(1) NOT NULL ,
  PRIMARY KEY (`reaction_id`) ,
  CONSTRAINT `fk_9aa858d9-ba9d-11de-b920-0014a45de702`
    FOREIGN KEY (`ec` )
    REFERENCES `Cycads`.`Dbxref` (`dbxref_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `Cycads`.`Compound`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Cycads`.`Compound` (
  `compound_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `small_molecule` TINYINT(1) NOT NULL ,
  PRIMARY KEY (`compound_id`) )
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `Cycads`.`Pathway_has_reaction`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Cycads`.`Pathway_has_reaction` (
  `pathway_id` INT(11) UNSIGNED NOT NULL ,
  `reaction_id` INT(11) UNSIGNED NOT NULL ,
  PRIMARY KEY (`pathway_id`, `reaction_id`) ,
  CONSTRAINT `fk_9aa858b1-ba9d-11de-b920-0014a45de702`
    FOREIGN KEY (`pathway_id` )
    REFERENCES `Cycads`.`Pathway` (`pathway_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_9aa858b5-ba9d-11de-b920-0014a45de702`
    FOREIGN KEY (`reaction_id` )
    REFERENCES `Cycads`.`Reaction` (`reaction_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `Cycads`.`Reaction_has_compound`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Cycads`.`Reaction_has_compound` (
  `reaction_id` INT(11) UNSIGNED NOT NULL ,
  `compound_id` INT(11) UNSIGNED NOT NULL ,
  `side_a` TINYINT(1) NOT NULL ,
  `quantity` INT(11) UNSIGNED NULL ,
  PRIMARY KEY (`reaction_id`, `compound_id`) ,
  CONSTRAINT `fk_9aa858b9-ba9d-11de-b920-0014a45de702`
    FOREIGN KEY (`reaction_id` )
    REFERENCES `Cycads`.`Reaction` (`reaction_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_9aa858bd-ba9d-11de-b920-0014a45de702`
    FOREIGN KEY (`compound_id` )
    REFERENCES `Cycads`.`Compound` (`compound_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
PACK_KEYS = 0
ROW_FORMAT = DEFAULT;


-- -----------------------------------------------------
-- Table `Cycads`.`Annotation`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Cycads`.`Annotation` (
  `annotation_id` INT(11) UNSIGNED NOT NULL ,
  `annotation_method_id` INT(11) UNSIGNED NOT NULL ,
  `score` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`annotation_id`) ,
  INDEX `fk_Annotation_Association1` (`annotation_id` ASC) ,
  INDEX `fk_Annotation_annotation_method1` (`annotation_method_id` ASC) ,
  CONSTRAINT `fk_Annotation_Association1`
    FOREIGN KEY (`annotation_id` )
    REFERENCES `Cycads`.`Association` (`association_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Annotation_annotation_method1`
    FOREIGN KEY (`annotation_method_id` )
    REFERENCES `Cycads`.`Method` (`method_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Cycads`.`Synonym`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Cycads`.`Synonym` (
  `source_id` INT UNSIGNED NULL ,
  `source_type_id` INT(10) UNSIGNED NOT NULL ,
  `dbxref_id` INT(10) UNSIGNED NOT NULL ,
  PRIMARY KEY (`source_id`, `source_type_id`, `dbxref_id`) ,
  INDEX `fk_synonym_term_type1` (`source_type_id` ASC) ,
  INDEX `fk_synonym_dbxref1` (`dbxref_id` ASC) ,
  CONSTRAINT `fk_synonym_term_type1`
    FOREIGN KEY (`source_type_id` )
    REFERENCES `Cycads`.`Term_type` (`type_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_synonym_dbxref1`
    FOREIGN KEY (`dbxref_id` )
    REFERENCES `Cycads`.`Dbxref` (`dbxref_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Cycads`.`Note`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Cycads`.`Note` (
  `source_id` INT(11) NOT NULL ,
  `source_type_id` INT(10) NOT NULL ,
  `note_type_id` INT(10) NOT NULL ,
  `value` TEXT NOT NULL ,
  INDEX `fk_Note_term_type1` (`source_type_id` ASC) ,
  INDEX `fk_Note_term_type2` (`note_type_id` ASC) ,
  CONSTRAINT `fk_Note_term_type1`
    FOREIGN KEY (`source_type_id` )
    REFERENCES `Cycads`.`Term_type` (`type_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Note_term_type2`
    FOREIGN KEY (`note_type_id` )
    REFERENCES `Cycads`.`Term_type` (`type_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Cycads`.`Feature`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Cycads`.`Feature` (
  `feature_id` INT(10) UNSIGNED NOT NULL ,
  PRIMARY KEY (`feature_id`) ,
  INDEX `fk_Feature_term_type1` (`feature_id` ASC) ,
  CONSTRAINT `fk_Feature_term_type1`
    FOREIGN KEY (`feature_id` )
    REFERENCES `Cycads`.`Term_type` (`type_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

USE `Cycads`;

DELIMITER //
CREATE TRIGGER del_Annotation AFTER DELETE ON Annotation
FOR EACH ROW 
BEGIN
DELETE FROM Association WHERE association_id = OLD.annotation_id;
END
//


DELIMITER ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
