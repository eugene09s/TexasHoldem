## Poker game
The first thing a guest sees is the home page with poker rules.
A guest also can log in or register. After passing the authorization, a guest becomes a user. Each user has his own profile, which can be edited: name, biography, phone number and photo. Also, users can change their passwords using the old password. The administrator's rights allows you to ban/unban users and add money to users' balances. All users can go to the game page (lobby), select a table, join, and start the game if there are at least two players . After the game, the statistics will be recorded and a user can see it.
## Project features:
* User friendly URL
* Localization: en_EN, ru_RU
* 2 roles
* Custom tag
* Custom connection pool and proxy connections
* Save transactions
* WebSocket
* Access token (jwt)
* SQL database

function | ADMIN’S SCOPE | USER’S SCOPE | GUEST’S SCOPE
---------| --------------|----------------|---------------
change language| * | * | * |
ban,unban users | * |
modify balance users | *  |   |  |
change own profile information | * | * |
start playing | *  |  * |  |
see user profiles | *  |  * |  |
see statistic games | *  |  * |  |
logout | * | * |
singing in |   |   | * |
register user |   |   | * |
### Schema database:
![text](https://github.com/Evgenij009/TexasHoldem/blob/master/src/sql/pokerschema.png?raw=true)

Statistic
![image](https://user-images.githubusercontent.com/38386052/235198931-7d2d40c2-2015-4aef-8b99-fe4878b853d0.png)
