# HKA INFM Projektarbeit 2 - StateMerger Application

This repository is part of the module "Projektarbeit 2" of the computer science M.Sc. study programm at [Karlsruhe University of Applied Sciences](https://www.h-ka.de/en/) accompanied by Prof. Dr. Körner.

The task is to create an application that can merge graph nodes in (near) linear time, wrapped up in a user-friendly GUI.
Done by example of "merging" German federal states, containing a capital city each.


## The Task (in German)

**Optimierung der Bundesrepublik**

In der Bundesrepublik Deutschland wurde schon mehrmals vorgeschlagen, kleinere Bundesländer mit anderen zu fusionieren, um Verwaltungskosten zu sparen (z.B. das Saarland mit Rheinland-Pfalz oder Berlin mit Brandenburg). Angenommen, Sie möchten eine Datenstruktur implementieren, die folgende Operationen für die Städte in der Bundesrepublik beherrscht:

* `Find(s)`: man kann für jede Stadt `s` abfragen, in welchem Bundesland sie liegt
* `Union(b1, b2)`: fusioniere die Bundesländer `b1` und `b2` und vergebe für das vereinte Bundesland den Namen `b1` oder auch `b2`. "Vereint" bedeutet, dass `Find(s)` für jede Stadt aus den beiden ursprünglich beteiligten Bundesländern nun immer nur noch den einen neuen Namen zurückgibt.

Damit man irgendwie auch "anfangen" kann, definieren wir noch diese Operation:

* `Make-State(s, b)`: erstelle ein neues Bundesland mit dem Namen `b`, welches nur die eine Stadt `s` enthält.

Zum Aufbau der "echten" heutigen Bundesländer kann man dann die Operation `Union` entsprechend oft aufrufen.

Es ist möglich, eine Datenstruktur zu erstellen, die alle diese Operationen in so gut wie (optimaler) Linearzeit ausführt. Ihr Programm implementiert diese Datenstruktur und veranschaulicht die Arbeitsweise der einzelnen Operationen.

**Literatur:** Th. H. Cormen et al.: _Introduction to Algorithms_, Kapitel 21


## How to {build, test, run}

You will need [Node Package Manager](https://nodejs.org/en/download/package-manager) and an internet connection to install the node modules.

### Getting started

Run `npm install` to download and setup the node modules.

### Open dev server

To open a local dev server with hot-reloading, run `npm run dev`. If you want to access the website on other devices in your local network, run `npm run dev -- --host`.

Vite will give you information on how to access the website. Usually, that is [http://localhost:5173](http://localhost:5173).

### Run unit tests

For unit testing, run `npm test`.

### Build for production

To build the application for production, run `npm run build`.


## Contributing

--- work in progress ---


## License

&copy; 2023 Fynn Arnold. Licensed under the [EUPL](LICENSE).
