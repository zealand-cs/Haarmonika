
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
Medarbejderen kan vælge en booking og aflyse den, så den stadig ligger i systemet,
og åbner op for en anden kunde at booke den tid.


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

# USE CASE - Edit booking

###### Precondition:
En medarbejder ønsker at redigere i en booking.

Medarbejderen vælger bookingen fra en liste af bookings der passer
til kundens informationer.

###### Postcondition:
Medarbejderen kan vælge en booking og redigere den, såsom tidspunkt,
kunde og type af service.

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
# USE CASE - Visning af bookings (Se dagsskema/ugeplan)
##### Precondition:
En medarbejder ønsker at se sine bookinger for en given dag eller uge.
Systemet skal kunne præsentere en oversigt over bookinger med relevante detaljer.

##### Postcondition:
Medarbejderen har nu adgang til en oversigt over dagens/ugens bookinger og kan planlægge arbejdet bedre.

```mermaid
sequenceDiagram

participant Employee
participant System

title View Bookings SystemSequenceDiagram
Employee ->> System: Request booking overview

    System ->> System: Fetch bookings for selected day/week

    alt Bookings available
        System ->> Employee: Show booking overview
    else No bookings found
        System ->> Employee: Display "No bookings available"
    end
    
```
# USE CASE - Håndtering af konflikter i booking
##### Precondition:
En medarbejder eller administrator forsøger at oprette
en ny tidsbestilling.
Systemet skal sikre, at ingen medarbejder 
har to samtidige bookinger.

##### Postcondition:
Hvis der er en konflikt, får brugeren en fejlmeddelelse og skal vælge et nyt tidspunkt.
Hvis der ikke er en konflikt, bliver bookingen oprettet korrekt.


```mermaid
sequenceDiagram

participant User
participant System
title Booking Conflict Handling SystemSequenceDiagram
User ->> System: Attempt to create booking

    System ->> System: Check for conflicting bookings

    alt No conflict
        System ->> User: Booking confirmed
    else Conflict detected
        System ->> User: Error - Time slot unavailable
    end
```

# USE CASE - Administratorfunktioner
##### Precondition:
Monika ønsker at administrere medarbejdere og bookinger i systemet.

#### Postcondition:
Monika har adgang til at tilføje/slette medarbejdere og administrere bookinger.

```mermaid
sequenceDiagram

    participant Admin
    participant System
    title Admin Functions SystemSequenceDiagram
    Admin ->> System: Manage employee or booking data

    System ->> System: Validate admin privileges

    alt Valid admin access
        System ->> Admin: Access granted
    else Unauthorized access
        System ->> Admin: Error - Insufficient privileges
    end
```

# USE CASE - Notifikationer til kunder
##### Precondition:
En booking er oprettet, ændret eller aflyst, og kunden skal informeres via e-mail eller SMS.

##### Postcondition:
Kunden har modtaget en besked om deres bookingstatus.

```mermaid
sequenceDiagram

    participant System
    participant Customer
    title Customer Notification SystemSequenceDiagram
    System ->> System: Detect booking creation/update/cancellation

    alt Notification enabled
        System ->> Customer: Send email/SMS notification
    else No notification setup
        System ->> System: No action taken
    end
```
# USE CASE - Kunderegister
##### Precondition:
En medarbejder ønsker at gemme eller opdatere oplysninger om en kunde.

##### Postcondition:
Kunden er nu registreret i systemet med navn, kontaktoplysninger og eventuel bookinghistorik.

```mermaid
sequenceDiagram

    participant Employee
    participant System
    title Customer Register SystemSequenceDiagram
    Employee ->> System: Enter customer details

    System ->> System: Validate and store customer data

    alt Valid data
        System ->> Employee: Customer saved successfully
    else Invalid data
        System ->> Employee: Error - Invalid input
    end
```

# USE CASE - Automatisk sletning af gamle bookinger
##### Precondition:
Der findes bookinger i systemet, der er ældre end 5 år.

##### Postcondition:
Systemet har automatisk slettet gamle bookinger med et fast interval (f.eks. en gang om måneden).

```mermaid
sequenceDiagram

    participant System
    title Auto Delete Old Bookings SystemSequenceDiagram
    System ->> System: Identify bookings older than 5 years

    alt Old bookings found
        System ->> System: Delete outdated bookings
    else No outdated bookings
        System ->> System: No action taken
    end
```