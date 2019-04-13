# DBMS Experiment 3

A social network database.

## ER Diagram

![SocialNetwork_ER](social_network.svg)

## Commands

* Personal Info

```
login <username> <password>

register <username> <password>

unregister

logout

exit
```

```
show info

update info (<attr_name>=<value>, ...)
```

* Additional Info

```
show education [(<attr_name>=<value>, ...)]

add education (<attr_name>=<value>, ...)

update education <education_id> (<attr_name>=<value>, ...)

remove education <education_id>
```

```
show work [(<attr_name>=<value>, ...)]

add work (<attr_name>=<value>, ...)

update work <work_id> (<attr_name>=<value>, ...)

remove work <work_id>
```

* Friend

```
show user [(<attr_name>=<value>, ...)]

show friend [(<attr_name>=<value>, ...)]

add friend <username>

update friend (<attr_name>=<value>, ...)

remove friend <username>
```

```
show friend_group

add friend_group (<attr_name>=<value>, ...)

update friend_group (<attr_name>=<value>, ...)

remove friend_group <group_name>
```

* Post

```
show post [(<attr_name>=<value>, ...)]

add post (<attr_name>=<value>, ...)

update post (<attr_name>=<value>, ...)

remove post <post_id>
```

* Reply

```
show reply [(<attr_name>=<value>, ...)]

add reply (<attr_name>=<value>, ...)

remove reply <reply_id>
```

* Share

```
show share [(<attr_name>=<value>, ...)]

add share (<attr_name>=<value>, ...)

remove share <share_id>
```
