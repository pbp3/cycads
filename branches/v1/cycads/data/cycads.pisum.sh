time=`date +%H%M`
day=`date +%d%m%y`
dirout="cycads-out/$day/$time"
dirin="cycads-data"
mkdir -p $dirout
jar=cycads.jar
cp $jar $dirout/.
cp config.properties $dirout/.


java -Xmx512M -cp $jar org.cycads.ui.loader.KOLoaderSQL $dirin/ko > $dirout/KOLoaderSQL.out
java -Xmx512M -cp $jar org.cycads.ui.loader.GFF3LoaderSQL $dirin/ACYPI.gff3 7029 AphidBaseS 1.0 > $dirout/GFF3LoaderSQL.out
java -Xmx512M -cp $jar org.cycads.ui.loader.SubseqAnnotationLoaderSQL $dirin/2810_KO_for34821_ACYPI_BBH-subsetEukaryotes 7029 KAAS-subset-eukaryotes 0 AphidBaseP 1 KO -1  > $dirout/SubseqAnnotationLoaderSQL.subset-eukaryotes.out
java -Xmx512M -cp $jar org.cycads.ui.loader.SubseqAnnotationLoaderSQL $dirin/3018_KO_for34821_ACYPI_BBH-RepSet-genes 7029 KAAS-repset-genes 0 AphidBaseP 1 KO -1 > $dirout/SubseqAnnotationLoaderSQL.repset-genes.out
java -Xmx512M -cp $jar org.cycads.ui.loader.SubseqAnnotationLoaderSQL $dirin/3570_KO_for34821_ACYPI_BBH-RepSet-eukaryotes 7029 KAAS-repset-eukaryotes 0 AphidBaseP 1 KO -1  > $dirout/SubseqAnnotationLoaderSQL.repset-eukaryotes.out
java -Xmx512M -cp $jar org.cycads.ui.loader.SubseqAnnotationLoaderSQL $dirin/blast2go_Annot_010409.txt 7029 Blast2Go 0 AphidBaseP 8 "*" 5 > $dirout/SubseqAnnotationLoaderSQL.Blast2Go.out
java -Xmx512M -cp $jar org.cycads.ui.extract.cyc.PFFileGeneratorSQL $dirout/acypi.pf 7029 1.0 n 1.9 1.5 > $dirout/PFFileGeneratorSQL.out

#echo create dblinks in pathwaytools
#AphidBase ---> AphidBase-Gene ---> http://genoweb1.irisa.fr/cgi-bin/gbrowse/gbrowse_details/aphidbase?name=~A
#AphidBaseR ---> AphidBase-mRNA ---> http://genoweb1.irisa.fr/cgi-bin/gbrowse/gbrowse_details/aphidbase?name=~A
#GLEAN ---> AphidBase ---> http://genoweb1.irisa.fr/cgi-bin/gbrowse/gbrowse_details/aphidbase?name=~A
#PhylomeDB ---> PhylomeDB ---> http://phylomedb.org/?seqid=~A
#KO ---> KO ---> http://www.genome.jp/dbget-bin/www_bget?ko+~A
#RefSeqP ---> Refseq-Protein ---> http://www.ncbi.nlm.nih.gov/entrez/viewer.fcgi?val=~A
#RefSeqM ---> Refseq-mRNA ---> http://www.ncbi.nlm.nih.gov/entrez/viewer.fcgi?val=~A

#AphidBaseS ---> AphidBase ---> http://genoweb1.irisa.fr/cgi-bin/gbrowse/gbrowse_details/aphidbase?name=~A
#AphidBaseP ---> PhylomeDB ---> http://phylomedb.org/?seqid=~A

