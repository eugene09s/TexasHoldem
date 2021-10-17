## Poker game
This is a Java web project. The topic is the ...
## How it's works:
Guest can find passing scores for past years. Then register and their status changes on user. User can fill the form and see own profile information. Admin can check users information, activate users to participate in the entrance competition, block and unblock users, make user admin. Admin can start the entrance competition and see enrolled and unenrolled applicants by specialties.
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