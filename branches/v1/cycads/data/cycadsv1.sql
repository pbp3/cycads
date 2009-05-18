CREATE TABLE Organism (
  NCBI_TAXON_ID INTEGER UNSIGNED NOT NULL,
  name TEXT NULL,
  Next_Cyc_Id INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(NCBI_TAXON_ID)
)
TYPE=InnoDB;

CREATE TABLE dbxref (
  dbxref_id INTEGER(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  dbname VARCHAR(40) NOT NULL,
  accession VARCHAR(128) NOT NULL,
  PRIMARY KEY(dbxref_id),
  UNIQUE INDEX dbxref_db(dbname, accession)
)
TYPE=InnoDB;

CREATE TABLE biofunction (
  function_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description TEXT NULL,
  PRIMARY KEY(function_id),
  UNIQUE INDEX biofunction_index1983(name)
)
TYPE=InnoDB;

CREATE TABLE annotation_method (
  annotation_method_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  last_weight DECIMAL NULL,
  PRIMARY KEY(annotation_method_id),
  UNIQUE INDEX annotation_method_index1821(name)
)
TYPE=InnoDB;

CREATE TABLE term_type (
  type_id INTEGER(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description TEXT NULL,
  PRIMARY KEY(type_id),
  UNIQUE INDEX name(name),
  INDEX term_type_index1775(name)
)
TYPE=InnoDB;

CREATE TABLE sequence (
  sequence_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  NCBI_TAXON_ID INTEGER UNSIGNED NOT NULL,
  version VARCHAR(32) NOT NULL,
  PRIMARY KEY(sequence_id),
  INDEX sequence_index2009(NCBI_TAXON_ID),
  FOREIGN KEY(NCBI_TAXON_ID)
    REFERENCES Organism(NCBI_TAXON_ID)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE subsequence (
  subsequence_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  sequence_id INTEGER UNSIGNED NOT NULL,
  start_position INTEGER(10) UNSIGNED NOT NULL,
  end_position INTEGER(10) UNSIGNED NOT NULL,
  PRIMARY KEY(subsequence_id),
  INDEX location_start(start_position, end_position),
  FOREIGN KEY(sequence_id)
    REFERENCES sequence(sequence_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE biosequence (
  sequence_id INTEGER UNSIGNED NOT NULL,
  seq LONGTEXT NOT NULL,
  length INTEGER(10) NOT NULL,
  alphabet VARCHAR(10) NULL,
  PRIMARY KEY(sequence_id),
  FOREIGN KEY(sequence_id)
    REFERENCES sequence(sequence_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE Annotation (
  annotation_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  annotation_method_id INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(annotation_id),
  FOREIGN KEY(annotation_method_id)
    REFERENCES annotation_method(annotation_method_id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE sequence_note (
  sequence_id INTEGER UNSIGNED NOT NULL,
  type_id INTEGER(10) UNSIGNED NOT NULL,
  value TEXT NULL,
  FOREIGN KEY(sequence_id)
    REFERENCES sequence(sequence_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(type_id)
    REFERENCES term_type(type_id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE subsequence_note (
  subsequence_id INTEGER UNSIGNED NOT NULL,
  type_id INTEGER(10) UNSIGNED NOT NULL,
  value TEXT NULL,
  FOREIGN KEY(subsequence_id)
    REFERENCES subsequence(subsequence_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(type_id)
    REFERENCES term_type(type_id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE subsequence_synonym (
  subsequence_id INTEGER UNSIGNED NOT NULL,
  dbxref_id INTEGER(10) UNSIGNED NOT NULL,
  PRIMARY KEY(subsequence_id, dbxref_id),
  FOREIGN KEY(subsequence_id)
    REFERENCES subsequence(subsequence_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(dbxref_id)
    REFERENCES dbxref(dbxref_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE sequence_synonym (
  sequence_id INTEGER UNSIGNED NOT NULL,
  dbxref_id INTEGER(10) UNSIGNED NOT NULL,
  PRIMARY KEY(sequence_id, dbxref_id),
  INDEX sequence_dbxref_index1716(dbxref_id),
  FOREIGN KEY(sequence_id)
    REFERENCES sequence(sequence_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(dbxref_id)
    REFERENCES dbxref(dbxref_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE subseq_annotation (
  annotation_id INTEGER UNSIGNED NOT NULL,
  subsequence_id INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(annotation_id),
  FOREIGN KEY(annotation_id)
    REFERENCES Annotation(annotation_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(subsequence_id)
    REFERENCES subsequence(subsequence_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE annotation_parent (
  annotation_id INTEGER UNSIGNED NOT NULL,
  annotation_parent_id INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(annotation_id, annotation_parent_id),
  FOREIGN KEY(annotation_id)
    REFERENCES Annotation(annotation_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(annotation_parent_id)
    REFERENCES Annotation(annotation_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE Annotation_synonym (
  annotation_id INTEGER UNSIGNED NOT NULL,
  dbxref_id INTEGER(10) UNSIGNED NOT NULL,
  PRIMARY KEY(annotation_id, dbxref_id),
  FOREIGN KEY(annotation_id)
    REFERENCES Annotation(annotation_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(dbxref_id)
    REFERENCES dbxref(dbxref_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE annotation_method_note (
  annotation_method_id INTEGER UNSIGNED NOT NULL,
  type_id INTEGER(10) UNSIGNED NOT NULL,
  value TEXT NULL,
  FOREIGN KEY(annotation_method_id)
    REFERENCES annotation_method(annotation_method_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(type_id)
    REFERENCES term_type(type_id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE Annotation_note (
  annotation_id INTEGER UNSIGNED NOT NULL,
  type_id INTEGER(10) UNSIGNED NOT NULL,
  value TEXT NULL,
  FOREIGN KEY(annotation_id)
    REFERENCES Annotation(annotation_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(type_id)
    REFERENCES term_type(type_id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE Annotation_type (
  annotation_id INTEGER UNSIGNED NOT NULL,
  type_id INTEGER(10) UNSIGNED NOT NULL,
  PRIMARY KEY(annotation_id, type_id),
  FOREIGN KEY(annotation_id)
    REFERENCES Annotation(annotation_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(type_id)
    REFERENCES term_type(type_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE dbxref_synonym (
  dbxref_id1 INTEGER(10) UNSIGNED NOT NULL,
  dbxref_id INTEGER(10) UNSIGNED NOT NULL,
  PRIMARY KEY(dbxref_id1, dbxref_id),
  FOREIGN KEY(dbxref_id1)
    REFERENCES dbxref(dbxref_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(dbxref_id)
    REFERENCES dbxref(dbxref_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE function_synonym (
  function_id1 INTEGER UNSIGNED NOT NULL,
  function_id2 INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(function_id1, function_id2),
  FOREIGN KEY(function_id2)
    REFERENCES biofunction(function_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(function_id1)
    REFERENCES biofunction(function_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE dbxref_note (
  dbxref_id INTEGER(10) UNSIGNED NOT NULL,
  type_id INTEGER(10) UNSIGNED NOT NULL,
  value TEXT NULL,
  FOREIGN KEY(dbxref_id)
    REFERENCES dbxref(dbxref_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(type_id)
    REFERENCES term_type(type_id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE dbxref_dbxref_annotation (
  annotation_id INTEGER UNSIGNED NOT NULL,
  dbxref_source INTEGER(10) UNSIGNED NOT NULL,
  dbxref_target INTEGER(10) UNSIGNED NOT NULL,
  PRIMARY KEY(annotation_id),
  FOREIGN KEY(annotation_id)
    REFERENCES Annotation(annotation_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(dbxref_source)
    REFERENCES dbxref(dbxref_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(dbxref_target)
    REFERENCES dbxref(dbxref_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE Intron (
  subsequence_id INTEGER UNSIGNED NOT NULL,
  start_position INTEGER(10) UNSIGNED NOT NULL,
  end_position INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(subsequence_id, start_position),
  FOREIGN KEY(subsequence_id)
    REFERENCES subsequence(subsequence_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE subseq_dbxref_annotation (
  annotation_id INTEGER UNSIGNED NOT NULL,
  dbxref_id INTEGER(10) UNSIGNED NOT NULL,
  PRIMARY KEY(annotation_id),
  FOREIGN KEY(annotation_id)
    REFERENCES subseq_annotation(annotation_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(dbxref_id)
    REFERENCES dbxref(dbxref_id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE subseq_function_annotation (
  annotation_id INTEGER UNSIGNED NOT NULL,
  function_id INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(annotation_id),
  FOREIGN KEY(annotation_id)
    REFERENCES subseq_annotation(annotation_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(function_id)
    REFERENCES biofunction(function_id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
)
TYPE=InnoDB;


