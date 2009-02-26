CREATE TABLE biodatabase (
  biodatabase_id INTEGER(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(128) NOT NULL,
  description TEXT NULL,
  PRIMARY KEY(biodatabase_id),
  UNIQUE INDEX name(name)
)
TYPE=InnoDB;

CREATE TABLE biofunction (
  function_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NULL,
  description TEXT NULL,
  PRIMARY KEY(function_id),
  UNIQUE INDEX biofunction_index1983(name)
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

CREATE TABLE taxon (
  taxon_id INTEGER(10) UNSIGNED NOT NULL,
  ncbi_taxon_id INTEGER(10) NOT NULL,
  parent_taxon_id INTEGER(10) UNSIGNED NOT NULL,
  node_rank VARCHAR(32) NOT NULL,
  genetic_code TINYINT(3) UNSIGNED NOT NULL,
  mito_genetic_code TINYINT(3) UNSIGNED NOT NULL,
  left_value INTEGER(10) UNSIGNED NOT NULL,
  right_value INTEGER(10) UNSIGNED NOT NULL,
  PRIMARY KEY(taxon_id),
  INDEX ncbi_taxon_id(ncbi_taxon_id),
  INDEX left_value(left_value),
  INDEX right_value(right_value),
  INDEX taxparent(parent_taxon_id)
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
  term_type_id INTEGER(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  term_type_parent INTEGER(10) UNSIGNED NOT NULL,
  name VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  PRIMARY KEY(term_type_id),
  UNIQUE INDEX name(name, term_type_parent),
  INDEX term_type_index1775(term_type_parent, name),
  FOREIGN KEY(term_type_parent)
    REFERENCES term_type(term_type_id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE taxon_name (
  taxon_id INTEGER(10) UNSIGNED NOT NULL,
  name VARCHAR(255) NOT NULL,
  name_class VARCHAR(32) NOT NULL,
  PRIMARY KEY(taxon_id, name, name_class),
  INDEX taxon_id(taxon_id, name, name_class),
  INDEX taxnametaxonid(taxon_id),
  INDEX taxnamename(name),
  FOREIGN KEY(taxon_id)
    REFERENCES taxon(taxon_id)
      ON DELETE CASCADE
      ON UPDATE RESTRICT
)
TYPE=InnoDB;

CREATE TABLE Annotation (
  annotation_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  annotation_parent INTEGER UNSIGNED NOT NULL,
  type INTEGER(10) UNSIGNED NOT NULL,
  method INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(annotation_id),
  FOREIGN KEY(type)
    REFERENCES term_type(term_type_id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE,
  FOREIGN KEY(method)
    REFERENCES annotation_method(annotation_method_id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE,
  FOREIGN KEY(annotation_parent)
    REFERENCES Annotation(annotation_id)
      ON DELETE NO ACTION
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE annotation_method_note (
  annotation_method_id INTEGER UNSIGNED NOT NULL,
  note_type INTEGER(10) UNSIGNED NOT NULL,
  value TEXT NULL,
  FOREIGN KEY(annotation_method_id)
    REFERENCES annotation_method(annotation_method_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(note_type)
    REFERENCES term_type(term_type_id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE dbxref_note (
  dbxref_id INTEGER(10) UNSIGNED NOT NULL,
  note_type INTEGER(10) UNSIGNED NOT NULL,
  value TEXT NULL,
  FOREIGN KEY(dbxref_id)
    REFERENCES dbxref(dbxref_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(note_type)
    REFERENCES term_type(term_type_id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE sequence (
  sequence_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  biodatabase_id INTEGER(10) UNSIGNED NOT NULL,
  taxon_id INTEGER(10) UNSIGNED NOT NULL,
  name VARCHAR(40) NOT NULL,
  accession VARCHAR(128) NOT NULL,
  description TEXT NOT NULL,
  version SMALLINT(5) UNSIGNED NOT NULL,
  PRIMARY KEY(sequence_id),
  INDEX accession(accession, biodatabase_id, version),
  INDEX bioentry_name(name),
  INDEX bioentry_db(biodatabase_id),
  INDEX bioentry_tax(taxon_id),
  FOREIGN KEY(biodatabase_id)
    REFERENCES biodatabase(biodatabase_id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE,
  FOREIGN KEY(taxon_id)
    REFERENCES taxon(taxon_id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE sequence_note (
  sequence_id INTEGER UNSIGNED NOT NULL,
  note_type INTEGER(10) UNSIGNED NOT NULL,
  value TEXT NULL,
  FOREIGN KEY(sequence_id)
    REFERENCES sequence(sequence_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(note_type)
    REFERENCES term_type(term_type_id)
      ON DELETE RESTRICT
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

CREATE TABLE dbxref_synonym (
  dbxref_id1 INTEGER(10) UNSIGNED NOT NULL,
  dbxref_id INTEGER(10) UNSIGNED NOT NULL,
  PRIMARY KEY(dbxref_id1, dbxref_id),
  INDEX dbxref_synonym_index1744(dbxref_id),
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

CREATE TABLE subsequence (
  subsequence_id INTEGER UNSIGNED NOT NULL,
  sequence_id INTEGER UNSIGNED NOT NULL,
  position INTEGER(10) UNSIGNED NOT NULL,
  length INTEGER(10) UNSIGNED NOT NULL,
  positive_strand BOOLEAN NOT NULL,
  PRIMARY KEY(subsequence_id),
  INDEX location_start(position, length),
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
  alphabet VARCHAR(10) NOT NULL,
  PRIMARY KEY(sequence_id),
  FOREIGN KEY(sequence_id)
    REFERENCES sequence(sequence_id)
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

CREATE TABLE subsequence_note (
  subsequence_id INTEGER UNSIGNED NOT NULL,
  note_type INTEGER(10) UNSIGNED NOT NULL,
  value TEXT NULL,
  FOREIGN KEY(subsequence_id)
    REFERENCES subsequence(subsequence_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(note_type)
    REFERENCES term_type(term_type_id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE subseq_annotation (
  annotation_id INTEGER UNSIGNED NOT NULL,
  subsequence INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(annotation_id),
  FOREIGN KEY(annotation_id)
    REFERENCES Annotation(annotation_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(subsequence)
    REFERENCES subsequence(subsequence_id)
      ON DELETE CASCADE
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

CREATE TABLE Annotation_note (
  annotation_id INTEGER UNSIGNED NOT NULL,
  note_type INTEGER(10) UNSIGNED NOT NULL,
  value TEXT NULL,
  FOREIGN KEY(annotation_id)
    REFERENCES Annotation(annotation_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(note_type)
    REFERENCES term_type(term_type_id)
      ON DELETE RESTRICT
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

CREATE TABLE Intron (
  subsequence_id INTEGER UNSIGNED NOT NULL,
  position INTEGER(10) UNSIGNED NOT NULL,
  length INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(subsequence_id, position),
  FOREIGN KEY(subsequence_id)
    REFERENCES subsequence(subsequence_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE subseq_function_annotation (
  annotation_id INTEGER UNSIGNED NOT NULL,
  function INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(annotation_id),
  FOREIGN KEY(annotation_id)
    REFERENCES subseq_annotation(annotation_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(function)
    REFERENCES biofunction(function_id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
)
TYPE=InnoDB;

CREATE TABLE subseq_dbxref_annotation (
  annotation_id INTEGER UNSIGNED NOT NULL,
  dbxref INTEGER(10) UNSIGNED NOT NULL,
  PRIMARY KEY(annotation_id),
  FOREIGN KEY(annotation_id)
    REFERENCES subseq_annotation(annotation_id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(dbxref)
    REFERENCES dbxref(dbxref_id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
)
TYPE=InnoDB;

