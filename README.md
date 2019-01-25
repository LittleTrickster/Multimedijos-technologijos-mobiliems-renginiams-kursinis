# Multimedijos technologijos mobiliems įrenginiams kursinis darbas

## Pagridniniai 2 aplikacijos langai

### Pirmas langas
Pirmas langas kuriame pasirenkamas Bluetooth prietaisas. Jo klases pavadinimas MainActivity. Tik pririšus naują prietaisą paspaudus „Refresh“ mygtuką galima atnaujinti sąrašą.
 
![Alt text](/misc/0.jpg?raw=true  "Pasirinkimo langas")
 ### Antras langas
Pasirinkus prietaisą ir be klaidos prisijungus prie jo langas pasikeičia į valdymo langa kuriame yra du roboto valdymo virtualios vairalazdės.
Kairys skirtas važiavimui į prieki arba atgal o dešinys skirtas posūkiams.
Čia yra panaudota viešai prieinama biblioteka'io.github.controlwear:virtualjoystick:1.10.1' kuri leidžia kurti įvairias vairalazdes (controllers). Viršuje yra juoda linija, kuri vizualiai atvaizduoja iš roboto gautus ultragarso ilgėjant kai atstumas padidėja ir atvirkščiai. O žemiau atstumas centimetrais.

 ![Alt text ](/misc/1.jpg?raw=true "Roboto valdymo fragmentas")

Žemiau matomas kaip atrodo naudojama virtuali vairalazdė ir besikeičiantis iš roboto ultragarso gautas 
 
  ![Alt text ](/misc/2.jpg?raw=true  "Roboto valdymo fragmentas")

## Komentarai
Arduino kodas randasi ArduinoCar.rar aplankale.

