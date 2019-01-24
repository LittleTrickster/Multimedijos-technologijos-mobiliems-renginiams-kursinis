# Multimedijos technologijos mobiliems renginiams kursinis darbas

Ši aplikacija yra sudaryta iš 2 langų 
Pirmas langas kuriame pasirenkamas Bluetooth prietaisas. Jo klases pavadinimas MainActivity. Turint naują prietaisą paspaudus „Refresh“ mygtuką galima atnaujinti sąrašą.
 
![Alt text](/misc/0.jpg?raw=true  "Double Joystick with custom size and colors")
 
Pasirinkus prietaisą ir be klaidos prisijungus prie jo langas pasikeičia į valdymo langa kuriame yra du roboto valdymo virtualios vairalazdės.
Kairys skirtas važiavimui tiesiai arba atgal o dešinys skirtas posūkiams.
Čia yra panaudota viešai prieinama biblioteka'io.github.controlwear:virtualjoystick:1.10.1' kuri leidžia kurti įvairias vairalazdes (controllers). Viršuje yra juoda linija, kuri vizualiai atvaizduoja iš roboto gautus ultragarso ilgėjant kai atstumas padidėja ir atvirkščiai. O žemiau atstumas centimetrais.

 ![Alt text size](/misc/1.jpg?raw=true "Double Joystick with custom size and colors")

Žemiau matomas kaip atrodo naudojama virtuali vairalazdė ir besikeičiantis iš roboto ultragarso gautas 
 
  ![Alt text size](/misc/2.jpg?raw=true "Double Joystick with custom size and colors")

Komentarai
Arduino kodas randasi ArduinoCar.rar aplankale.

