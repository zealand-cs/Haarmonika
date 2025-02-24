
# Diagram

```mermaid
SystemsequenceDiagram

participant User
participant System
    title Login System Sequence Diagram
     User ->> System: Enter USERNAME & Password
        System ->> System: Validate UserInput
        alt Correct UserInput
            System ->> User: Login succesful
        else Wrong UserInput
            System ->> User: Error -- Username or Password
        end
```


