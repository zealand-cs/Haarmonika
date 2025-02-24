
# Diagram


# USE CASE - Login system

###### Precondition:
En medarbejder ønsker at kunne logge ind til arbejdssystemet for
at tilgå sin kalendar.

Når medarbejderen kommer på arbejde vil vedkommende kunne logge ind
på systemet. Der bliver tjekket for korrekt username og password

###### Postcondition:
Medarbejderen har mulighed for at logge ind på systemet for at få sin kalender,
så de kan holde øje med dagen/ugen og månedens kalender.

```mermaid
sequenceDiagram

participant User
participant System
    title Login System SystemSequenceDiagram
     User ->> System: Enter USERNAME & Password
        System ->> System: Validate UserInput
        alt Correct UserInput
            System ->> User: Login succesful
        else Wrong UserInput
            System ->> User: Error -- Username or Password
        end
```

# USE CASE - Create booking

###### Precondition:
En medarbejder ønsker at lave en booking til en kunde.

Medarbejderen vælger dato, tidspunkt og type af service, samt
indtaster kundeinformationer (navn, telefon nr eller e-mail)

###### Postcondition:
Medarbejden kan lave en booking til en kunde ud fra den givne
medarbejder, med kundens informationer og et tidspunkt/dato.

```mermaid
sequenceDiagram

participant User
participant System
    title Create booking SystemSequenceDiagram
     User ->> System: Enter/Select Customer <br/> Date/Time and type of haircut 
     
        System ->> System: Validate Booking
        alt Correct Input
            System ->> User: Booking succesful
        else Wrong Input
            System ->> User: Error -- Wrong inputs
        end
```
# USE CASE - Cancel booking

###### Precondition:
En medarbejder ønsker at aflyse en booking til en kunde.

Medarbejderen vælger bookingen fra en liste af bookings der passer
til kundens informationer.

###### Postcondition:


```mermaid
sequenceDiagram

participant User
participant System
    title Cancel booking SystemSequenceDiagram
     User ->> System: Enter/Select Booking  
     
        System ->> System: Validate Booking
        alt Correct Input
            System ->> User: Booking Cancelled
        else Wrong Input
            System ->> User: Error -- Wrong inputs
        end
```



```mermaid
sequenceDiagram

participant User
participant System
    title Edit booking SystemSequenceDiagram
     User ->> System: Select booking  
     
        System ->> System: Validate Booking
        
        alt Correct Input
            System ->> User: Booking info shown
        else Wrong Input
            System ->> User: Error    
        end
              
```


