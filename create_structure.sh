#!/usr/bin/env bash
# Script para crear la estructura de carpetas y archivos desde un árbol en un archivo de texto.
BASE="src/main/java/com/example/hack2v2"
INPUT="estructura_proyecto_structure.txt"

# Verifica que el archivo de entrada exista
if [ ! -f "$INPUT" ]; then
  echo "Archivo $INPUT no encontrado. Asegúrate de tenerlo en la raíz del proyecto."
  exit 1
fi

# Procesa cada línea, elimina caracteres de árbol y crea carpetas/ficheros
sed -E 's/^[[:space:]│├└─]*//' "$INPUT" | while read -r LINE; do
  if [[ "$LINE" == */ ]]; then
    mkdir -p "$BASE/${LINE%/}"
  else
    DIR=$(dirname "$BASE/$LINE")
    mkdir -p "$DIR"
    touch "$BASE/$LINE"
  fi
done

echo "Estructura creada correctamente."
