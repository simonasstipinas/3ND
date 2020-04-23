#!/bin/sh
#SBATCH -p 1 # eilė
#SBATCH -N1 # keliuose kompiuteriuose (trečioje programoje nenaudojame MPI, todėl tik 1)
#SBATCH -c16 # kiek procesorių viename kompiuteryje
#SBATCH -C alpha # telkinys (jei alpha neveikia, bandykite beta)
java MergeSort