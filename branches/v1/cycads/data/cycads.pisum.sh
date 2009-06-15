java -Xmx512M -cp cycads.jar org.cycads.ui.loader.KOLoaderSQL /home/avellozo/cycads-data/ko > /home/avellozo/cycads-data/KOLoaderSQL.out
java -Xmx512M -cp cycads.jar org.cycads.ui.loader.GFF3LoaderSQL /home/avellozo/cycads-data/ACYPI.gff3 7029 AphidBaseS 1.0 > /home/avellozo/cycads-data/GFF3LoaderSQL.out
java -Xmx512M -cp cycads.jar org.cycads.ui.loader.SubseqAnnotationLoaderSQL /home/avellozo/cycads-data/2810_KO_for34821_ACYPI_BBH-subsetEukaryotes 7029 KAAS-subset-eukaryotes 0 AphidBaseP 1 KO -1  > /home/avellozo/cycads-data/SubseqAnnotationLoaderSQL.subset-eukaryotes.out
java -Xmx512M -cp cycads.jar org.cycads.ui.loader.SubseqAnnotationLoaderSQL /home/avellozo/cycads-data/3018_KO_for34821_ACYPI_BBH-RepSet-genes 7029 KAAS-repset-genes 0 AphidBaseP 1 KO -1 > /home/avellozo/cycads-data/SubseqAnnotationLoaderSQL.repset-genes.out
java -Xmx512M -cp cycads.jar org.cycads.ui.loader.SubseqAnnotationLoaderSQL /home/avellozo/cycads-data/3570_KO_for34821_ACYPI_BBH-RepSet-eukaryotes 7029 KAAS-repset-eukaryotes 0 AphidBaseP 1 KO -1  > /home/avellozo/cycads-data/SubseqAnnotationLoaderSQL.repset-eukaryotes.out
java -Xmx512M -cp cycads.jar org.cycads.ui.loader.SubseqAnnotationLoaderSQL /home/avellozo/cycads-data/blast2go_Annot_010409.txt 7029 Blast2Go 0 AphidBaseP 8 "*" 5 > /home/avellozo/cycads-data/SubseqAnnotationLoaderSQL.Blast2Go.out
java -Xmx512M -cp cycads.jar org.cycads.ui.extract.cyc.PFFileGeneratorSQL /home/avellozo/cycads-data/acypi.pf 7029 1.0 n 1.9 1.5 > /home/avellozo/cycads-data/PFFileGeneratorSQL.out

#echo create dblinks in pathwaytools
#AphidBase ---> AphidBase-Gene ---> http://genoweb1.irisa.fr/cgi-bin/gbrowse/gbrowse_details/aphidbase?name=~A
#AphidBaseS ---> AphidBase ---> http://genoweb1.irisa.fr/cgi-bin/gbrowse/gbrowse_details/aphidbase?name=~A
#AphidBaseR ---> AphidBase-mRNA ---> http://genoweb1.irisa.fr/cgi-bin/gbrowse/gbrowse_details/aphidbase?name=~A
#GLEAN ---> AphidBase ---> http://genoweb1.irisa.fr/cgi-bin/gbrowse/gbrowse_details/aphidbase?name=~A
#AphidBaseP ---> PhylomeDB ---> http://phylomedb.org/?seqid=~A
#PhylomeDB ---> PhylomeDB ---> http://phylomedb.org/?seqid=~A
#KO ---> KO ---> http://www.genome.jp/dbget-bin/www_bget?ko+~A
#RefSeqP ---> Refseq-Protein ---> http://www.ncbi.nlm.nih.gov/entrez/viewer.fcgi?val=~A
#RefSeqM ---> Refseq-mRNA ---> http://www.ncbi.nlm.nih.gov/entrez/viewer.fcgi?val=~A

