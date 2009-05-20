java -cp cycads.jar org.cycads.ui.loader.KOLoaderSQL /home/avellozo/cycads-data/ko
java -cp cycads.jar org.cycads.ui.loader.GFF3LoaderSQL /home/avellozo/cycads-data/ACYPI.gff3 7029 AphidBaseS 1.0
java -cp cycads.jar org.cycads.ui.loader.CDSToDbxrefLoaderSQL /home/avellozo/cycads-data/blast2go_Annot_010409.txt 7029 Blast2Go 0 AphidBaseP 8 "*" 5
java -cp cycads.jar org.cycads.ui.loader.CDSToDbxrefLoaderSQL /home/avellozo/cycads-data/2810_KO_for34821_ACYPI_BBH-subsetEukaryotes.txt 7029 KAAS-subset-eukaryotes 0 AphidBaseP 1 KO -1
java -cp cycads.jar org.cycads.ui.loader.CDSToDbxrefLoaderSQL /home/avellozo/cycads-data/3018_KO_for34821_ACYPI_BBH-RepSet-genes.txt 7029 KAAS-repset-genes 0 AphidBaseP 1 KO -1
java -cp cycads.jar org.cycads.ui.loader.CDSToDbxrefLoaderSQL /home/avellozo/cycads-data/3570_KO_for34821_ACYPI_BBH-RepSet-eukaryotes.txt 7029 KAAS-repset-eukaryotes 0 AphidBaseP 1 KO -1
java -cp cycads.jar org.cycads.ui.extract.cyc.PFFileGeneratorSQL /home/avellozo/cycads-data/acypi.pf 7029 1.0 3.0 n

#echo create dblinks in pathwaytools
#AphidBase ---> http://genoweb1.irisa.fr/cgi-bin/gbrowse/gbrowse_details/aphidbase?name=~A
#AphidBaseS ---> http://genoweb1.irisa.fr/cgi-bin/gbrowse/gbrowse_details/aphidbase?name=~A
#AphidBaseR ---> http://genoweb1.irisa.fr/cgi-bin/gbrowse/gbrowse_details/aphidbase?name=~A
#GLEAN ---> http://genoweb1.irisa.fr/cgi-bin/gbrowse/gbrowse_details/aphidbase?name=~A
#AphidBaseP ---> http://phylomedb.org/?seqid=~A
#KO ---> http://www.genome.jp/dbget-bin/www_bget?ko+~A


