## Poker game
This is a Java web project. The topic is the ...
## How it's works:
The first thing the guest sees is the home page that shows the rules of the poker game. Also, the guest can log in or register. Further, the guest, having passed the authorization, becomes a user. Each user has his own profile. The profile can be edited: name, bio, phone number and photo. Also, the user can change his password.
## Project features:
* User friendly URL
* Localization: en_EN, ru_RU
* 2 roles
* Custom tag
* Custom connection pool and proxy connections
* Save transactions
* Web socket
* Access token (jwt)
* SQL database

function | ADMIN’S SCOPE | USER’S SCOPE | GUEST’S SCOPE
---------| --------------|----------------|---------------
change language| * | * | * |
ban,unban users | * |
change own profile information | * | * |
logout | * | * |
singing in |   |   | * |
register user |   |   | * |
