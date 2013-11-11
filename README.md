Implicit Feedback Ranking: 
===========================================

This project allows to mine the attractiveness of images or other items
displayed on a web site by examining Web user behavior (clicks).

Counting clicks upon images displayed on a search results page (e.g. CD covers,
pictures, applicants) allows to measure the attractiveness of these images. The
measured attractiveness can subsequently be used to order search results pages.
This process is called implicit feedback ranking. Yet mining the attractiveness
of images from clicks is a non-trivial task. Several factors must be taken ino
account such as the position that the item appears on a search results page,
the number of times it has been displayed and the relative attractiveness of
other items displayed on the same search results page.

This project allows to accurately measure the attractivness of items by
maintaining an acyclic weigthed graph where the vertices (nodes) correspond to
the items, and the weighted edges correspond to user preferences between the
items.

Prerequisites for building
--------------
* Java 1.7 or greater
* gradle (build tool)

Installation
-------------

Milestones:
-----------

Issues:
-------
