time=`date +%H%M`
day=`date +%d%m%y`
dirout="test-out/$day/$time"
dirin="data"
mkdir -p $dirout
jar=cycads.jar
cp $jar $dirout/.
othersjar=:biojava.jar:bytecode.jar:mysql-connector-java-5.1.6-bin.jar
cp config.properties $dirout/.
cp "cycads.pisum.sh" $dirout/.


java -Xmx512M -cp $jar$othersjar org.cycads.ui.loader.KOLoader $dirin/ko > $dirout/KOLoaderSQL.out
java -Xmx1024M -cp $jar$othersjar org.cycads.ui.loader.GFF3Loader $dirin/ACYPI.gff3 7029 AphidBaseS 1.0 > $dirout/GFF3LoaderSQL.out
java -Xmx512M -cp $jar$othersjar org.cycads.ui.loader.SubseqDbxrefAnnotationLoader $dirin/2810_KO_for34821_ACYPI_BBH-subsetEukaryotes 7029 KAAS-subset-eukaryotes 0 AphidBaseP 1 KO -1  > $dirout/SubseqAnnotationLoaderSQL.subset-eukaryotes.out
java -Xmx512M -cp $jar$othersjar org.cycads.ui.loader.SubseqDbxrefAnnotationLoader $dirin/3018_KO_for34821_ACYPI_BBH-RepSet-genes 7029 KAAS-repset-genes 0 AphidBaseP 1 KO -1 > $dirout/SubseqAnnotationLoaderSQL.repset-genes.out
java -Xmx512M -cp $jar$othersjar org.cycads.ui.loader.SubseqDbxrefAnnotationLoader $dirin/3570_KO_for34821_ACYPI_BBH-RepSet-eukaryotes 7029 KAAS-repset-eukaryotes 0 AphidBaseP 1 KO -1  > $dirout/SubseqAnnotationLoaderSQL.repset-eukaryotes.out
java -Xmx512M -cp $jar$othersjar org.cycads.ui.loader.SubseqDbxrefAnnotationLoader $dirin/blast2go_Annot_010409.txt 7029 Blast2Go-EC 0 AphidBaseP 8 "*" -1 > $dirout/SubseqAnnotationLoaderSQL.Blast2Go.out
java -Xmx512M -cp $jar$othersjar org.cycads.ui.loader.SubseqDbxrefAnnotationLoader $dirin/acypi_PRIAM_seqEC.txt 7029 PRIAM 0 AphidBaseP 1 EC -1 > $dirout/SubseqAnnotationLoaderSQL.PRIAM.out
java -Xmx512M -cp $jar$othersjar org.cycads.ui.tools.CleanColumn $dirin/Aphid_transferred_annotation_phylomescore.tdf 6 '\t' '#' '.*\[|\]' $dirin/ACYPI_PhylomeDB > $dirout/CleanColumn.out
java -Xmx512M -cp $jar$othersjar org.cycads.ui.loader.SubseqDbxrefAnnotationLoader $dirin/ACYPI_PhylomeDB 7029 PhylomeDB 0 AphidBaseP 6 GO 7 2 > $dirout/SubseqAnnotationLoaderSQL.PhylomeDB.out
java -Xmx2048M -cp $jar$othersjar org.cycads.ui.extract.cyc.PFFileGenerator $dirout/acypi1.2.pf 7029 '*' 1.0 n 2.0 2.0 > $dirout/PFFileGeneratorSQL.out

#echo create dblinks in pathwaytools
#AphidBaseG ---> AphidBase-GeneReport ---> http://webapps1.genouest.org/grs-1.6/grs?reportID=aphidbase_genome_report&objectID=~A
#AphidBaseR ---> AphidBase-GBrowse ---> http://genoweb1.irisa.fr/cgi-bin/gbrowse/gbrowse_details/aphidbase?name=~A
#GLEAN ---> AphidBase ---> http://genoweb1.irisa.fr/cgi-bin/gbrowse/gbrowse_details/aphidbase?name=~A
#PhylomeDB ---> PhylomeDB ---> http://phylomedb.org/?seqid=~A
#KO ---> KO ---> http://www.genome.jp/dbget-bin/www_bget?ko+~A
#RefSeqP ---> Refseq-Protein ---> http://www.ncbi.nlm.nih.gov/entrez/viewer.fcgi?val=~A
#RefSeqM ---> Refseq-mRNA ---> http://www.ncbi.nlm.nih.gov/entrez/viewer.fcgi?val=~A
#GO_LOW ---> Gene Ontology Database ---> GO ---> http://www.geneontology.org/ ---> http://amigo.geneontology.org/cgi-bin/amigo/go.cgi?query=~A&search_constraint=terms&action=query&view=query

#AphidBaseS ---> AphidBase ---> http://genoweb1.irisa.fr/cgi-bin/gbrowse/gbrowse_details/aphidbase?name=~A
#AphidBaseP ---> PhylomeDB ---> http://phylomedb.org/?seqid=~A

